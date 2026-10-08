package com.aicompanion.emergency.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyEventRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    private String payload;

    private Boolean isExplicitSos;

    private String triggerType; // e.g., "KEYWORD", "FALL_DETECTION", "SOS_BUTTON"
}