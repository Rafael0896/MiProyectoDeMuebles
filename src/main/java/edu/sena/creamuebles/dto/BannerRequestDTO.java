package edu.sena.creamuebles.dto;

import edu.sena.creamuebles.model.Banner;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record BannerRequestDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
        String title,

        String subtitle,
        String description,
        String imageUrl,
        String linkUrl,
        String linkText,

        @NotNull(message = "El estado 'activo' es obligatorio")
        Boolean active,

        Integer sortOrder,

        @NotNull(message = "El tipo de banner es obligatorio")
        Banner.BannerType type,

        LocalDateTime startDate,
        LocalDateTime endDate
) {}