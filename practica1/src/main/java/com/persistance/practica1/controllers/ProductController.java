package com.persistance.practica1.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.persistance.practica1.services.EquipoService;
import com.persistance.practica1.services.HeroeService;
import com.persistance.practica1.services.PoderService;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private HeroeService heroeService;
    
    @Autowired
    private EquipoService equipoService;
    
    @Autowired
    private PoderService poderService;

    // ==================== GET ENDPOINTS ====================
    
    /**
     * GET /api/users
     * Retorna información de usuarios/héroes en formato JSON
     */
    @GetMapping("users")
    public ResponseEntity<Map<String, Object>> obtenerUsers() {
        Map<String, Object> response = new HashMap<>();
        response.put("title", "API REST - Gestión de Héroes, Equipos y Poderes");
        response.put("version", "1.0.0");
        response.put("autor", "Marco Azcona");
        response.put("descripcion", "Backend REST API para gestionar héroes, equipos y poderes");
        response.put("endpoints", new String[]{
            "GET  /api/users - Información de la API",
            "GET  /api/heroes - Obtener todos los héroes",
            "GET  /api/equipos - Obtener todos los equipos",
            "GET  /api/poderes - Obtener todos los poderes",
            "POST /api/create - Crear nuevo héroe",
            "POST /api/equipment - Crear nuevo equipo",
            "POST /api/power - Crear nuevo poder"
        });
        
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/heroes
     * Retorna lista de todos los héroes
     */
    @GetMapping("heroes")
    public ResponseEntity<Map<String, Object>> obtenerHeroes() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("success", true);
            response.put("message", "Héroes obtenidos correctamente");
            response.put("count", heroeService.obtenerHeroes().size());
            response.put("data", heroeService.obtenerHeroes());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener héroes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/equipos
     * Retorna lista de todos los equipos
     */
    @GetMapping("equipos")
    public ResponseEntity<Map<String, Object>> obtenerEquipos() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("success", true);
            response.put("message", "Equipos obtenidos correctamente");
            response.put("count", equipoService.obtenerEquipos().size());
            response.put("data", equipoService.obtenerEquipos());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener equipos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/poderes
     * Retorna lista de todos los poderes
     */
    @GetMapping("poderes")
    public ResponseEntity<Map<String, Object>> obtenerPoderes() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            response.put("success", true);
            response.put("message", "Poderes obtenidos correctamente");
            response.put("count", poderService.obtenerPoderes().size());
            response.put("data", poderService.obtenerPoderes());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener poderes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== POST ENDPOINTS ====================

    /**
     * POST /api/create
     * Crea un nuevo héroe desde JSON
     * 
     * Body JSON:
     * {
     *   "nombre": "Nombre del héroe",
     *   "universo": "Universo",
     *   "superpoder": "Poder principal",
     *   "equipoId": 1
     * }
     */
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> crearHeroe(@RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que los campos requeridos existan
            if (!body.containsKey("nombre") || !body.containsKey("equipoId")) {
                response.put("success", false);
                response.put("message", "Campos requeridos: nombre, equipoId");
                return ResponseEntity.badRequest().body(response);
            }
            
            String nombre = (String) body.get("nombre");
            String universo = (String) body.getOrDefault("universo", "Desconocido");
            String superpoder = (String) body.getOrDefault("superpoder", "Sin definir");
            Long equipoId = ((Number) body.get("equipoId")).longValue();
            
            // Verificar que el equipo existe
            Optional<?> equipoOpt = equipoService.findById(equipoId);
            if (!equipoOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "El equipo con ID " + equipoId + " no existe");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Crear héroe
            com.persistance.practica1.entitites.Heroe nuevoHeroe = 
                new com.persistance.practica1.entitites.Heroe(nombre, superpoder);
            nuevoHeroe.setUniverso(universo);
            nuevoHeroe.setMiEquipo((com.persistance.practica1.entitites.Equipo) equipoOpt.get());
            
            com.persistance.practica1.entitites.Heroe heroGuardado = heroeService.crearHeroe(nuevoHeroe);
            
            response.put("success", true);
            response.put("message", "Héroe creado correctamente");
            response.put("data", heroGuardado);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear héroe: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * POST /api/equipment
     * Crea un nuevo equipo desde JSON
     * 
     * Body JSON:
     * {
     *   "nombre": "Nombre del equipo",
     *   "descripcion": "Descripción del equipo"
     * }
     */
    @PostMapping("equipment")
    public ResponseEntity<Map<String, Object>> crearEquipo(@RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!body.containsKey("nombre")) {
                response.put("success", false);
                response.put("message", "Campo requerido: nombre");
                return ResponseEntity.badRequest().body(response);
            }
            
            String nombre = (String) body.get("nombre");
            String descripcion = (String) body.getOrDefault("descripcion", "Sin descripción");
            
            com.persistance.practica1.entitites.Equipo nuevoEquipo = 
                new com.persistance.practica1.entitites.Equipo(nombre, descripcion);
            
            com.persistance.practica1.entitites.Equipo equipoGuardado = equipoService.crearEquipo(nuevoEquipo);
            
            response.put("success", true);
            response.put("message", "Equipo creado correctamente");
            response.put("data", equipoGuardado);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear equipo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * POST /api/power
     * Crea un nuevo poder desde JSON
     * 
     * Body JSON:
     * {
     *   "nombre": "Nombre del poder",
     *   "descripcion": "Descripción del poder"
     * }
     */
    @PostMapping("power")
    public ResponseEntity<Map<String, Object>> crearPoder(@RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!body.containsKey("nombre")) {
                response.put("success", false);
                response.put("message", "Campo requerido: nombre");
                return ResponseEntity.badRequest().body(response);
            }
            
            String nombre = (String) body.get("nombre");
            String descripcion = (String) body.getOrDefault("descripcion", "Sin descripción");
            
            com.persistance.practica1.entitites.Poder nuevoPoder = 
                new com.persistance.practica1.entitites.Poder(nombre, descripcion);
            
            com.persistance.practica1.entitites.Poder poderGuardado = poderService.crearPoder(nuevoPoder);
            
            response.put("success", true);
            response.put("message", "Poder creado correctamente");
            response.put("data", poderGuardado);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al crear poder: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
