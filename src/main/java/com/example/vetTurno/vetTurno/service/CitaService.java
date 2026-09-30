package com.example.vetTurno.vetTurno.service;

import com.example.vetTurno.vetTurno.exception.ReglaNegocioException;
import com.example.vetTurno.vetTurno.dto.CitaDTO;
import com.example.vetTurno.vetTurno.dto.CitaRequest;
import com.example.vetTurno.vetTurno.model.Cita;
import com.example.vetTurno.vetTurno.model.Mascota;
import com.example.vetTurno.vetTurno.model.Veterinario;
import com.example.vetTurno.vetTurno.repository.CitaRepository;
import com.example.vetTurno.vetTurno.repository.MascotaRepository;
import com.example.vetTurno.vetTurno.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    public CitaService(CitaRepository citaRepository,
                       MascotaRepository mascotaRepository,
                       VeterinarioRepository veterinarioRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<CitaDTO> listarCitas() {
        return citaRepository.findAllByOrderByFechaHoraAsc()
                .stream()
                .map(CitaDTO::new)
                .toList();
    }

    public List<CitaDTO> listarPorVeterinario(Long veterinarioId) {
        return citaRepository.findByVeterinarioIdOrderByFechaHoraAsc(veterinarioId)
                .stream()
                .map(CitaDTO::new)
                .toList();
    }

    public CitaDTO agendarCita(CitaRequest req) {
        Mascota mascota = mascotaRepository.findById(req.getMascotaId())
                .orElseThrow(() -> new ReglaNegocioException(
                        "No existe una mascota con id " + req.getMascotaId()));

        Veterinario veterinario = veterinarioRepository.findById(req.getVeterinarioId())
                .orElseThrow(() -> new ReglaNegocioException(
                        "No existe un veterinario con id " + req.getVeterinarioId()));

        if (!req.getFechaHora().isAfter(LocalDateTime.now())) {
            throw new ReglaNegocioException("La cita debe ser en una fecha futura");
        }

        if (citaRepository.existsByVeterinarioIdAndFechaHora(veterinario.getId(), req.getFechaHora())) {
            throw new ReglaNegocioException(
                    "El veterinario " + veterinario.getNombre() + " ya tiene una cita en ese horario");
        }

        Cita cita = new Cita(req.getFechaHora(), req.getMotivo(), mascota, veterinario);
        return new CitaDTO(citaRepository.save(cita));
    }
}
