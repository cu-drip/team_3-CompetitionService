package com.drip.competition.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "team_player_relation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamPlayerRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID teamId;
    private UUID userId;
}
