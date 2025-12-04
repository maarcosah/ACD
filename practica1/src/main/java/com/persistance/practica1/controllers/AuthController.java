package com.persistance.practica1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.persistance.practica1.util.JwtPropierties;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para autenticación y generación de tokens JWT
 * Proporciona endpoint de login para obtener tokens de acceso
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtPropierties jwtTokenUtil;

    /**
     * Clase interna para recibir credenciales de login
     */
    public static class AuthRequest {
        public String username;
        public String password;
    }

    /**
     * Clase interna para enviar respuesta con token JWT
     */
    public static class AuthResponse {
        public String token;

        public AuthResponse(String token) {
            this.token = token;
        }
    }

    /**
     * Endpoint de login para autenticar usuario y obtener token JWT
     * 
     * @param authRequest - Credenciales del usuario (username y password)
     * @return ResponseEntity con token JWT si autenticación es exitosa
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // Autenticar usuario con las credenciales proporcionadas
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.username,
                            authRequest.password));

            // Generar token JWT para el usuario autenticado
            String token = jwtTokenUtil.generateToken(authRequest.username);

            // Retornar respuesta con el token
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (Exception e) {
            // Retornar error si las credenciales son inválidas
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Credenciales inválidas");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
}