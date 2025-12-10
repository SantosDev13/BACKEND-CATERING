package com.catering.backend.dto;

import java.time.LocalDate; // Importante
import java.util.List; // Importante

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuoteDTO(
    @NotBlank(message = "El nombre del cliente es obligatorio")
    String clientName,

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El formato del correo no es válido")
    String clientEmail,

    @NotBlank(message = "El teléfono es obligatorio")
    String clientPhone,

    @NotNull(message = "La fecha del evento es obligatoria")
    @Future(message = "La fecha del evento debe ser en el futuro") // ¡Magia!
    LocalDate eventDate,

    @NotBlank(message = "El tipo de evento es obligatorio")
    String eventType,

    @NotNull(message = "La cantidad de invitados es obligatoria")
    @Min(value = 10, message = "El mínimo de invitados es 10") // Regla de negocio
    Integer guestCount,

    @NotBlank(message = "La ubicación es obligatoria")
    String location,

    String comments, // Este es opcional, no lleva validación

    @Valid // Valida también los objetos dentro de la lista
    List<QuoteItemDTO> items 
) {}