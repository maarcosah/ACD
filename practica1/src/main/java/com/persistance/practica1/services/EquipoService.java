package com.persistance.practica1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.repositories.EquipoRepository;

import jakarta.transaction.Transactional;

@Service    
public class EquipoService {
    @Autowired
    EquipoRepository repo;

    // Lecturas
    List<Equipo> findAll(){
        return (List<Equipo>) repo.findAll();
    }   
    Optional<Equipo> findFirstByNombre(Long id){
        return repo.findById(id);
    }

    // ESCRITURAS
    @Transactional
    public void guardarEquipo(Equipo equipo){
        repo.save(equipo);
    }   
}