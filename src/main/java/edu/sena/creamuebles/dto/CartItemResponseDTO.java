package edu.sena.creamuebles.dto;

import java.math.BigDecimal;

// DTO para representar un ítem del carrito en las respuestas.
public record CartItemResponseDTO(
        Long itemId,
        Long productId,
        String productName,
        String productImageUrl,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal subtotal
) {}