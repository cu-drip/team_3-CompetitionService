package com.drip.competition.dto;

import com.drip.competition.entity.Sport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDTO {
    private UUID id;
    private String title;
    private String description;
    private Sport sport;
    private Integer matchesNumber;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private Double entryCost; // BigDecimal
    private Integer maxParticipants;
    private LocalDateTime registrationDeadline;
    private String place;
    private UUID organizedId;
}
