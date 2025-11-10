package com.persistance.practica1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.entitites.Pasajeros;
import com.persistance.practica1.repositories.PasajerosRepository;
import com.persistance.practica1.services.EquipoService;
import com.persistance.practica1.services.HeroeService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PasajerosRepository pasajerosRepo;

    @Autowired
    private HeroeService heroeService;

    @Autowired
    private EquipoService equipoService;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si la base de datos está vacía
        if (pasajerosRepo.count() == 0) {
            // Crear pasajeros
            Pasajeros p1 = new Pasajeros("Pepe", "Viyuela");
            Pasajeros p2 = new Pasajeros("Manolo", "Lama");
            pasajerosRepo.save(p1);
            pasajerosRepo.save(p2);

            // Crear equipo
            Equipo e1 = new Equipo("Los mejores", "Super fuerza");
            equipoService.guardarEquipo(e1);

            // Crear héroes
            Heroe h1 = new Heroe("Torrente", "dinero");
            h1.setMiEquipo(e1);
            
            Heroe h2 = new Heroe("Mattias", "sabiduria");
            h2.setMiEquipo(e1);
            
            Heroe h3 = new Heroe("Javi", "Tik Tok");
            h3.setMiEquipo(e1);

            heroeService.guardarHeroe(h1);
            heroeService.guardarHeroe(h2);
            heroeService.guardarHeroe(h3);

            System.out.println("✅ Datos iniciales cargados correctamente");
        } else {
            System.out.println("ℹ️ La base de datos ya contiene datos, omitiendo inicialización");
        }
    }
}
