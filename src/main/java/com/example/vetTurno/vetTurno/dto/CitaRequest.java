package com.example.vetTurno.vetTurno.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CitaRequest {

    @NotNull(message = "La fecha y hora son obligatorias")
    @Future(message = "La cita debe ser en una fecha futura")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotNull(message = "La mascota es obligatoria")
    private Long mascotaId;

    @NotNull(message = "El veterinario es obligatorio")
    private Long veterinarioId;

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }
}
