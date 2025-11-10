package com.persistance.practica1.entitites;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Equipos")
public class Equipo {
 
    public Equipo(){};
    public Equipo(String nombre, String superpoder){
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

    @OneToMany(mappedBy="miEquipo")
    List<Heroe> heroes;

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
    public List<Heroe> getHeroes() {
        return heroes;
    }
    public void setHeroes(List<Heroe> heroes) {
        this.heroes = heroes;
    }
}
