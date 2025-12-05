package com.persistance.practica1.util;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

/**
 * Utilidad para gestión de tokens JWT
 * Genera, valida y extrae información de tokens JWT con soporte de roles
 */
@Component
public class JwtPropierties {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Obtiene la clave secreta para firmar tokens
     * 
     * @return SecretKey - Clave HMAC-SHA
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Genera un nuevo token JWT para un usuario (sin roles)
     * 
     * @param username - Nombre del usuario
     * @return String - Token JWT generado
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Genera un nuevo token JWT para un usuario con roles
     * 
     * @param username - Nombre del usuario
     * @param roles    - Colección de roles del usuario
     * @return String - Token JWT generado con roles
     */
    public String generateToken(String username, Collection<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extrae todas las reclamaciones del token
     * 
     * @param token - Token JWT
     * @return Claims - Reclamaciones del token
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene el nombre de usuario del token
     * 
     * @param token - Token JWT
     * @return String - Nombre de usuario
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Obtiene los roles del token como List
     * 
     * @param token - Token JWT
     * @return List - Lista de roles del usuario
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        return getClaimsFromToken(token).get("roles", List.class);
    }

    /**
     * Obtiene la fecha de expiración del token
     * 
     * @param token - Token JWT
     * @return Date - Fecha de expiración
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    /**
     * Valida que el token sea válido y no esté expirado
     * 
     * @param token - Token JWT
     * @return boolean - true si es válido, false si no
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.err.println("Token mal formado: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Token expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Token no soportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Token vacío o nulo: " + e.getMessage());
        } catch (JwtException e) {
            System.err.println("Error general con el token: " + e.getMessage());
        }
        return false;
    }

    /**
     * Verifica si el token está expirado
     * 
     * @param token - Token JWT
     * @return boolean - true si está expirado, false si no
     */
    public boolean isTokenExpired(String token) {
        try {
            Date tokenExpiration = getExpirationDateFromToken(token);
            return tokenExpiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
