package com.example.vetTurno.vetTurno.dto;

import com.example.vetTurno.vetTurno.model.Veterinario;

public class VeterinarioDTO {
    private Long id;
    private String nombre;
    private String especialidad;

    public VeterinarioDTO(Veterinario veterinario) {
        this.id = veterinario.getId();
        this.nombre = veterinario.getNombre();
        this.especialidad = veterinario.getEspecialidad();
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }
}
