package edu.sena.creamuebles.config;

import edu.sena.creamuebles.service.UserService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es", "MX")); // O "es", "CO" según tu país
        return slr;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
        http
                // CSRF está habilitado por defecto en aplicaciones con estado, lo cual es bueno.
                // .csrf(csrf -> csrf.disable()) // Podemos dejar que Spring lo maneje.

                // Definimos las reglas de autorización para cada endpoint
                .authorizeHttpRequests(auth -> auth
                        // === ENDPOINTS DE VISTAS Y RECURSOS PÚBLICOS ===
                        .requestMatchers("/", "/home", "/register", "/products", "/products/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                        .requestMatchers("/cart/**").permitAll()
                        // === ENDPOINTS DE API PÚBLICOS ===
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/banners/**").permitAll()

                        // === CUALQUIER OTRA PETICIÓN REQUIERE AUTENTICACIÓN ===
                        .anyRequest().authenticated()
                )

                // === CONFIGURACIÓN DEL FORMULARIO DE LOGIN ===
                .formLogin(form -> form
                        .loginPage("/login") // URL de nuestra página de login personalizada
                        .loginProcessingUrl("/login") // La URL que Spring Security vigilará para procesar el login
                        .defaultSuccessUrl("/products", true) // A dónde redirigir tras un login exitoso
                        .permitAll() // Permitir a todos acceder a la página de login
                )

                // === CONFIGURACIÓN DEL LOGOUT ===
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL para activar el logout
                        .logoutSuccessUrl("/login?logout") // A dónde ir tras cerrar sesión exitosamente
                        .permitAll()
                )

                // Le decimos a Spring que use nuestro proveedor de autenticación personalizado.
                .authenticationProvider(authenticationProvider);

        return http.build();
    }

}