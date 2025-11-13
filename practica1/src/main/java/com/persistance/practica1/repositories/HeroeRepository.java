package com.persistance.practica1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.persistance.practica1.entitites.Heroe;

@Repository
public interface HeroeRepository extends CrudRepository<Heroe, Long>{
        Optional<Heroe> findById(int id);
        Optional<Heroe> findFirstByNombre(String argumento);

        @Query(value = "select * from heroes where id = ?1", nativeQuery = true)
        List<Heroe> findCustomizado(int id);
}
