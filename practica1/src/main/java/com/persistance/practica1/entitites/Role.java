package com.persistance.practica1.entitites;

import jakarta.persistence.*;

/**
 * Entidad Role para gestión de roles de usuario
 * Utiliza enum RoleName para definir los roles disponibles
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleName name;

    // Constructores
    public Role() {
    }

    public Role(RoleName name) {
        this.name = name;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    /**
     * Enum para los roles disponibles en el sistema
     */
    public enum RoleName {
        ROLE_ADMIN,
        ROLE_USER,
        ROLE_MODERATOR
    }
}
