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

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.entitites.EquipoUpdateDTO;
import com.persistance.practica1.services.EquipoService;

@RestController
@RequestMapping("/api/v1/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;
    
    // GET - LECTURA de todos los equipos
    @GetMapping
    public ResponseEntity<List<Equipo>> obtenerEquipos() {
        List<Equipo> equipos = equipoService.obtenerEquipos();
        return ResponseEntity.ok(equipos);
    }
    
    // GET - LECTURA por ID
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtenerEquipoPorId(@PathVariable Long id) {
        Optional<Equipo> equipo = equipoService.findById(id);
        if (equipo.isPresent()) {
            return ResponseEntity.ok(equipo.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET - LECTURA por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Equipo> obtenerEquipoPorNombre(@PathVariable String nombre) {
        Optional<Equipo> equipo = equipoService.findByNombre(nombre);
        if (equipo.isPresent()) {
            return ResponseEntity.ok(equipo.get());
        }
        return ResponseEntity.notFound().build();
    }
    
    // POST - CREAR nuevo equipo
    @PostMapping
    public ResponseEntity<Equipo> crearEquipo(@RequestBody Equipo equipo) {
        try {
            Equipo equipoCreado = equipoService.crearEquipo(equipo);
            return ResponseEntity.status(HttpStatus.CREATED).body(equipoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    // PUT - ACTUALIZAR equipo
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizarEquipo(@PathVariable Long id, @RequestBody EquipoUpdateDTO updateDTO) {
        try {
            Equipo equipoActualizado = equipoService.actualizar(id, updateDTO);
            return ResponseEntity.ok(equipoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // PUT - ACTUALIZAR héroes del equipo
    @PutMapping("/{id}/heroes")
    public ResponseEntity<Equipo> actualizarHeroesEquipo(@PathVariable Long id, @RequestBody List<Long> heroeIds) {
        try {
            Equipo equipoActualizado = equipoService.actualizarHeroes(id, heroeIds);
            return ResponseEntity.ok(equipoActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE - ELIMINAR equipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEquipo(@PathVariable Long id) {
        try {
            equipoService.eliminarEquipo(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
