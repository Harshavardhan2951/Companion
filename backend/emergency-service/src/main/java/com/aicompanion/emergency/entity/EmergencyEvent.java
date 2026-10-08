package com.aicompanion.emergency.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "emergency_events",
        indexes = {
                @Index(name = "idx_emergency_user_id", columnList = "user_id"),
                @Index(name = "idx_emergency_created_at", columnList = "created_at DESC")
        }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmergencyEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "event_type", nullable = false, length = 50)
    private String eventType;

    @Builder.Default
    @Column(name = "escalation_status", nullable = false, length = 50)
    private String escalationStatus = "PENDING";

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "additional_info", columnDefinition = "TEXT")
    private String additionalInfo;
}