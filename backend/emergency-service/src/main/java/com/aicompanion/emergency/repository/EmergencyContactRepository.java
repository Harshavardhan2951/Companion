package com.aicompanion.emergency.repository;

import com.aicompanion.emergency.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Integer> {
    List<EmergencyContact> findByPatientIdOrderByPriorityOrderAsc(Integer patientId);
    List<EmergencyContact> findByPatientId(Integer patientId);
    boolean existsByPatientIdAndCaregiverPhone(Integer patientId, String caregiverPhone);
}