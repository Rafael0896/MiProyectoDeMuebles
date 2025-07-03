package edu.sena.creamuebles.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Usado para enviar datos en las respuestas GET
public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        BigDecimal salePrice,
        Integer stock,
        String sku,
        String imageUrl,
        String additionalImages,
        BigDecimal weight,
        String dimensions,
        String material,
        String color,
        boolean featured,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String categoryName, // Enviamos el nombre de la categoría, más útil para el frontend

        // Campos calculados para conveniencia del cliente
        BigDecimal effectivePrice,
        boolean onSale,
        boolean inStock,
        BigDecimal discountPercentage
) {}