package com.persistance.practica1.controllers;

import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.entitites.Poder;
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
            
            Poder nuevoPoder = new Poder(nombre, descripcion);
            
            Poder poderGuardado = poderService.crearPoder(nuevoPoder);
            
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

    // ==================== PUT ENDPOINTS ====================

    /**
     * PUT /api/update-heroe/{id}
     * Actualiza un héroe existente
     * 
     * Body JSON:
     * {
     *   "nombre": "Nuevo nombre",
     *   "universo": "Nuevo universo",
     *   "superpoder": "Nuevo poder",
     *   "equipoId": 1
     * }
     */
    @PutMapping("update-heroe/{id}")
    public ResponseEntity<Map<String, Object>> actualizarHeroe(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el héroe existe
            Optional<?> heroeOpt = heroeService.findById(id);
            if (!heroeOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "El héroe con ID " + id + " no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Heroe heroe = (Heroe) heroeOpt.get();
            
            // Actualizar campos si se proporcionan
            if (body.containsKey("nombre")) {
                heroe.setNombre((String) body.get("nombre"));
            }
            if (body.containsKey("universo")) {
                heroe.setUniverso((String) body.get("universo"));
            }
            if (body.containsKey("superpoder")) {
                heroe.setSuperpoder((String) body.get("superpoder"));
            }
            if (body.containsKey("equipoId")) {
                Long equipoId = ((Number) body.get("equipoId")).longValue();
                Optional<?> equipoOpt = equipoService.findById(equipoId);
                if (equipoOpt.isPresent()) {
                    heroe.setMiEquipo((Equipo) equipoOpt.get());
                } else {
                    response.put("success", false);
                    response.put("message", "El equipo con ID " + equipoId + " no existe");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                }
            }
            
            Heroe heroeActualizado = heroeService.guardarHeroe(heroe);
            
            response.put("success", true);
            response.put("message", "Héroe actualizado correctamente");
            response.put("data", heroeActualizado);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar héroe: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * PUT /api/update-equipo/{id}
     * Actualiza un equipo existente
     * 
     * Body JSON:
     * {
     *   "nombre": "Nuevo nombre",
     *   "descripcion": "Nueva descripción"
     * }
     */
    @PutMapping("update-equipo/{id}")
    public ResponseEntity<Map<String, Object>> actualizarEquipo(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el equipo existe
            Optional<?> equipoOpt = equipoService.findById(id);
            if (!equipoOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "El equipo con ID " + id + " no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Equipo equipo = (Equipo) equipoOpt.get();
            
            // Actualizar campos si se proporcionan
            if (body.containsKey("nombre")) {
                equipo.setNombre((String) body.get("nombre"));
            }
            if (body.containsKey("descripcion")) {
                equipo.setDescripcion((String) body.get("descripcion"));
            }
            
            Equipo equipoActualizado = equipoService.guardarEquipo(equipo);
            
            response.put("success", true);
            response.put("message", "Equipo actualizado correctamente");
            response.put("data", equipoActualizado);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar equipo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * PUT /api/update-poder/{id}
     * Actualiza un poder existente
     * 
     * Body JSON:
     * {
     *   "nombre": "Nuevo nombre",
     *   "descripcion": "Nueva descripción"
     * }
     */
    @PutMapping("update-poder/{id}")
    public ResponseEntity<Map<String, Object>> actualizarPoder(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el poder existe
            Optional<?> poderOpt = poderService.findById(id);
            if (!poderOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "El poder con ID " + id + " no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            Poder poder = (Poder) poderOpt.get();
            
            // Actualizar campos si se proporcionan
            if (body.containsKey("nombre")) {
                poder.setNombre((String) body.get("nombre"));
            }
            if (body.containsKey("descripcion")) {
                poder.setDescripcion((String) body.get("descripcion"));
            }
            
            Poder poderActualizado = poderService.guardarPoder(poder);
            
            response.put("success", true);
            response.put("message", "Poder actualizado correctamente");
            response.put("data", poderActualizado);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al actualizar poder: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== DELETE ENDPOINTS ====================

    /**
     * DELETE /api/delete-heroe/{id}
     * Elimina un héroe
     */
    @DeleteMapping("delete-heroe/{id}")
    public ResponseEntity<Map<String, Object>> eliminarHeroe(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el héroe existe
            Optional<?> heroeOpt = heroeService.findById(id);
            if (!heroeOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "El héroe con ID " + id + " no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            heroeService.eliminarHeroe(id);
            
            response.put("success", true);
            response.put("message", "Héroe eliminado correctamente");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar héroe: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * DELETE /api/delete-equipo/{id}
     * Elimina un equipo
     */
    @DeleteMapping("delete-equipo/{id}")
    public ResponseEntity<Map<String, Object>> eliminarEquipo(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el equipo existe
            Optional<?> equipoOpt = equipoService.findById(id);
            if (!equipoOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "El equipo con ID " + id + " no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            equipoService.eliminarEquipo(id);
            
            response.put("success", true);
            response.put("message", "Equipo eliminado correctamente");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar equipo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * DELETE /api/delete-poder/{id}
     * Elimina un poder
     */
    @DeleteMapping("delete-poder/{id}")
    public ResponseEntity<Map<String, Object>> eliminarPoder(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validar que el poder existe
            Optional<?> poderOpt = poderService.findById(id);
            if (!poderOpt.isPresent()) {
                response.put("success", false);
                response.put("message", "El poder con ID " + id + " no existe");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
            poderService.eliminarPoder(id);
            
            response.put("success", true);
            response.put("message", "Poder eliminado correctamente");
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al eliminar poder: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== BÚSQUEDA ENDPOINTS ====================

    /**
     * GET /api/search-heroe?nombre=Superman
     * Busca un héroe por nombre
     */
    @GetMapping("search-heroe")
    public ResponseEntity<Map<String, Object>> buscarHeroe(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<?> heroeOpt = heroeService.findByNombre(nombre);
            
            if (heroeOpt.isPresent()) {
                response.put("success", true);
                response.put("message", "Héroe encontrado");
                response.put("data", heroeOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Héroe no encontrado: " + nombre);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error en búsqueda de héroe: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/search-equipo?nombre=Vengadores
     * Busca un equipo por nombre
     */
    @GetMapping("search-equipo")
    public ResponseEntity<Map<String, Object>> buscarEquipo(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<?> equipoOpt = equipoService.findByNombre(nombre);
            
            if (equipoOpt.isPresent()) {
                response.put("success", true);
                response.put("message", "Equipo encontrado");
                response.put("data", equipoOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Equipo no encontrado: " + nombre);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error en búsqueda de equipo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/search-poder?nombre=Vuelo
     * Busca un poder por nombre
     */
    @GetMapping("search-poder")
    public ResponseEntity<Map<String, Object>> buscarPoder(@RequestParam String nombre) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<?> poderOpt = poderService.findByNombre(nombre);
            
            if (poderOpt.isPresent()) {
                response.put("success", true);
                response.put("message", "Poder encontrado");
                response.put("data", poderOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Poder no encontrado: " + nombre);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error en búsqueda de poder: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // ==================== DETALLE ENDPOINTS ====================

    /**
     * GET /api/heroe/{id}
     * Obtiene detalles de un héroe por ID
     */
    @GetMapping("heroe/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDetallesHeroe(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<?> heroeOpt = heroeService.findById(id);
            
            if (heroeOpt.isPresent()) {
                response.put("success", true);
                response.put("message", "Héroe encontrado");
                response.put("data", heroeOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Héroe no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener héroe: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/equipo/{id}
     * Obtiene detalles de un equipo por ID
     */
    @GetMapping("equipo/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDetallesEquipo(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<?> equipoOpt = equipoService.findById(id);
            
            if (equipoOpt.isPresent()) {
                response.put("success", true);
                response.put("message", "Equipo encontrado");
                response.put("data", equipoOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Equipo no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener equipo: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/poder/{id}
     * Obtiene detalles de un poder por ID
     */
    @GetMapping("poder/{id}")
    public ResponseEntity<Map<String, Object>> obtenerDetallesPoder(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<?> poderOpt = poderService.findById(id);
            
            if (poderOpt.isPresent()) {
                response.put("success", true);
                response.put("message", "Poder encontrado");
                response.put("data", poderOpt.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Poder no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error al obtener poder: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
