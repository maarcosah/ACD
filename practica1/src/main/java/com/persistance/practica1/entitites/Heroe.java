package com.persistance.practica1.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Heroe")
public class Heroe {
    
    public Heroe(){};
    public Heroe(String nombre, String superpoder){
        this.nombre = nombre;
        this.superpoder = superpoder;
    };

    @Column(name="nombre", unique = true, length = 40)
    String nombre;

    @Column(name="superpoder", length = 40)
    String superpoder;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPasajeros;

    @ManyToOne
    @JoinColumn( name = "miEquipo", nullable = false )
    Equipo miEquipo;

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getSuperpoder() {
        return superpoder;
    }
    public void setSuperpoder(String superpoder) {
        this.superpoder = superpoder;
    }
    public Long getIdPasajeros() {
        return idPasajeros;
    }
    public void setIdPasajeros(Long idPasajeros) {
        this.idPasajeros = idPasajeros;
    }
    public Equipo getMiEquipo() {
        return miEquipo;
    }
    public void setMiEquipo(Equipo miEquipo) {
        this.miEquipo = miEquipo;
    }
}
