package com.example.vetTurno.vetTurno.dto;

import com.example.vetTurno.vetTurno.model.Cita;

import java.time.LocalDateTime;

public class CitaDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String mascota;
    private String propietario;
    private String veterinario;

    public CitaDTO(Cita cita) {
        this.id = cita.getId();
        this.fechaHora = cita.getFechaHora();
        this.motivo = cita.getMotivo();
        this.mascota = cita.getMascota().getNombre();
        this.propietario = cita.getMascota().getPropietario().getNombre();
        this.veterinario = cita.getVeterinario().getNombre();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getMascota() {
        return mascota;
    }

    public String getPropietario() {
        return propietario;
    }

    public String getVeterinario() {
        return veterinario;
    }
}
