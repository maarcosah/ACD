package com.persistance.practica1.services;

import com.persistance.practica1.entitites.Role;
import com.persistance.practica1.entitites.User;
import com.persistance.practica1.repositories.RoleRepository;
import com.persistance.practica1.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Servicio para gestión de usuarios
 * Proporciona métodos para crear y buscar usuarios
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Crea un nuevo usuario con los roles especificados
     * 
     * @param username  - Nombre de usuario
     * @param password  - Contraseña (será encriptada)
     * @param email     - Email del usuario
     * @param roleNames - Set de nombres de roles a asignar
     * @return User - Usuario creado
     * @throws RuntimeException - Si el usuario o email ya existen
     */
    @Transactional
    public User createUser(String username, String password, String email, Set<String> roleNames) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("El usuario ya existe");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya está registrado");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role.RoleName roleEnum = Role.RoleName.valueOf(roleName);
            Role role = roleRepository.findByName(roleEnum)
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

    /**
     * Busca un usuario por nombre de usuario
     * 
     * @param username - Nombre de usuario
     * @return User - Usuario encontrado
     * @throws RuntimeException - Si no se encuentra el usuario
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    /**
     * Verifica si existe un usuario con el nombre dado
     * 
     * @param username - Nombre de usuario
     * @return boolean - true si existe, false si no
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Verifica si existe un usuario con el email dado
     * 
     * @param email - Email a verificar
     * @return boolean - true si existe, false si no
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
