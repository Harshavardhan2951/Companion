package com.aicompanion.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmergencyContactResponse {

    private Integer id;
    private Integer patientId;
    private String caregiverName;
    private String caregiverPhone;
    private Integer priorityOrder;
    private OffsetDateTime createdAt;
}
