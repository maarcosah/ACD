package com.persistance.practica1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.persistance.practica1.entitites.Equipo;
import com.persistance.practica1.entitites.Heroe;
import com.persistance.practica1.entitites.Pasajeros;
import com.persistance.practica1.repositories.PasajerosRepository;
import com.persistance.practica1.services.EquipoService;
import com.persistance.practica1.services.HeroeService;

import org.springframework.ui.Model;

@Controller
public class WebController {

    @Autowired
    private PasajerosRepository repo;

    @Autowired
    private HeroeService repoHeroes;

    @Autowired
    private EquipoService repoEquipo;

    @GetMapping("/pasajeros")
    public String prueba (Model model)
    {
        Pasajeros p1 = new Pasajeros("Pepe", "Viyuela");
        Pasajeros p2 = new Pasajeros("Manolo", "Lama");

        repo.save(p1);
        repo.save(p2);

        model.addAttribute("id", p1.getIdPasajeros());
        model.addAttribute("nombre", p1.getNombre());


        // HEROES
        Heroe h1 = new Heroe("Torrente", "dinero");
        Heroe h2 = new Heroe("Mattias", "sabiduria");
        Heroe h3 = new Heroe("Javi", "Tik Tok");

        model.addAttribute("nombre", h1.getNombre());
        model.addAttribute("nombre", h2.getNombre());
        model.addAttribute("nombre", h3.getNombre());

        repoHeroes.guardarHeroe(h1);
        repoHeroes.guardarHeroe(h2);
        repoHeroes.guardarHeroe(h3);

        // EQUIPO
        Equipo e1 = new Equipo("Los mejores", null);
        repoEquipo.guardarEquipo(e1);

        return "pasajeros";
    }
}
