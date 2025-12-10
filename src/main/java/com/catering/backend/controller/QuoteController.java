package com.catering.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catering.backend.dto.QuoteDTO;
import com.catering.backend.service.QuoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService;

    /**
     * Endpoint POST para recibir el formulario desde React.
     * URL: http://localhost:8080/api/quotes
     */
    @PostMapping
    public ResponseEntity<?> requestQuote(@Valid @RequestBody QuoteDTO quoteDTO) {
        try {
            quoteService.createQuote(quoteDTO);
            return ResponseEntity.ok().body("{\"message\": \"Cotización enviada con éxito\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}