package com.persistance.practica1.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.entitites.HeroeUpdateDTO;
import com.persistance.practica1.services.HeroeService;

@RestController
@RequestMapping("/api/v1/heroes")
public class HeroeController {

    @Autowired
    private HeroeService heroeService;
    
    // GET - LECTURA de todos los héroes
    @GetMapping
    public ResponseEntity<List<Heroe>> obtenerHeroes() {
        List<Heroe> heroes = heroeService.obtenerHeroes();
        return ResponseEntity.ok(heroes);
    }
    
    // GET - LECTURA por ID
    @GetMapping("/{id}")
    public ResponseEntity<Heroe> obtenerHeroePorId(@PathVariable Long id) {
        Optional<Heroe> heroe = heroeService.findById(id);
        if (heroe.isPresent()) {
            return ResponseEntity.ok(heroe.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET - LECTURA por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Heroe> obtenerHeroePorNombre(@PathVariable String nombre) {
        Optional<Heroe> heroe = heroeService.findByNombre(nombre);
        if (heroe.isPresent()) {
            return ResponseEntity.ok(heroe.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // POST - CREAR nuevo héroe
    @PostMapping
    public ResponseEntity<Heroe> crearHeroe(@RequestBody Heroe heroe) {
        try {
            Heroe heroCreado = heroeService.crearHeroe(heroe);
            return ResponseEntity.status(HttpStatus.CREATED).body(heroCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT - ACTUALIZAR héroe
    @PutMapping("/{id}")
    public ResponseEntity<Heroe> actualizarHeroe(@PathVariable Long id, @RequestBody HeroeUpdateDTO updateDTO) {
        try {
            Heroe heroeActualizado = heroeService.actualizar(id, updateDTO);
            return ResponseEntity.ok(heroeActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PUT - ACTUALIZAR poderes del héroe
    @PutMapping("/{id}/poderes")
    public ResponseEntity<Heroe> actualizarPoderesHeroe(@PathVariable Long id, @RequestBody List<Long> poderIds) {
        try {
            Heroe heroeActualizado = heroeService.actualizarPoderes(id, poderIds);
            return ResponseEntity.ok(heroeActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE - ELIMINAR héroe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHeroe(@PathVariable Long id) {
        try {
            heroeService.eliminarHeroe(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

