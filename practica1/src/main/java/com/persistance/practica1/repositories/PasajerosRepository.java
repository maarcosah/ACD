package com.persistance.practica1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.persistance.practica1.entitites.Pasajeros;

@Repository
public interface PasajerosRepository extends CrudRepository<Pasajeros, Long>{
    
}
