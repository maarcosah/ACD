package com.persistance.practica1.controllers;

import com.persistance.practica1.entitites.User;
import com.persistance.practica1.security.UserDetailsImpl;
import com.persistance.practica1.services.UserDetailsServiceImpl;
import com.persistance.practica1.services.UserService;
import com.persistance.practica1.util.JwtPropierties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controlador para autenticación y gestión de usuarios con JWT
 * Proporciona endpoints: /login, /register, /me
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtPropierties jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    // ==================== DTOs ====================

    /**
     * DTO para petición de login
     */
    public static class LoginRequest {
        public String username;
        public String password;
    }

    /**
     * DTO para petición de registro
     */
    public static class RegisterRequest {
        public String username;
        public String password;
        public String email;
        public Set<String> roles;
    }

    /**
     * DTO para respuesta de autenticación
     */
    public static class AuthResponse {
        public String token;
        public String type = "Bearer";
        public String username;
        public String email;
        public Set<String> roles;

        public AuthResponse(String token, String username, String email, Set<String> roles) {
            this.token = token;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.username,
                            loginRequest.password));

            // Establecer la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtener UserDetails
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            // Obtener roles
            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            // Generar token con roles
            String token = jwtTokenUtil.generateToken(userDetails.getUsername(), roles);

            // Retornar respuesta
            return ResponseEntity.ok(new AuthResponse(
                    token,
                    userDetails.getUsername(),
                    userDetails.getUser().getEmail(),
                    roles));

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Credenciales inválidas");
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            Set<String> roles = registerRequest.roles != null ? registerRequest.roles : Set.of("ROLE_USER");

            // Crear usuario
            User user = userService.createUser(
                    registerRequest.username,
                    registerRequest.password,
                    registerRequest.email,
                    roles);

            // Autenticar automáticamente después del registro
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registerRequest.username,
                            registerRequest.password));

            // Incluir autenticación al contexto
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar token
            String token = jwtTokenUtil.generateToken(registerRequest.username, roles);

            // Obtener roles para response
            Set<String> userRoles = user.getRoles().stream()
                    .map(role -> role.getName().name())
                    .collect(Collectors.toSet());

            // Retornar respuesta
            return ResponseEntity.ok(new AuthResponse(
                    token,
                    user.getUsername(),
                    user.getEmail(),
                    userRoles));

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("No autenticado");
        }

        try {
            // Obtiene el nombre de usuario
            String username = (String) authentication.getPrincipal();

            // Carga el objeto UserDetails/User nuevamente
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(username);

            // Obtener roles
            Set<String> userRoles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            // Construir respuesta
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", userDetails.getUsername());
            userInfo.put("email", userDetails.getUser().getEmail());
            userInfo.put("roles", userRoles);

            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}