package com.persistance.practica1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.persistance.practica1.entitites.Equipo;

@Repository
public interface EquipoRepository extends CrudRepository<Equipo, Long>{
        <Optional>Equipo findFirstByNombre(String argumento);
}
