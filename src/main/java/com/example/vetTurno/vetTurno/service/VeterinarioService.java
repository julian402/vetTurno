package com.example.vetTurno.vetTurno.service;

import com.example.vetTurno.vetTurno.dto.VeterinarioDTO;
import com.example.vetTurno.vetTurno.dto.VeterinarioRequest;
import com.example.vetTurno.vetTurno.model.Veterinario;
import com.example.vetTurno.vetTurno.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    public List<VeterinarioDTO> listarVeterinarios() {
        return veterinarioRepository.findAll()
                .stream()
                .map(VeterinarioDTO::new)
                .toList();
    }

    public VeterinarioDTO crearVeterinario(VeterinarioRequest req) {
        Veterinario veterinario = new Veterinario(req.getNombre(), req.getEspecialidad());
        return new VeterinarioDTO(veterinarioRepository.save(veterinario));
    }
}
