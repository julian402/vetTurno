package com.example.vetTurno.vetTurno.controller;

import com.example.vetTurno.vetTurno.dto.AuthResponse;
import com.example.vetTurno.vetTurno.dto.LoginRequest;
import com.example.vetTurno.vetTurno.dto.RegistroRequest;
import com.example.vetTurno.vetTurno.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegistroRequest req) {
        return ResponseEntity.ok(authService.registrar(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
