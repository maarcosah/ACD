package com.persistance.practica1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.persistance.practica1.filter.JwtRequestFilter;

/**
 * Configuración de seguridad para la aplicación
 * Define las políticas de autenticación, autorización y filtros JWT
 * Usa @EnableMethodSecurity para permitir @Secured en métodos
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

        @Autowired
        private JwtRequestFilter jwtRequestFilter;

        /**
         * Configura la cadena de filtros de seguridad
         * - /api/v1/auth/** : Acceso público (login, register)
         * - /api/v1/heroes/** : Requiere rol MODERATOR
         * - Resto de endpoints : Requiere autenticación
         * 
         * @param http - HttpSecurity para configurar
         * @return SecurityFilterChain - Cadena de filtros configurada
         * @throws Exception - Si hay error en la configuración
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers("/api/v1/auth/**").permitAll()
                                                .requestMatchers("/api/v1/heroes/**").hasRole("MODERATOR")
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        /**
         * Define el codificador de contraseñas
         * 
         * @return PasswordEncoder - BCryptPasswordEncoder
         */
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        /**
         * Obtiene el gestor de autenticación
         * 
         * @param authenticationConfiguration - Configuración de autenticación
         * @return AuthenticationManager - Gestor de autenticación
         * @throws Exception - Si hay error al obtener el gestor
         */
        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }
}