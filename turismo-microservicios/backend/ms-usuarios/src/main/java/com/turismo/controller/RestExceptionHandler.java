package com.turismo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", "Datos de entrada inválidos");
        body.put("errores", errors);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        String message = ex.getMessage() == null ? "Solicitud inválida" : ex.getMessage();
        HttpStatus status = resolveStatus(message);

        Map<String, String> body = new HashMap<>();
        body.put("mensaje", message);
        return ResponseEntity.status(status).body(body);
    }

    private HttpStatus resolveStatus(String message) {
        String normalized = message.toLowerCase();
        if (normalized.contains("no encontrado") || normalized.contains("no existe")) {
            return HttpStatus.NOT_FOUND;
        }
        if (normalized.contains("ya está registrado")) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
