package com.aicompanion.emergency.controller;

import com.aicompanion.emergency.dto.EmergencyEventRequest;
import com.aicompanion.emergency.dto.EmergencyEventResponse;
import com.aicompanion.emergency.service.EmergencyEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency")
@RequiredArgsConstructor
@Slf4j
public class EmergencyEventController {

    private final EmergencyEventService eventService;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\":\"UP\"}");
    }

    @PostMapping("/process")
    public ResponseEntity<EmergencyEventResponse> processEvent(
            @RequestBody EmergencyEventRequest request
    ) {
        EmergencyEventResponse response = eventService.processEvent(request);
        return ResponseEntity.ok(response);
    }
}