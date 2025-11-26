package com.persistance.practica1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.persistance.practica1.entitites.Poder;

@Repository
public interface PoderRepository extends JpaRepository<Poder, Long> {
    Optional<Poder> findFirstByNombre(String nombre);
}
