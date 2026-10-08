package com.aicompanion.emergency.service;

import com.aicompanion.emergency.dto.EmergencyEventRequest;
import com.aicompanion.emergency.dto.EmergencyEventResponse;
import com.aicompanion.emergency.entity.EmergencyEvent;
import com.aicompanion.emergency.entity.User;
import com.aicompanion.emergency.repository.EmergencyEventRepository;
import com.aicompanion.emergency.repository.EmergencyUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmergencyEventService {

    private final EmergencyEventRepository emergencyEventRepository;
    private final EmergencyUserRepository emergencyUserRepository;

    public static final Pattern EMERGENCY_CASE_MATTER = Pattern.compile(
            "\\b(help|fall|sos|emergency|fatigue|insomnia)\\b",
            Pattern.CASE_INSENSITIVE
    );

    public EmergencyEventResponse processEvent(EmergencyEventRequest request) {
        long startTime = System.currentTimeMillis();
        boolean triggered = request.getIsExplicitSos() ||
                (request.getPayload() != null &&
                        EMERGENCY_CASE_MATTER.matcher(request.getPayload()).find());

        String reason = triggered ? "Emergency Detected" : "No Trigger";

        User user = emergencyUserRepository.findById(request.getPatientId().intValue())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(triggered) {
            EmergencyEvent event = EmergencyEvent.builder()
                    .user(user)
                    .eventType(request.getTriggerType() != null ? request.getTriggerType() : "SOS")
                    .escalationStatus("ESCALATED")
                    .additionalInfo(request.getPayload())
                    .build();
            emergencyEventRepository.save(event);
            log.warn("EMERGENCY: User {} - {}", user.getId(), reason);
        }
        long latency = System.currentTimeMillis() - startTime;
        return EmergencyEventResponse.builder()
                .patientId(request.getPatientId())
                .emergencyTriggered(triggered)
                .reason(reason)
                .notifiedContactsCount(0)
                .processingTimeMs(latency)
                .build();
    }
}