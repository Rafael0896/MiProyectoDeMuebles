package edu.sena.creamuebles.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 50)
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 2, max = 50)
        String lastName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser un email válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password
) {}