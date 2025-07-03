package edu.sena.creamuebles.dto;

import edu.sena.creamuebles.model.Banner;
import java.time.LocalDateTime;

public record BannerResponseDTO(
        Long id,
        String title,
        String subtitle,
        String description,
        String imageUrl,
        String linkUrl,
        String linkText,
        boolean active,
        Integer sortOrder,
        Banner.BannerType type,
        LocalDateTime startDate,
        LocalDateTime endDate,
        boolean isCurrentlyActive // Campo calculado para el cliente
) {}