package com.aicompanion.emergency.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyEventRequest {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private String payload;

    private Boolean isExplicitSos;

    private String triggerType; // e.g., "KEYWORD", "FALL_DETECTION", "SOS_BUTTON"
}