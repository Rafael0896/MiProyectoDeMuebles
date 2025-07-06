package edu.sena.creamuebles.controller;

import edu.sena.creamuebles.dto.LoginRequestDTO;
import edu.sena.creamuebles.dto.LoginResponseDTO;
import edu.sena.creamuebles.dto.UserRegistrationDTO;
import edu.sena.creamuebles.dto.UserResponseDTO;
import edu.sena.creamuebles.model.User;
// <-- CAMBIO 1: Importar el JwtService
import edu.sena.creamuebles.service.JwtService;
import edu.sena.creamuebles.service.UserService;
import jakarta.validation.Valid;
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
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    // <-- CAMBIO 2: Inyectar el servicio para generar tokens JWT
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO registrationDTO) {
        UserResponseDTO newUser = userService.registerNewUser(registrationDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Endpoint para autenticar un usuario y obtener un token.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        // 1. Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        // 2. Si la autenticación es exitosa, establecerla en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3. Obtener los detalles del usuario autenticado
        // Spring Security ahora usa UserDetails, así que lo obtenemos de ahí.
        // El casting a tu clase User funcionará si tu UserDetailsService lo devuelve así.
        User userDetails = (User) authentication.getPrincipal();

        // <-- CAMBIO 3: Generar un token JWT REAL usando el servicio
        String token = jwtService.generateToken(userDetails);

        // 5. Crear el DTO de respuesta con el token y los datos del usuario
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