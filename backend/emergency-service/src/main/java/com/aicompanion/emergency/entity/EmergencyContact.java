package com.aicompanion.emergency.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "emergency_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @Column(name = "caregiver_name", nullable = false, length = 100)
    private String caregiverName;

    @Column(name = "caregiver_phone", nullable = false, length = 20)
    private String caregiverPhone;

    @Column(name = "priority_order", columnDefinition = "INTEGER DEFAULT 1")
    private Integer priorityOrder = 1;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}