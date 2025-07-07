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
@IdClass(TeamPlayerRelationId.class)
public class TeamPlayerRelation {
    @Id
    @Column(name = "team_id")
    private UUID teamId;

    @Id
    @Column(name = "user_id")
    private UUID userId;
}
