package com.example.vetTurno.vetTurno.service;

import com.example.vetTurno.vetTurno.dto.PropietarioDTO;
import com.example.vetTurno.vetTurno.dto.PropietarioRequest;
import com.example.vetTurno.vetTurno.model.Propietario;
import com.example.vetTurno.vetTurno.repository.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public List<PropietarioDTO> listarPropietarios() {
        return propietarioRepository.findAll()
                .stream()
                .map(PropietarioDTO::new)
                .toList();
    }

    public PropietarioDTO crearPropietario(PropietarioRequest req) {
        Propietario propietario = new Propietario(req.getNombre(), req.getTelefono(), req.getEmail());
        return new PropietarioDTO(propietarioRepository.save(propietario));
    }
}
