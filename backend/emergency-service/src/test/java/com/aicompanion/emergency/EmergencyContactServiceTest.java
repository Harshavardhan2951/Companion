package com.aicompanion.emergency;

import com.aicompanion.emergency.dto.EmergencyContactRequest;
import com.aicompanion.emergency.dto.EmergencyContactResponse;
import com.aicompanion.emergency.entity.User;
import com.aicompanion.emergency.repository.EmergencyContactRepository;
import com.aicompanion.emergency.repository.UserRepository;
import com.aicompanion.emergency.service.EmergencyContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmergencyContactServiceTest {

    @Autowired
    private EmergencyContactService contactService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmergencyContactRepository contactRepository;

    private User testUser;

    @BeforeEach
    public void setup() {
        testUser = User.builder()
                .fullName("Test Patient")
                .email("patient@test.com")
                .phoneNumber("1234567890")
                .build();
        testUser = userRepository.save(testUser);
        contactRepository.deleteAll();
    }

    @Test
    public void testAddContact() {
        EmergencyContactRequest request = EmergencyContactRequest.builder()
                .patientId(testUser.getId())
                .caregiverName("Mom")
                .caregiverPhone("9876543210")
                .priorityOrder(1)
                .build();

        EmergencyContactResponse response = contactService.createContact(request);

        assertNotNull(response.getId());
        assertEquals("Mom", response.getCaregiverName());
    }

    @Test
    public void testGetContacts() {
        EmergencyContactRequest req1 = EmergencyContactRequest.builder()
                .patientId(testUser.getId())
                .caregiverName("Mom")
                .caregiverPhone("9876543210")
                .priorityOrder(1)
                .build();

        contactService.createContact(req1);

        List<EmergencyContactResponse> contacts = contactService.getContactsByPatient(testUser.getId());

        assertEquals(1, contacts.size());
    }

    @Test
    public void testUpdateContact() {
        EmergencyContactRequest req = EmergencyContactRequest.builder()
                .patientId(testUser.getId())
                .caregiverName("Mom")
                .caregiverPhone("9876543210")
                .priorityOrder(1)
                .build();

        EmergencyContactResponse added = contactService.createContact(req);

        EmergencyContactRequest updateReq = EmergencyContactRequest.builder()
                .patientId(testUser.getId())
                .caregiverName("Dad")
                .caregiverPhone("8765432109")
                .priorityOrder(1)
                .build();

        EmergencyContactResponse updated = contactService.updateContact(added.getId(), updateReq);

        assertEquals("Dad", updated.getCaregiverName());
    }
}
