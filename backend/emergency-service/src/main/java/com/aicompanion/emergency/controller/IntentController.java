package com.aicompanion.emergency.controller;

import com.aicompanion.emergency.dto.IntentResponse;
import com.aicompanion.emergency.service.IntentParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency/intent")
@RequiredArgsConstructor
public class IntentController {

    private final IntentParser intentParser;

    @PostMapping
    public ResponseEntity<IntentResponse> classify(@RequestBody String text) {
        return ResponseEntity.ok(intentParser.classify(text));
    }
}