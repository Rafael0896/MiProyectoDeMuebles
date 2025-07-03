package edu.sena.creamuebles.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

// Usado para recibir datos en las peticiones POST y PUT
public record ProductRequestDTO(
        @NotBlank(message = "El nombre del producto es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String name,

        String description,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @DecimalMin(value = "0.0", message = "El precio de oferta no puede ser negativo")
        BigDecimal salePrice,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
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

        @NotNull(message = "El ID de la categoría es obligatorio")
        Long categoryId
) {}