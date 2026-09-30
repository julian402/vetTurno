package com.example.vetTurno.vetTurno.service;

import com.example.vetTurno.vetTurno.exception.ReglaNegocioException;
import com.example.vetTurno.vetTurno.dto.AuthResponse;
import com.example.vetTurno.vetTurno.dto.LoginRequest;
import com.example.vetTurno.vetTurno.dto.RegistroRequest;
import com.example.vetTurno.vetTurno.model.Rol;
import com.example.vetTurno.vetTurno.model.Usuario;
import com.example.vetTurno.vetTurno.repository.UsuarioRepository;
import com.example.vetTurno.vetTurno.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(RegistroRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new ReglaNegocioException("Ya existe un usuario con ese email");
        }

        // El registro siempre asigna USER, aunque el cliente envíe otro rol
        Usuario usuario = new Usuario(req.getEmail(), passwordEncoder.encode(req.getPassword()), Rol.USER);
        usuarioRepository.save(usuario);

        return new AuthResponse(jwtService.generarToken(usuario.getEmail(), usuario.getRol().name()));
    }

    public AuthResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        Usuario usuario = usuarioRepository.findByEmail(req.getEmail()).orElseThrow();
        return new AuthResponse(jwtService.generarToken(usuario.getEmail(), usuario.getRol().name()));
    }
}
