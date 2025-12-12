package com.catering.backend.dto;

public record ProductRequestDTO(
    String name,
    String description,
    String category, // Aquí recibimos el texto (Ej: "MOBILIARIO")
    String imageUrl,
    Double price
) {}