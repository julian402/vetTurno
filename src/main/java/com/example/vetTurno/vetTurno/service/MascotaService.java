package com.example.vetTurno.vetTurno.service;

import com.example.vetTurno.vetTurno.dto.MascotaDTO;
import com.example.vetTurno.vetTurno.dto.MascotaRequest;
import com.example.vetTurno.vetTurno.model.Mascota;
import com.example.vetTurno.vetTurno.model.Propietario;
import com.example.vetTurno.vetTurno.repository.MascotaRepository;
import com.example.vetTurno.vetTurno.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final PropietarioRepository propietarioRepository;

    public MascotaService(MascotaRepository mascotaRepository,
                          PropietarioRepository propietarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
    }

    public List<MascotaDTO> listarMascotas() {
        return mascotaRepository.findAll()
                .stream()
                .map(MascotaDTO::new)
                .toList();
    }

    public MascotaDTO crearMascota(MascotaRequest req) {
        Propietario propietario = propietarioRepository.findById(req.getPropietarioId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un propietario con id " + req.getPropietarioId()));

        Mascota mascota = new Mascota(req.getNombre(), req.getEspecie(), req.getRaza(), propietario);
        return new MascotaDTO(mascotaRepository.save(mascota));
    }
}
