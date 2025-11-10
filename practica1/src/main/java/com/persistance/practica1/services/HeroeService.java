package com.persistance.practica1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.entitites.HeroeUpdateDTO;
import com.persistance.practica1.repositories.HeroeRepository;

import jakarta.transaction.Transactional;

@Service    
public class HeroeService {
    @Autowired
    HeroeRepository repo;

    // Lecturas
    public List<Heroe> obtenerHeroes(){
        return (List<Heroe>) repo.findAll();
    }   
    public Optional<Heroe> findFirstByNombre(Long id){
        return repo.findById(id);
    }

    // ESCRITURAS
    @Transactional
    public Heroe guardarHeroe(Heroe heroe){
        return repo.save(heroe);
    }
    public void actualizar(Long id, HeroeUpdateDTO updateDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }
}