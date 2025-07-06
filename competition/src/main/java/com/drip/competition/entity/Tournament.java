package com.drip.competition.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tournaments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private Sport sport;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_tournament")
    private TypeTournament typeTournament;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_group")
    private TypeGroup typeGroup;

    @Column(name = "matches_number")
    private Integer matchesNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "entry_cost")
    private Double entryCost;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    @Column(name = "place")
    private String place;

    @Column(name = "organizer_id") // Исправлено название поля
    private UUID organizerId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
