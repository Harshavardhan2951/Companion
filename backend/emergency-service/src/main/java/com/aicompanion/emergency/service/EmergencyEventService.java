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
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyEventService {

    private final EmergencyEventRepository emergencyEventRepository;
    private final UserRepository userRepository;

    public static final Pattern EMERGENCY_CASE_MATTER = Pattern.compile(
            "\\b(help|fall|sos|emergency|fatigue|insomnia)\\b",
            Pattern.CASE_INSENSITIVE
    );

    public EmergencyEventResponse processEvent(EmergencyEventRequest request) {
        long startTime = System.currentTimeMillis();
        boolean triggered = Boolean.TRUE.equals(request.getIsExplicitSos()) ||
                (request.getPayload() != null &&
                        EMERGENCY_CASE_MATTER.matcher(request.getPayload()).find());

        String reason = triggered ? "Emergency Detected" : "No Trigger";

        User user = userRepository.findById(request.getUserId().intValue())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        EmergencyEvent event = null;

        if (triggered) {
            event = EmergencyEvent.builder()
                    .user(user)
                    .eventType(request.getTriggerType() != null ? request.getTriggerType() : "SOS")
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