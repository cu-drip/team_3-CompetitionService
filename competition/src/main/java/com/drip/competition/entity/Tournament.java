package com.drip.competition.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private UUID id;

    @Column
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport")
    private Sport sport; // enum sports

    @Enumerated(EnumType.STRING)
    @Column(name = "type_tournament")
    private TypeTournament typeTournament; // enum solo team

    @Enumerated(EnumType.STRING)
    @Column(name = "type_group")
    private TypeGroup typeGroup; // enum olympic swiss round_robin

    @Column(name = "matches_number")
    private Integer matchesNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "entry_cost")
    private BigDecimal entryCost;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    @Column(name = "place")
    private String place;

    @Column(name = "organizer_id")
    private UUID organizerId;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
