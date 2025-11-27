package com.persistance.practica1.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.entitites.Poder;
import com.persistance.practica1.repositories.EquipoRepository;
import com.persistance.practica1.repositories.HeroeRepository;
import com.persistance.practica1.repositories.PoderRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private HeroeRepository heroeRepo;

    @Autowired
    private EquipoRepository equipoRepo;

    @Autowired
    private PoderRepository poderRepo;

    @Override
    public void run(String... args) throws Exception {
        // Verificar si la base de datos está vacía
        if (equipoRepo.count() == 0) {
            
            // ============ CREAR EQUIPOS ============
            Equipo equipoMarvel = new Equipo("Vengadores", "Equipo de héroes Marvel");
            Equipo equipoDC = new Equipo("Liga de la Justicia", "Equipo de héroes DC");
            equipoRepo.save(equipoMarvel);
            equipoRepo.save(equipoDC);
            System.out.println("✅ Equipos creados");
            
            // ============ CREAR PODERES ============
            Poder poderFuerzaSobrenatural = new Poder("Fuerza Sobrenatural", "Capacidad de levantar objetos muy pesados");
            Poder poderVuelo = new Poder("Vuelo", "Capacidad de volar por los aires");
            Poder poderVelocidad = new Poder("Velocidad", "Capacidad de moverse a velocidad extrema");
            Poder poderInteligencia = new Poder("Inteligencia Genial", "Capacidad de resolver problemas complejos");
            Poder poderLaserVision = new Poder("Visión Láser", "Disparo de rayos láser por los ojos");
            Poder poderRiqueza = new Poder("Riqueza", "Acceso a recursos tecnológicos avanzados");
            
            poderRepo.save(poderFuerzaSobrenatural);
            poderRepo.save(poderVuelo);
            poderRepo.save(poderVelocidad);
            poderRepo.save(poderInteligencia);
            poderRepo.save(poderLaserVision);
            poderRepo.save(poderRiqueza);
            System.out.println("✅ Poderes creados");
            
            // ============ CREAR HÉROES ============
            Heroe superman = new Heroe("Superman", "Vuelo");
            superman.setUniverso("DC Comics");
            superman.setMiEquipo(equipoDC);
            superman.setPoderes(Arrays.asList(poderFuerzaSobrenatural, poderVuelo, poderLaserVision));
            heroeRepo.save(superman);
            
            Heroe batman = new Heroe("Batman", "Inteligencia");
            batman.setUniverso("DC Comics");
            batman.setMiEquipo(equipoDC);
            batman.setPoderes(Arrays.asList(poderInteligencia, poderRiqueza));
            heroeRepo.save(batman);
            
            Heroe ironman = new Heroe("Iron Man", "Tecnología");
            ironman.setUniverso("Marvel");
            ironman.setMiEquipo(equipoMarvel);
            ironman.setPoderes(Arrays.asList(poderInteligencia, poderRiqueza));
            heroeRepo.save(ironman);
            
            Heroe flash = new Heroe("Flash", "Velocidad");
            flash.setUniverso("DC Comics");
            flash.setMiEquipo(equipoDC);
            flash.setPoderes(Arrays.asList(poderVelocidad));
            heroeRepo.save(flash);
            
            Heroe wonderwoman = new Heroe("Wonder Woman", "Combate");
            wonderwoman.setUniverso("DC Comics");
            wonderwoman.setMiEquipo(equipoDC);
            wonderwoman.setPoderes(Arrays.asList(poderFuerzaSobrenatural, poderVuelo));
            heroeRepo.save(wonderwoman);
            
            System.out.println("✅ Héroes creados con relaciones N-a-N de Poderes");
            System.out.println("✅ Datos iniciales cargados correctamente");
            
        } else {
            System.out.println("ℹ️ La base de datos ya contiene datos, omitiendo inicialización");
        }
    }
}

