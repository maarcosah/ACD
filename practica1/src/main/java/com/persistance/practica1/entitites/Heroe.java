package com.persistance.practica1.entitites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name="Heroe")
public class Heroe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="nombre", unique = true, length = 40)
    String nombre;

    @Column(name="universo", length = 100)
    String universo;

    @Column(name="superpoder", length = 40)
    String superpoder;

    @ManyToOne
    @JoinColumn( name = "miEquipo", nullable = false )
    Equipo miEquipo;

    @ManyToMany
    @JoinTable(
        name = "Heroe_Poder",
        joinColumns = @JoinColumn(name = "heroe_id"),
        inverseJoinColumns = @JoinColumn(name = "poder_id")
    )
    List<Poder> poderes;
    
    public Heroe(){};
    public Heroe(String nombre, String superpoder){
        this.nombre = nombre;
        this.superpoder = superpoder;
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUniverso() {
        return universo;
    }

    public void setUniverso(String universo) {
        this.universo = universo;
    }

    public String getSuperpoder() {
        return superpoder;
    }
    public void setSuperpoder(String superpoder) {
        this.superpoder = superpoder;
    }
    public Long getIdPasajeros() {
        return this.id;
    }
    public void setIdPasajeros(Long id) {
        this.id = id;
    }
    public Equipo getMiEquipo() {
        return miEquipo;
    }
    public void setMiEquipo(Equipo miEquipo) {
        this.miEquipo = miEquipo;
    }

    public List<Poder> getPoderes() {
        return poderes;
    }

    public void setPoderes(List<Poder> poderes) {
        this.poderes = poderes;
    }
}
