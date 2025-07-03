package com.drip.competition.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationId implements Serializable {
    private UUID tournamentId;
    private UUID participantId;
    private ParticipantType participantType;
}
