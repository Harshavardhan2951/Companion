package com.aicompanion.emergency.repository;

import com.aicompanion.emergency.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact,Integer> {
    List<EmergencyContact> findByPatientIdOrderByPriorityOrderAsc(Long patientId);
}