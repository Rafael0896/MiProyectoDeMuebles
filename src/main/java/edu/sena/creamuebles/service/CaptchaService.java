// En un nuevo paquete, por ejemplo, edu.sena.creamuebles.service
package edu.sena.creamuebles.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class CaptchaService {

    @Value("${recaptcha.secret.key}")
    private String recaptchaSecret;

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean validateCaptcha(String captchaResponse) {
        if (captchaResponse == null || captchaResponse.isEmpty()) {
            return false;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?secret=%s&response=%s", RECAPTCHA_VERIFY_URL, recaptchaSecret, captchaResponse);

        try {
            Map<String, Object> response = restTemplate.postForObject(url, null, Map.class);
            if (response != null && (Boolean) response.get("success")) {
                return true;
            }
        } catch (Exception e) {
            // Log the exception
            return false;
        }
        return false;
    }
}
    