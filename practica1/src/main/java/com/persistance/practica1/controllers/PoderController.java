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

import com.persistance.practica1.entitites.Poder;
import com.persistance.practica1.entitites.PoderUpdateDTO;
import com.persistance.practica1.services.PoderService;

@RestController
@RequestMapping("/api/v1/poderes")
public class PoderController {

    @Autowired
    private PoderService poderService;
    
    // GET - LECTURA de todos los poderes
    @GetMapping
    public ResponseEntity<List<Poder>> obtenerPoderes() {
        List<Poder> poderes = poderService.obtenerPoderes();
        return ResponseEntity.ok(poderes);
    }
    
    // GET - LECTURA por ID
    @GetMapping("/{id}")
    public ResponseEntity<Poder> obtenerPoderPorId(@PathVariable Long id) {
        Optional<Poder> poder = poderService.findById(id);
        if (poder.isPresent()) {
            return ResponseEntity.ok(poder.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET - LECTURA por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Poder> obtenerPoderPorNombre(@PathVariable String nombre) {
        Optional<Poder> poder = poderService.findByNombre(nombre);
        if (poder.isPresent()) {
            return ResponseEntity.ok(poder.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // POST - CREAR nuevo poder
    @PostMapping
    public ResponseEntity<Poder> crearPoder(@RequestBody Poder poder) {
        try {
            Poder poderCreado = poderService.crearPoder(poder);
            return ResponseEntity.status(HttpStatus.CREATED).body(poderCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT - ACTUALIZAR poder
    @PutMapping("/{id}")
    public ResponseEntity<Poder> actualizarPoder(@PathVariable Long id, @RequestBody PoderUpdateDTO updateDTO) {
        try {
            Poder poderActualizado = poderService.actualizar(id, updateDTO);
            return ResponseEntity.ok(poderActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE - ELIMINAR poder
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPoder(@PathVariable Long id) {
        try {
            poderService.eliminarPoder(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
