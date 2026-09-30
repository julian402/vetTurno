package com.example.vetTurno.vetTurno.controller;

import com.example.vetTurno.vetTurno.dto.VeterinarioDTO;
import com.example.vetTurno.vetTurno.dto.VeterinarioRequest;
import com.example.vetTurno.vetTurno.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    @GetMapping
    public ResponseEntity<List<VeterinarioDTO>> obtenerVeterinarios() {
        return ResponseEntity.ok(veterinarioService.listarVeterinarios());
    }

    @PostMapping
    public ResponseEntity<VeterinarioDTO> crearVeterinario(@Valid @RequestBody VeterinarioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinarioService.crearVeterinario(req));
    }
}
