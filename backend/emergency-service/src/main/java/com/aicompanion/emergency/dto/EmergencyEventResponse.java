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

    private Long id;
    private Integer userId;
    private String eventType;
    private String escalationStatus;
    private OffsetDateTime createdAt;
    private String additionalInfo;
}