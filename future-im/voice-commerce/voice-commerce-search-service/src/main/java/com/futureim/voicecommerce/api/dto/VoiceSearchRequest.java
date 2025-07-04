package com.futureim.voicecommerce.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VoiceSearchRequest {
    @NotBlank(message = "Voice command cannot be empty")
    private String voiceCommand;
}
