package com.catering.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record QuoteItemDTO(
    @NotNull(message = "El ID del producto es obligatorio")
    Long productId, 
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima es 1")
    Integer quantity
) {}