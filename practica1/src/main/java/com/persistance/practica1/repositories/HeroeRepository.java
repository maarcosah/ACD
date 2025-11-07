package com.persistance.practica1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.persistance.practica1.entitites.Heroe;

@Repository
public interface HeroeRepository extends CrudRepository<Heroe, Long>{
        <Optional>Heroe findFirstByNombre(String argumento);
}
