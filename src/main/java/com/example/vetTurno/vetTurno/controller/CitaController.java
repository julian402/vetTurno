package com.example.vetTurno.vetTurno.controller;

import com.example.vetTurno.vetTurno.dto.CitaDTO;
import com.example.vetTurno.vetTurno.dto.CitaRequest;
import com.example.vetTurno.vetTurno.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public ResponseEntity<List<CitaDTO>> obtenerCitas() {
        return ResponseEntity.ok(citaService.listarCitas());
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<CitaDTO>> obtenerPorVeterinario(@PathVariable Long veterinarioId) {
        return ResponseEntity.ok(citaService.listarPorVeterinario(veterinarioId));
    }

    @PostMapping
    public ResponseEntity<CitaDTO> agendarCita(@Valid @RequestBody CitaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.agendarCita(req));
    }
}
