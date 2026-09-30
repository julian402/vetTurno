package com.example.vetTurno.vetTurno.repository;

import com.example.vetTurno.vetTurno.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
}
