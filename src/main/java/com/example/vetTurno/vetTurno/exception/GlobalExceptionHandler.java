package com.example.vetTurno.vetTurno.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid falló: devuelve un error por cada campo inválido
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errores.putIfAbsent(fe.getField(), fe.getDefaultMessage());
        }
        return responder(HttpStatus.BAD_REQUEST, "Los datos enviados no son válidos", errores);
    }

    // Reglas del negocio: referencias inexistentes, fecha pasada, horario ocupado, email repetido
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ApiError> manejarReglaNegocio(ReglaNegocioException ex) {
        return responder(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    // JSON mal formado o fecha con formato incorrecto
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return responder(HttpStatus.BAD_REQUEST,
                "El cuerpo de la petición no es válido. Revisa el JSON y el formato de fecha (yyyy-MM-ddTHH:mm:ss)", null);
    }

    // Ej: /api/citas/veterinario/abc
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> manejarTipoInvalido(MethodArgumentTypeMismatchException ex) {
        return responder(HttpStatus.BAD_REQUEST, "El parámetro '" + ex.getName() + "' no es válido", null);
    }

    // Login con credenciales incorrectas
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> manejarAutenticacion(AuthenticationException ex) {
        return responder(HttpStatus.UNAUTHORIZED, "Email o contraseña incorrectos", null);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> manejarNoEncontrado(NoResourceFoundException ex) {
        return responder(HttpStatus.NOT_FOUND, "La ruta solicitada no existe", null);
    }

    // Cualquier fallo imprevisto: 500 sin trazas ni nombres internos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> manejarGeneral(Exception ex) {
        return responder(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado en el servidor", null);
    }

    private ResponseEntity<ApiError> responder(HttpStatus status, String mensaje, Map<String, String> errores) {
        return ResponseEntity.status(status).body(new ApiError(status.value(), mensaje, errores));
    }
}
