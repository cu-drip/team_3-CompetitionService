package com.drip.competition.service;

import com.drip.competition.dto.TournamentDTO;
import com.drip.competition.entity.Tournament;
import com.drip.competition.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<TournamentDTO> getAllTournaments() {
        return tournamentRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public TournamentDTO createTournament(TournamentDTO dto) {
        Tournament tournament = toEntity(dto);
        return toDTO(tournamentRepository.save(tournament));
    }

    public TournamentDTO getTournamentById(UUID id) {
        Tournament tournament = tournamentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tournament not found: " + id));
        return toDTO(tournament);
    }

    public TournamentDTO updateTournament(UUID id, TournamentDTO dto) {
        Tournament existing = tournamentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tournament not found: " + id));

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setSport(dto.getSport());
        existing.setTypeTournament(dto.getTypeTournament());
        existing.setTypeGroup(dto.getTypeGroup());
        existing.setMatchesNumber(dto.getMatchesNumber());
        existing.setStartTime(dto.getStartTime());
        existing.setEntryCost(dto.getEntryCost());
        existing.setMaxParticipants(dto.getMaxParticipants());
        existing.setRegistrationDeadline(dto.getRegistrationDeadline());
        existing.setPlace(dto.getPlace());
        existing.setOrganizerId(dto.getOrganizedId());

        return toDTO(tournamentRepository.save(existing));
    }

    public TournamentDTO partialUpdateTournament(UUID id, TournamentDTO dto) {
        Tournament existing = tournamentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tournament not found: " + id));

        if (dto.getTitle() != null) {
            existing.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            existing.setDescription(dto.getDescription());
        }
        if (dto.getSport() != null) {
            existing.setSport(dto.getSport());
        }
        if (dto.getTypeTournament() != null) {
            existing.setTypeTournament(dto.getTypeTournament());
        }
        if (dto.getTypeGroup() != null) {
            existing.setTypeGroup(dto.getTypeGroup());
        }
        if (dto.getMatchesNumber() != null) {
            existing.setMatchesNumber(dto.getMatchesNumber());
        }
        if (dto.getStartTime() != null) {
            existing.setStartTime(dto.getStartTime());
        }
        if (dto.getEntryCost() != null) {
            existing.setEntryCost(dto.getEntryCost());
        }
        if (dto.getMaxParticipants() != null) {
            existing.setMaxParticipants(dto.getMaxParticipants());
        }
        if (dto.getRegistrationDeadline() != null) {
            existing.setRegistrationDeadline(dto.getRegistrationDeadline());
        }
        if (dto.getPlace() != null) {
            existing.setPlace(dto.getPlace());
        }
        if (dto.getOrganizedId() != null) {
            existing.setOrganizerId(dto.getOrganizedId());
        }

        return toDTO(tournamentRepository.save(existing));
    }

    public void deleteTournament(UUID id) {
        tournamentRepository.deleteById(id);
    }

    public void registerParticipant(UUID tournamentId, UUID participantId, String participantType) {
        // Логика вставки в таблицу tournament_registrations
    }

    public void unregisterParticipant(UUID tournamentId, UUID participantId, String participantType) {
        // Логика удаления из таблицы tournament_registrations
    }

    public void notifyParticipants(UUID tournamentId) {
        // Логика уведомления
    }

    private TournamentDTO toDTO(Tournament entity) {
        TournamentDTO dto = new TournamentDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setSport(entity.getSport());
        dto.setTypeTournament(entity.getTypeTournament());
        dto.setTypeGroup(entity.getTypeGroup());
        dto.setMatchesNumber(entity.getMatchesNumber());
        dto.setStartTime(entity.getStartTime());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setEntryCost(entity.getEntryCost());
        dto.setMaxParticipants(entity.getMaxParticipants());
        dto.setRegistrationDeadline(entity.getRegistrationDeadline());
        dto.setPlace(entity.getPlace());
        dto.setOrganizedId(entity.getOrganizerId());

        return dto;
    }

    private Tournament toEntity(TournamentDTO dto) {
        Tournament entity = new Tournament();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setSport(dto.getSport());
        entity.setTypeTournament(dto.getTypeTournament());
        entity.setTypeGroup(dto.getTypeGroup());
        entity.setMatchesNumber(dto.getMatchesNumber());
        entity.setStartTime(dto.getStartTime());
        entity.setEntryCost(dto.getEntryCost());
        entity.setMaxParticipants(dto.getMaxParticipants());
        entity.setRegistrationDeadline(dto.getRegistrationDeadline());
        entity.setPlace(dto.getPlace());
        entity.setOrganizerId(dto.getOrganizedId());

        return entity;
    }
}
