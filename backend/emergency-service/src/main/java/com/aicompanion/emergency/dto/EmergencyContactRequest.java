package com.aicompanion.emergency.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContactRequest {
    @NotNull(message = "Patient-ID is required")
    private Integer patientId;

    @NotBlank(message = "CareGiver_Name is Required")
    private String caregiverName;

    @NotBlank(message = "CareGiver_Phone is required")
    private String caregiverNum;

    private Integer priorityOrder = 1;
}
