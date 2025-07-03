package edu.sena.creamuebles.dto;

import edu.sena.creamuebles.model.User;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String fullName,
        String email,
        String phone,
        String address,
        User.Role role
) {}