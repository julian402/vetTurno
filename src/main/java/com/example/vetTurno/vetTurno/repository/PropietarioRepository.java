package com.example.vetTurno.vetTurno.repository;

import com.example.vetTurno.vetTurno.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
}
