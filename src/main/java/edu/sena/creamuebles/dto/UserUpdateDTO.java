package edu.sena.creamuebles.dto;

import edu.sena.creamuebles.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 50)
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 2, max = 50)
        String lastName,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Debe ser un email válido")
        String email,

        String phone,
        String address,

        @NotNull
        User.Role role,

        @NotNull
        Boolean enabled
) {}