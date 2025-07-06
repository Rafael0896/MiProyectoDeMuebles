package edu.sena.creamuebles.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**") // Aplica la configuración CORS a todos los endpoints bajo /api/
                .allowedOrigins("http://localhost:3000") // Permite peticiones desde este orígen (React)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite estos métodos HTTP
                .allowedHeaders("*") // Permite todas las cabeceras
                .allowCredentials(true); // Permite el envío de cookies y credenciales de autenticación
    }
}