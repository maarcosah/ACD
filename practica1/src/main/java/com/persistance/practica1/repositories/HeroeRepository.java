package com.persistance.practica1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persistance.practica1.entitites.Heroe;

@Repository
public interface HeroeRepository extends JpaRepository<Heroe, Long> {
    Optional<Heroe> findById(Long id);
    Optional<Heroe> findFirstByNombre(String nombre);

    @Query(value = "select * from Heroe where id = ?1", nativeQuery = true)
    List<Heroe> findCustomizado(Long id);
}

