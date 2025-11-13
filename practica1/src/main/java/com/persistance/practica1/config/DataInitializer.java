package com.persistance.practica1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.entitites.Pasajeros;
import com.persistance.practica1.repositories.EquipoRepository;
import com.persistance.practica1.repositories.HeroeRepository;
import com.persistance.practica1.repositories.PasajerosRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PasajerosRepository pasajerosRepo;

    @Autowired
    private HeroeRepository heroeRepo;

    @Autowired
    private EquipoRepository equipoRepo;

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
            equipoRepo.save(e1);

            // Crear héroes
            Heroe h1 = new Heroe("Torrente", "dinero");
            h1.setMiEquipo(e1);
            
            Heroe h2 = new Heroe("Mattias", "sabiduria");
            h2.setMiEquipo(e1);
            
            Heroe h3 = new Heroe("Javi", "Tik Tok");
            h3.setMiEquipo(e1);

            heroeRepo.save(h1);
            heroeRepo.save(h2);
            heroeRepo.save(h3);

            System.out.println("✅ Datos iniciales cargados correctamente");
        } else {
            System.out.println("ℹ️ La base de datos ya contiene datos, omitiendo inicialización");
        }
    }
}
