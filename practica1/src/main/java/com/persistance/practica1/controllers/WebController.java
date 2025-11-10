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

    @GetMapping("/")
    public String index() {
        return "redirect:/pasajeros";
    }

    @GetMapping("/pruebas")
    public String pruebas(Model model) {
        return "pruebas";
    }

    @GetMapping("/pasajeros")
    public String listaPasajeros(Model model)
    {
        // Obtener todos los pasajeros
        var pasajeros = repo.findAll();
        model.addAttribute("pasajeros", pasajeros);
        
        // Obtener primer pasajero para mostrar en detalle
        if (pasajeros.iterator().hasNext()) {
            Pasajeros p1 = pasajeros.iterator().next();
            model.addAttribute("id", p1.getIdPasajeros());
            model.addAttribute("nombre", p1.getNombre());
        }

        return "pasajeros";
    }
}
