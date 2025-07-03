package edu.sena.creamuebles.dto;

import java.math.BigDecimal;
import java.util.List;

// DTO para representar el carrito completo en las respuestas.
public record CartResponseDTO(
        Long id,
        int totalItems,       // Suma de las cantidades de todos los productos
        int totalProducts,    // Número de productos distintos
        BigDecimal totalAmount, // Costo total
        List<CartItemResponseDTO> items
) {}