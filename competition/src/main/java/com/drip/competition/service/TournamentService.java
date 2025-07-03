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
        tournament.setId(UUID.randomUUID());
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
        existing.setMatchesNumber(dto.getMatchesNumber());
        // Обновляйте остальные поля по необходимости
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
        // и так далее для нужных полей
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
        dto.setMatchesNumber(entity.getMatchesNumber());
        return dto;
    }

    private Tournament toEntity(TournamentDTO dto) {
        Tournament entity = new Tournament();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setSport(dto.getSport());
        entity.setMatchesNumber(dto.getMatchesNumber());
        return entity;
    }
}
