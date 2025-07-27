// src/main/java/edu/sena/creamuebles/service/RecaptchaService.java
package edu.sena.creamuebles.service;

import edu.sena.creamuebles.dto.RecaptchaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class RecaptchaService {

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${recaptcha.secret.key}") // Inyecta la clave secreta desde application.properties
    private String recaptchaSecret;

    public boolean validateRecaptcha(String captchaToken) {
        if (captchaToken == null || captchaToken.isEmpty()) {
            log.warn("El token de reCAPTCHA es nulo o vacío.");
            return false;
        }

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", recaptchaSecret);
        requestMap.add("response", captchaToken);

        try {
            RecaptchaResponse apiResponse = restTemplate.postForObject(GOOGLE_RECAPTCHA_VERIFY_URL, requestMap, RecaptchaResponse.class);
            if (apiResponse == null) {
                log.error("La respuesta de la API de Google reCAPTCHA fue nula.");
                return false;
            }
            return apiResponse.isSuccess();
        } catch (Exception e) {
            log.error("Ocurrió un error al validar el token de reCAPTCHA.", e);
            return false;
        }
    }
}