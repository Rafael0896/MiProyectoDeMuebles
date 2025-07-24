package edu.sena.creamuebles.controller;

import edu.sena.creamuebles.dto.LoginRequestDTO;
import edu.sena.creamuebles.dto.LoginResponseDTO;
import edu.sena.creamuebles.dto.UserRegistrationDTO;
import edu.sena.creamuebles.dto.UserResponseDTO;
import edu.sena.creamuebles.model.User;
import edu.sena.creamuebles.service.CaptchaService;
import edu.sena.creamuebles.service.JwtService;
import edu.sena.creamuebles.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CaptchaService captchaService;

    /**
     * Endpoint para registrar un nuevo usuario.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        // 1. Validar el CAPTCHA primero
        // --- CORRECCIÓN: Volvemos a la sintaxis de record ---
        if (!captchaService.validateCaptcha(registrationDTO.captchaToken())) {
            // Si el CAPTCHA es inválido, rechaza la petición.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: CAPTCHA inválido.");
        }

        // 2. Si es válido, proceder con el registro.
        UserResponseDTO newUser = userService.registerNewUser(registrationDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Endpoint para autenticar un usuario y obtener un token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        // 1. Validar el CAPTCHA primero
        // --- CORRECCIÓN: Volvemos a la sintaxis de record ---
        if (!captchaService.validateCaptcha(loginRequest.captchaToken())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: CAPTCHA inválido.");
        }

        // 2. Si es válido, proceder con la autenticación.
        Authentication authentication = authenticationManager.authenticate(
                // --- CORRECCIÓN: También para email y password ---
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDetails = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        UserResponseDTO userDTO = new UserResponseDTO(
                userDetails.getId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getFullName(),
                userDetails.getEmail(),
                userDetails.getPhone(),
                userDetails.getAddress(),
                userDetails.getRole()
        );

        return ResponseEntity.ok(new LoginResponseDTO(token, userDTO));
    }
}