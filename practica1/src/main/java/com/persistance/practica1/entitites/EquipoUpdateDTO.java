package com.persistance.practica1.entitites;

import java.util.List;

public class EquipoUpdateDTO {

    private String nombre;
    private String descripcion;
    private List<Long> heroeIds;

    // Constructores
    public EquipoUpdateDTO() {}

    public EquipoUpdateDTO(String nombre, String descripcion, List<Long> heroeIds) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.heroeIds = heroeIds;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Long> getHeroeIds() {
        return heroeIds;
    }

    public void setHeroeIds(List<Long> heroeIds) {
        this.heroeIds = heroeIds;
    }
}
