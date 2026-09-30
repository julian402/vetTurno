package com.example.vetTurno.vetTurno.controller;

import com.example.vetTurno.vetTurno.dto.PropietarioDTO;
import com.example.vetTurno.vetTurno.dto.PropietarioRequest;
import com.example.vetTurno.vetTurno.service.PropietarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @GetMapping
    public ResponseEntity<List<PropietarioDTO>> obtenerPropietarios() {
        return ResponseEntity.ok(propietarioService.listarPropietarios());
    }

    @PostMapping
    public ResponseEntity<PropietarioDTO> crearPropietario(@Valid @RequestBody PropietarioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propietarioService.crearPropietario(req));
    }
}
