package edu.sena.creamuebles.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "El nombre de la categoría es obligatorio")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String name,

        String description,
        String imageUrl,

        @NotNull(message = "El estado 'activo' es obligatorio")
        Boolean active,

        Integer sortOrder
) {}