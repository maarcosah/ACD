package com.persistance.practica1.entitites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "pasajeros")

public class Pasajeros {


    public Pasajeros(){};
    public Pasajeros(String nombre, String apellido){
        this.nombre = nombre;
        this.apellido = apellido;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPasajeros;

    @Column(name="nombre", unique = true, length = 40)
    String nombre;

    @Column(name="apellido", length = 40)
    String apellido;

    @Column(name="direccion", length = 100)
    String direccion;

    public Long getIdPasajeros() {
        return idPasajeros;
    }

    public void setIdPasajeros(Long idPasajeros) {
        this.idPasajeros = idPasajeros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
