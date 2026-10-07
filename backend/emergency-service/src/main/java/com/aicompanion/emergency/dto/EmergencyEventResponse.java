package com.aicompanion.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyEventResponse {

    private Long patientId;
    private Boolean emergencyTriggered;
    private String reason;
    private Integer notifiedContactsCount;
    private Long processingTimeMs;
}