package edu.sena.creamuebles.security;

import edu.sena.creamuebles.model.User;
import edu.sena.creamuebles.repository.UserRepository;
import edu.sena.creamuebles.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Inyectamos el codificador de contraseñas

    @Value("${app.oauth2.redirect-uri:http://localhost:3000/oauth2/redirect}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String fullName = (String) attributes.get("name");

        // Buscamos al usuario por email. Si no existe, lo creamos.
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);

                    // Tu lógica para separar nombre y apellido está perfecta
                    String[] names = fullName.split(" ", 2);
                    newUser.setFirstName(names[0]);
                    if (names.length > 1) {
                        newUser.setLastName(names[1]);
                    } else {
                        newUser.setLastName("");
                    }

                    // Asignamos un rol por defecto
                    newUser.setRole(User.Role.USER);

                    // --- SOLUCIÓN A LOS ERRORES DE VALIDACIÓN Y TRUNCAMIENTO ---

                    // 1. Asignamos una contraseña aleatoria y la codificamos.
                    //    Esto es necesario para cumplir con la restricción de la base de datos.
                    String randomPassword = UUID.randomUUID().toString();
                    newUser.setPassword(passwordEncoder.encode(randomPassword));

                    // 2. Asignamos valores de relleno CORTOS para los campos de documento.
                    //    "OAUTH" es breve y no causará errores de truncamiento.
                    newUser.setDocumentType("OAUTH");
                    newUser.setDocumentNumber("000000");

                    // Guardamos el nuevo usuario completo y válido en la base de datos
                    return userRepository.save(newUser);
                });

        // Generamos el token JWT para el usuario (ya sea existente o nuevo)
        String token = jwtService.generateToken(user);

        // Construimos la URL de redirección para el frontend, incluyendo el token
        String targetUrl = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token", token)
                .build().toUriString();

        // Limpiamos los atributos de autenticación y redirigimos al frontend
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}