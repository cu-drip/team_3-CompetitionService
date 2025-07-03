package com.drip.competition.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tournament_registrations")
@IdClass(RegistrationId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @Column(name = "tournament_id")
    private UUID tournamentId;

    @Id
    @Column(name = "participant_id")
    private UUID participantId;

    @Enumerated(EnumType.STRING)
    @Id
    @Column(name = "participant_type")
    private ParticipantType participantType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RegistrationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private Sport sport;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    // add any references to other entities if needed !!!

    @PrePersist
    public void prePersist() {
        this.registeredAt = LocalDateTime.now();
        this.status = RegistrationStatus.PENDING;
    }
}
