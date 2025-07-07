package com.drip.competition.repository;

import com.drip.competition.entity.ParticipantType;
import com.drip.competition.entity.Registration;
import com.drip.competition.entity.RegistrationId;
import com.drip.competition.entity.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TournamentRegistrationRepository extends JpaRepository<Registration, RegistrationId> {

    List<Registration> findByTournamentIdAndStatus(UUID tournamentId, RegistrationStatus status);

    boolean existsByTournamentIdAndParticipantIdAndParticipantType(
        UUID tournamentId, UUID participantId, ParticipantType participantType);

    void deleteByTournamentIdAndParticipantIdAndParticipantType(
        UUID tournamentId, UUID participantId, ParticipantType participantType);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.tournamentId = :tournamentId AND r.status = :status")
    long countByTournamentIdAndStatus(@Param("tournamentId") UUID tournamentId, @Param("status") RegistrationStatus status);

    @Query("SELECT r.participantId FROM Registration r WHERE r.tournamentId = :tournamentId AND r.status = :status")
    List<UUID> findParticipantIdsByTournamentIdAndStatus(@Param("tournamentId") UUID tournamentId, @Param("status") RegistrationStatus status);
}
