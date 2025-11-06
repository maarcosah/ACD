package com.persistance.practica1.entitites;

import javax.annotation.processing.Generated;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;

@Entity
@Table


public class Pasajeros {


    public Pasajeros(){};
    public Pasajeros(String nombre){
        this.nombre = nombre;
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPasajeos;

    @Column(name="nombre", unique = true, length = 40)
    String nombre;

    @Column(name="apellido", length = 40)
    String apellido;

    @Column(name="direccion", length = 100)
    String direccion;

    public Long getIdPasajeos() {
        return idPasajeos;
    }

    public void setIdPasajeos(Long idPasajeos) {
        this.idPasajeos = idPasajeos;
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
