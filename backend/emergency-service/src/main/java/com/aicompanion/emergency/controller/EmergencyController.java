package com.aicompanion.emergency.controller;

import com.aicompanion.emergency.dto.EmergencyEventRequest;
import com.aicompanion.emergency.dto.EmergencyEventResponse;
import com.aicompanion.emergency.service.EmergencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/emergency")
@RequiredArgsConstructor
public class EmergencyController {

    private final EmergencyService emergencyService;

    @PostMapping("/eval")
    public ResponseEntity<EmergencyEventResponse> evaluateEmergency(
            @Valid @RequestBody EmergencyEventRequest request) {
        EmergencyEventResponse response = emergencyService.processEvent(request);
        return ResponseEntity.ok(response);
    }
}