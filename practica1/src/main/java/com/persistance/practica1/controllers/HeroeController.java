package com.persistance.practica1.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.services.HeroeService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.persistance.practica1.entitites.HeroeUpdateDTO;

@RestController
@RequestMapping("/api/v1/heroes") 
public class HeroeController {

    // SERVICIOS
    @Autowired
    HeroeService service;
    
    // GET - LECTURAS
    @GetMapping("/{id}")
    public ResponseEntity<Heroe> getGroupById(@PathVariable Long id){
        Optional<Heroe> heroe = service.findFirstByNombre(id);
        if (heroe != null) {
            return ResponseEntity.ok((Heroe)heroe.orElse(null));
        }
        
        
        return ResponseEntity.notFound().build();

    }


    // PUT
    @PutMapping("path/{id}")
    public ResponseEntity<String> updateHeroe(@PathVariable Long id, @RequestBody HeroeUpdateDTO updateDTO){
        try {
            service.actualizar(id, updateDTO);
            return ResponseEntity.ok("Actualizado");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
    }
}}
