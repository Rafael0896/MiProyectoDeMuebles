package edu.sena.creamuebles.dto;

// DTO para la respuesta de un login exitoso
public record LoginResponseDTO(
        String token,
        UserResponseDTO user
) {}