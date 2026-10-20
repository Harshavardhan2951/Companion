package com.aicompanion.emergency;

import com.aicompanion.emergency.dto.EmergencyEventRequest;
import com.aicompanion.emergency.dto.EmergencyEventResponse;
import com.aicompanion.emergency.entity.User;
import com.aicompanion.emergency.repository.EmergencyEventRepository;
import com.aicompanion.emergency.repository.UserRepository;
import com.aicompanion.emergency.service.EmergencyEventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmergencyEventServiceTest {

    @Autowired
    private EmergencyEventService eventService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmergencyEventRepository eventRepository;

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = User.builder()
                .fullName("Test User")
                .email("test@example.com")
                .phoneNumber("1234567890")
                .build();
        testUser = userRepository.save(testUser);
    }

    @Test
    public void testEmergencyKeywordDetection() {
        EmergencyEventRequest request = EmergencyEventRequest.builder()
                .patientId(testUser.getId().longValue())
                .payload("help me")
                .isExplicitSos(false)
                .build();

        EmergencyEventResponse response = eventService.processEvent(request);

        assertTrue(response.getEmergencyTriggered(), "Should detect 'help' keyword");
    }

    @Test
    public void testExplicitSOS() {
        EmergencyEventRequest request = EmergencyEventRequest.builder()
                .patientId(testUser.getId().longValue())
                .payload(null)
                .isExplicitSos(true)
                .build();

        EmergencyEventResponse response = eventService.processEvent(request);

        assertTrue(response.getEmergencyTriggered(), "Should trigger on explicit SOS");
    }

    @Test
    public void testNoEmergency() {
        EmergencyEventRequest request = EmergencyEventRequest.builder()
                .patientId(testUser.getId().longValue())
                .payload("what time is it")
                .isExplicitSos(false)
                .build();

        EmergencyEventResponse response = eventService.processEvent(request);

        assertFalse(response.getEmergencyTriggered(), "Should not trigger on normal text");
    }

    @Test
    public void testProcessingTimeUnder200ms() {
        EmergencyEventRequest request = EmergencyEventRequest.builder()
                .patientId(testUser.getId().longValue())
                .payload("I fell")
                .isExplicitSos(false)
                .build();

        EmergencyEventResponse response = eventService.processEvent(request);

        assertTrue(response.getProcessingTimeMs() < 200, "Processing should be <200ms");
    }
}
