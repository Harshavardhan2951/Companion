package com.aicompanion.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyEventResponse {

    private Long eventId;            // Matches EmergencyEvent primary key type (Long)
    private Integer userId;          // Matches User primary key type (Integer)
    private String eventType;
    private String escalationStatus;
    private OffsetDateTime createdAt;
    private Boolean emergencyTriggered;
    private String reason;
    private Long processingTimeMs;
}