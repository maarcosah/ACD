package com.persistance.practica1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistance.practica1.entitites.Poder;
import com.persistance.practica1.entitites.PoderUpdateDTO;
import com.persistance.practica1.repositories.PoderRepository;

import jakarta.transaction.Transactional;

@Service
public class PoderService {
    
    @Autowired
    private PoderRepository poderRepository;

    // LECTURAS
    public List<Poder> obtenerPoderes() {
        return poderRepository.findAll();
    }
    
    public Optional<Poder> findById(Long id) {
        return poderRepository.findById(id);
    }
    
    public Optional<Poder> findByNombre(String nombre) {
        return poderRepository.findFirstByNombre(nombre);
    }

    // ESCRITURAS
    @Transactional
    public Poder crearPoder(Poder poder) {
        return poderRepository.save(poder);
    }
    
    @Transactional
    public Poder guardarPoder(Poder poder) {
        return poderRepository.save(poder);
    }
    
    @Transactional
    public Poder actualizar(Long id, PoderUpdateDTO updateDTO) {
        Optional<Poder> poderOpt = poderRepository.findById(id);
        if (!poderOpt.isPresent()) {
            throw new RuntimeException("Poder no encontrado con id: " + id);
        }
        
        Poder poder = poderOpt.get();
        
        if (updateDTO.getNombre() != null) {
            poder.setNombre(updateDTO.getNombre());
        }
        if (updateDTO.getDescripcion() != null) {
            poder.setDescripcion(updateDTO.getDescripcion());
        }
        
        return poderRepository.save(poder);
    }
    
    @Transactional
    public void eliminarPoder(Long id) {
        poderRepository.deleteById(id);
    }
}
