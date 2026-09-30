package com.example.vetTurno.vetTurno.exception;

// Error de una regla de VetTurno (referencia inexistente, fecha pasada, horario ocupado...).
// El GlobalExceptionHandler la convierte en 400 con el mensaje tal cual.
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
