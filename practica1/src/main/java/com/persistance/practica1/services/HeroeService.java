package com.persistance.practica1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.entitites.HeroeUpdateDTO;
import com.persistance.practica1.entitites.Poder;
import com.persistance.practica1.repositories.EquipoRepository;
import com.persistance.practica1.repositories.HeroeRepository;
import com.persistance.practica1.repositories.PoderRepository;

import jakarta.transaction.Transactional;

@Service
public class HeroeService {
    
    @Autowired
    private HeroeRepository heroeRepository;
    
    @Autowired
    private EquipoRepository equipoRepository;
    
    @Autowired
    private PoderRepository poderRepository;

    // LECTURAS
    public List<Heroe> obtenerHeroes() {
        return heroeRepository.findAll();
    }
    
    public Optional<Heroe> findById(Long id) {
        return heroeRepository.findById(id);
    }
    
    public Optional<Heroe> findByNombre(String nombre) {
        return heroeRepository.findFirstByNombre(nombre);
    }

    // ESCRITURAS
    @Transactional
    public Heroe crearHeroe(Heroe heroe) {
        return heroeRepository.save(heroe);
    }
    
    @Transactional
    public Heroe guardarHeroe(Heroe heroe) {
        return heroeRepository.save(heroe);
    }
    
    @Transactional
    public Heroe actualizar(Long id, HeroeUpdateDTO updateDTO) {
        Optional<Heroe> heroeOpt = heroeRepository.findById(id);
        if (!heroeOpt.isPresent()) {
            throw new RuntimeException("Heroe no encontrado con id: " + id);
        }
        
        Heroe heroe = heroeOpt.get();
        
        if (updateDTO.getNombre() != null) {
            heroe.setNombre(updateDTO.getNombre());
        }
        if (updateDTO.getUniverso() != null) {
            heroe.setUniverso(updateDTO.getUniverso());
        }
        if (updateDTO.getSuperpoder() != null) {
            heroe.setSuperpoder(updateDTO.getSuperpoder());
        }
        if (updateDTO.getEquipoId() != null) {
            Optional<Equipo> equipoOpt = equipoRepository.findById(updateDTO.getEquipoId());
            if (equipoOpt.isPresent()) {
                heroe.setMiEquipo(equipoOpt.get());
            } else {
                throw new RuntimeException("Equipo no encontrado con id: " + updateDTO.getEquipoId());
            }
        }
        
        return heroeRepository.save(heroe);
    }
    
    @Transactional
    public Heroe actualizarPoderes(Long id, List<Long> poderIds) {
        Optional<Heroe> heroeOpt = heroeRepository.findById(id);
        if (!heroeOpt.isPresent()) {
            throw new RuntimeException("Heroe no encontrado con id: " + id);
        }
        
        Heroe heroe = heroeOpt.get();
        List<Poder> poderes = poderRepository.findAllById(poderIds);
        heroe.setPoderes(poderes);
        
        return heroeRepository.save(heroe);
    }
    
    @Transactional
    public void eliminarHeroe(Long id) {
        heroeRepository.deleteById(id);
    }
}