package com.aicompanion.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntentResponse {
    private String intent;        // EMERGENCY, ROUTINE
    private Double confidence;    // 0.0 to 1.0
    private String matchedKeyword; // keyword that triggered the match, null if none
    private Long latencyMs;
}
