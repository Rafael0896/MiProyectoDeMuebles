// src/main/java/edu/sena/creamuebles/dto/RecaptchaResponse.java
package edu.sena.creamuebles.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data // Lombok para getters/setters
public class RecaptchaResponse {

    private boolean success;

    @JsonProperty("challenge_ts") // Mapea el campo "challenge_ts" del JSON
    private String challengeTs;

    private String hostname;

    @JsonProperty("error-codes") // Mapea el campo "error-codes"
    private List<String> errorCodes;
}