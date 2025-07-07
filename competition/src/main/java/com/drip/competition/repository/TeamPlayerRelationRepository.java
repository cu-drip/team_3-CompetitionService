package com.drip.competition.repository;

import com.drip.competition.entity.TeamPlayerRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamPlayerRelationRepository extends JpaRepository<TeamPlayerRelation, UUID> {
    List<TeamPlayerRelation> findByTeamId(UUID teamId);
    Optional<TeamPlayerRelation> findByTeamIdAndUserId(UUID teamId, UUID userId);
}
