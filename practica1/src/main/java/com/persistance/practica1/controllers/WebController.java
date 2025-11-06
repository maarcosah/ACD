package com.persistance.practica1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.persistance.practica1.entitites.Pasajeros;
import com.persistance.practica1.repositories.PasajerosRepository;

import org.springframework.ui.Model;

@Controller
public class WebController {

    @Autowired
    private PasajerosRepository repo;

    @GetMapping("/pasajeros")
    public String prueba (Model model)
    {
        Pasajeros p1 = new Pasajeros("Pepe", "Viyuela");
        Pasajeros p2 = new Pasajeros("Manolo", "Lama");

        repo.save(p1);
        repo.save(p2);

        model.addAttribute("id", p1.getIdPasajeros());
        model.addAttribute("nombre", p1.getNombre());

        return "pasajeros";
    }
}
