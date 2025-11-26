package com.persistance.practica1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.entitites.EquipoUpdateDTO;
import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.repositories.EquipoRepository;
import com.persistance.practica1.repositories.HeroeRepository;

import jakarta.transaction.Transactional;

@Service
public class EquipoService {
    
    @Autowired
    private EquipoRepository equipoRepository;
    
    @Autowired
    private HeroeRepository heroeRepository;

    // LECTURAS
    public List<Equipo> obtenerEquipos() {
        return equipoRepository.findAll();
    }
    
    public Optional<Equipo> findById(Long id) {
        return equipoRepository.findById(id);
    }
    
    public Optional<Equipo> findByNombre(String nombre) {
        return equipoRepository.findFirstByNombre(nombre);
    }

    // ESCRITURAS
    @Transactional
    public Equipo crearEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }
    
    @Transactional
    public Equipo guardarEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }
    
    @Transactional
    public Equipo actualizar(Long id, EquipoUpdateDTO updateDTO) {
        Optional<Equipo> equipoOpt = equipoRepository.findById(id);
        if (!equipoOpt.isPresent()) {
            throw new RuntimeException("Equipo no encontrado con id: " + id);
        }
        
        Equipo equipo = equipoOpt.get();
        
        if (updateDTO.getNombre() != null) {
            equipo.setNombre(updateDTO.getNombre());
        }
        if (updateDTO.getDescripcion() != null) {
            equipo.setDescripcion(updateDTO.getDescripcion());
        }
        
        return equipoRepository.save(equipo);
    }
    
    @Transactional
    public Equipo actualizarHeroes(Long id, List<Long> heroeIds) {
        Optional<Equipo> equipoOpt = equipoRepository.findById(id);
        if (!equipoOpt.isPresent()) {
            throw new RuntimeException("Equipo no encontrado con id: " + id);
        }
        
        Equipo equipo = equipoOpt.get();
        List<Heroe> heroes = heroeRepository.findAllById(heroeIds);
        equipo.setHeroes(heroes);
        
        return equipoRepository.save(equipo);
    }
    
    @Transactional
    public void eliminarEquipo(Long id) {
        equipoRepository.deleteById(id);
    }
}