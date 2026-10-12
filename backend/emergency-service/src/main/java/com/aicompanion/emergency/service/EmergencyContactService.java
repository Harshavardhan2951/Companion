package com.aicompanion.emergency.service;

import com.aicompanion.emergency.dto.EmergencyContactRequest;
import com.aicompanion.emergency.dto.EmergencyContactResponse;
import com.aicompanion.emergency.entity.EmergencyContact;
import com.aicompanion.emergency.entity.User;
import com.aicompanion.emergency.repository.EmergencyContactRepository;
import com.aicompanion.emergency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmergencyContactService {

    private final EmergencyContactRepository contactRepository;
    private final UserRepository userRepository;

    public EmergencyContactResponse createContact(
            EmergencyContactRequest contactRequest
    ) {
        User patient = userRepository.findById(contactRequest.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient ID not found"));

        EmergencyContact contact = EmergencyContact.builder()
                .patient(patient)
                .caregiverName(contactRequest.getCaregiverName())
                .caregiverPhone(contactRequest.getCaregiverPhone())
                .priorityOrder(contactRequest.getPriorityOrder())
                .build();

        contact = contactRepository.save(contact);
        log.info("Emergency contact added for patient {}", contactRequest.getPatientId());

        return mapToResponse(contact);
    }

    public List<EmergencyContactResponse> getContactsByPatient(Integer patientId) {
        return contactRepository.findByPatientIdOrderByPriorityOrderAsc(patientId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public EmergencyContactResponse updateContact(Integer contactId, EmergencyContactRequest request) {
        EmergencyContact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Contact not found"));

        contact.setCaregiverName(request.getCaregiverName());
        contact.setCaregiverPhone(request.getCaregiverPhone());
        contact.setPriorityOrder(request.getPriorityOrder());

        contact = contactRepository.save(contact);
        return mapToResponse(contact);
    }

    public void deleteContact(Integer contactId) {
        contactRepository.deleteById(contactId);
        log.info("Emergency contact deleted: {}", contactId);
    }

    private EmergencyContactResponse mapToResponse(EmergencyContact contact) {
        return EmergencyContactResponse.builder()
                .id(contact.getId())
                .patientId(contact.getPatient().getId())
                .caregiverName(contact.getCaregiverName())
                .caregiverPhone(contact.getCaregiverPhone())
                .priorityOrder(contact.getPriorityOrder())
                .createdAt(contact.getCreatedAt())
                .build();
    }
}
