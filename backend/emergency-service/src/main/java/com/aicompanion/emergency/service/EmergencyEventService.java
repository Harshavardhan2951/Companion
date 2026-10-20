package com.aicompanion.emergency.service;

import com.aicompanion.emergency.dto.EmergencyEventRequest;
import com.aicompanion.emergency.dto.EmergencyEventResponse;
import com.aicompanion.emergency.entity.EmergencyEvent;
import com.aicompanion.emergency.entity.User;
import com.aicompanion.emergency.repository.EmergencyEventRepository;
import com.aicompanion.emergency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyEventService {

    private final EmergencyEventRepository emergencyEventRepository;
    private final UserRepository userRepository;
    private final IntentParser intentParser;

    public EmergencyEventResponse processEvent(EmergencyEventRequest request) {
        long startTime = System.currentTimeMillis();
        boolean triggered = Boolean.TRUE.equals(request.getIsExplicitSos())
                || (request.getPayload() != null
                && "EMERGENCY".equals(intentParser.classify(request.getPayload()).getIntent()));
        String reason = triggered ? "Emergency Detected" : "No Trigger";

        User user = userRepository.findById(request.getPatientId().intValue())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        EmergencyEvent event = null;

        if (triggered) {
            event = EmergencyEvent.builder()
                    .user(user)
                    .eventType(Boolean.TRUE.equals(request.getIsExplicitSos()) ? "SOS" : "KEYWORD")
                    .escalationStatus("ESCALATED")
                    .additionalInfo(request.getPayload())
                    .build();
            event = emergencyEventRepository.save(event);
            log.warn("EMERGENCY: User {} - {}", user.getId(), reason);
        }

        long latency = System.currentTimeMillis() - startTime;

        return EmergencyEventResponse.builder()
                .eventId(event != null ? event.getId() : null)
                .userId(user.getId())
                .eventType(event != null ? event.getEventType() : request.getTriggerType())
                .escalationStatus(triggered ? "ESCALATED" : "NONE")
                .createdAt(event != null && event.getCreatedAt() != null ? event.getCreatedAt() : OffsetDateTime.now())
                .emergencyTriggered(triggered)
                .reason(reason)
                .processingTimeMs(latency)
                .build();
    }
}