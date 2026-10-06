package com.aicompanion.emergency.repository;

import com.aicompanion.emergency.entity.EmergencyEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyEventRepository extends JpaRepository<EmergencyEvent, Long> {
    // Find all events for a specific user ID
    List<EmergencyEvent> findByUserId(Integer userId);

    // Find paginated events for a specific user ID sorted automatically or by Pageable
    Page<EmergencyEvent> findByUserId(Integer userId, Pageable pageable);

    // Find events by status (e.g., "PENDING", "RESOLVED")
    List<EmergencyEvent> findByEscalationStatus(String escalationStatus);

    // Find events by user ID and status
    List<EmergencyEvent> findByUserIdAndEscalationStatus(Integer userId, String escalationStatus);
}