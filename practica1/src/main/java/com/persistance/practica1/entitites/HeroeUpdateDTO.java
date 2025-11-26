package com.persistance.practica1.entitites;

import java.util.List;

public class HeroeUpdateDTO {
    
    private String nombre;
    private String universo;
    private String superpoder;
    private Long equipoId;
    private List<Long> poderIds;

    // Constructores
    public HeroeUpdateDTO() {}

    public HeroeUpdateDTO(String nombre, String universo, String superpoder, Long equipoId, List<Long> poderIds) {
        this.nombre = nombre;
        this.universo = universo;
        this.superpoder = superpoder;
        this.equipoId = equipoId;
        this.poderIds = poderIds;
    }

    // Getters y Setters
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

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }

    public List<Long> getPoderIds() {
        return poderIds;
    }

    public void setPoderIds(List<Long> poderIds) {
        this.poderIds = poderIds;
    }
}
