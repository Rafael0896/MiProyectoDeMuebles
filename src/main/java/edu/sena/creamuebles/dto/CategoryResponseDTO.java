package edu.sena.creamuebles.dto;

public record CategoryResponseDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        boolean active,
        Integer sortOrder,
        int productCount // Campo calculado para el cliente
) {}