package edu.sena.creamuebles.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) que representa los datos enviados desde el formulario de registro.
 * Utiliza un 'record' de Java para ser conciso e inmutable.
 * Las anotaciones de validación aseguran que los datos sean correctos antes de procesarlos.
 */
public record UserRegistrationDTO(

        @NotBlank(message = "El correo electrónico es obligatorio")
        @Email(message = "Debe ser un correo electrónico válido")
        String email,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
        String firstName,

        @NotBlank(message = "El apellido es obligatorio")
        @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
        String lastName,

        @NotBlank(message = "El tipo de documento es obligatorio")
        String documentType,

        @NotBlank(message = "El número de documento es obligatorio")
        @Size(min = 5, max = 20, message = "El número de documento debe tener entre 5 y 20 caracteres")
        String documentNumber,

        // Este campo es opcional, por eso no lleva @NotBlank
        String phone,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        String password,

        // Este campo es solo para validación, no se guarda en la base de datos.
        @NotBlank(message = "La confirmación de la contraseña es obligatoria")
        String confirmPassword,

        // Mantenemos el token de reCAPTCHA como un campo obligatorio.
        @NotBlank(message = "El token de CAPTCHA es obligatorio")
        String captchaToken
) {
}