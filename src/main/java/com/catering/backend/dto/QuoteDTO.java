package com.catering.backend.dto;

import java.time.LocalDate;

/**
 * Record de Java 21. Define la estructura de datos que esperamos
 * recibir desde el Frontend (React).
 */
public record QuoteDTO(
    String clientName,
    String clientEmail,
    String clientPhone,
    LocalDate eventDate,
    String eventType,
    Integer guestCount,
    String location,
    String itemsOfInterest // Texto libre o lista formateada
) {}