package com.example.vetTurno.vetTurno.repository;

import com.example.vetTurno.vetTurno.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByVeterinarioIdAndFechaHora(Long veterinarioId, LocalDateTime fechaHora);

    List<Cita> findByVeterinarioIdOrderByFechaHoraAsc(Long veterinarioId);

    List<Cita> findAllByOrderByFechaHoraAsc();
}
