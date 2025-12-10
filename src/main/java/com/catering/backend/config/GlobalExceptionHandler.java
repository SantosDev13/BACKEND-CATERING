package com.catering.backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Esto le dice a Spring: "Escucha todos los errores de todos los controladores"
public class GlobalExceptionHandler {

    // 1. Capturar errores de validación (Fecha pasada, email malo, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // Extraemos cada campo que falló y su mensaje
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
            
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // 2. Capturar errores generales (Base de datos caída, NullPointer, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Ocurrió un error inesperado en el servidor.");
        error.put("detalle", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}