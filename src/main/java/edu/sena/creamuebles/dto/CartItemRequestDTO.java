package edu.sena.creamuebles.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// DTO para recibir la petición de añadir un ítem al carrito.
public record CartItemRequestDTO(
        @NotNull(message = "El ID del producto es obligatorio")
        Long productId,

        @Min(value = 1, message = "La cantidad debe ser al menos 1")
        int quantity
) {}