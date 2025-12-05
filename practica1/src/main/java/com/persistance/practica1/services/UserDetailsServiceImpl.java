package com.persistance.practica1.services;

import com.persistance.practica1.entitites.User;
import com.persistance.practica1.repositories.UserRepository;
import com.persistance.practica1.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementación de UserDetailsService para cargar usuarios desde la base de
 * datos
 * Utilizado por Spring Security para autenticación
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Carga un usuario por su nombre de usuario
     * 
     * @param username - Nombre de usuario a buscar
     * @return UserDetails - Detalles del usuario para Spring Security
     * @throws UsernameNotFoundException - Si el usuario no existe
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return new UserDetailsImpl(user);
    }
}
