package com.aicompanion.emergency.controller;

import com.aicompanion.emergency.dto.EmergencyContactRequest;
import com.aicompanion.emergency.dto.EmergencyContactResponse;
import com.aicompanion.emergency.service.EmergencyContactService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency/contact")
@Slf4j
@AllArgsConstructor
public class EmergencyContactController {

    private EmergencyContactService contactService;

    @PostMapping
    public ResponseEntity<EmergencyContactResponse> createContact(
            @Valid @RequestBody EmergencyContactRequest contactRequest
    ){
        EmergencyContactResponse contactResponse = contactService.createContact(contactRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactResponse);
    }

    @GetMapping("patientId/{patientId}")
    public ResponseEntity<List<EmergencyContactResponse>> getContactsByPatient(
        @PathVariable Integer patientId
    ){
        List<EmergencyContactResponse> response = contactService.getContactsByPatient(patientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("contactId/{contactId}")
    public ResponseEntity<EmergencyContactResponse> updateContact(
            @PathVariable Integer contactId,
            @Valid @RequestBody EmergencyContactRequest request
    ){
        EmergencyContactResponse contact = contactService.updateContact(contactId, request);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("delete/{contactId}")
    public ResponseEntity<Void> deleteContact(
            @PathVariable Integer contactId
    ){
        contactService.deleteContact(contactId);
        return ResponseEntity.noContent().build();
    }
}
