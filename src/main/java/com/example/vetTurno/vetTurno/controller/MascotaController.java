package com.example.vetTurno.vetTurno.controller;

import com.example.vetTurno.vetTurno.dto.MascotaDTO;
import com.example.vetTurno.vetTurno.dto.MascotaRequest;
import com.example.vetTurno.vetTurno.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public ResponseEntity<List<MascotaDTO>> obtenerMascotas() {
        return ResponseEntity.ok(mascotaService.listarMascotas());
    }

    @PostMapping
    public ResponseEntity<MascotaDTO> crearMascota(@Valid @RequestBody MascotaRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mascotaService.crearMascota(req));
    }
}
