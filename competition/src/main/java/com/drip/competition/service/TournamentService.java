package com.drip.competition.service;

import com.drip.competition.dto.NotificationDTO;
import com.drip.competition.dto.TournamentDTO;
import com.drip.competition.entity.*;
import com.drip.competition.repository.TeamRepository;
import com.drip.competition.repository.TournamentRegistrationRepository;
import com.drip.competition.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TournamentRegistrationRepository registrationRepository;
    private final TeamRepository teamRepository;

    public TournamentService(TournamentRepository tournamentRepository,
                             TournamentRegistrationRepository registrationRepository,
                             TeamRepository teamRepository) {
        this.tournamentRepository = tournamentRepository;
        this.registrationRepository = registrationRepository;
        this.teamRepository = teamRepository;
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
        existing.setTournirInstantState(dto.getTournirInstantState());
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
        if (dto.getTournirInstantState() != null) {
            existing.setTournirInstantState(dto.getTournirInstantState());
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

    @Autowired // плохо
    private TeamService teamService;

    public List<UUID> getAllTournamentParticipants(UUID tournamentId) {
        List<Registration> registrations = registrationRepository.findByTournamentIdAndStatus(
            tournamentId, RegistrationStatus.ACCEPTED);

        List<UUID> participants = new ArrayList<>();

        for (Registration registration : registrations) {
            if (registration.getParticipantType() == ParticipantType.solo) {
                // Получаем сольного участника
                participants.add(registration.getParticipantId());
            } else if (registration.getParticipantType() == ParticipantType.team) {
                // Получаем команду и её участников
                Team team = teamRepository.findById(registration.getParticipantId())
                    .orElse(null);
                if (team != null) {
                    participants = teamService.getAllTeamParticipants(team.getId());
                }
            }
        }
        return participants;
    }

    @Transactional
    public void registerParticipant(UUID tournamentId, UUID participantId, String participantType) {
        // Проверяем, что турнир существует
        Tournament tournament = tournamentRepository.findById(tournamentId)
            .orElseThrow(() -> new RuntimeException("Tournament not found"));

        // Проверяем дедлайн регистрации
        if (tournament.getRegistrationDeadline() != null &&
            LocalDateTime.now().isAfter(tournament.getRegistrationDeadline())) {
            throw new RuntimeException("Registration deadline has passed or isn't stated");
        }

        ParticipantType type = ParticipantType.valueOf(participantType.toLowerCase());

        // Проверяем, что участник еще не зарегистрирован
        if (registrationRepository.existsByTournamentIdAndParticipantIdAndParticipantType(
            tournamentId, participantId, type)) {
            throw new RuntimeException("Participant is already registered");
        }

        // Проверяем лимит участников
        if (tournament.getMaxParticipants() != null) {
            long currentParticipants = registrationRepository.countByTournamentIdAndStatus(
                tournamentId, RegistrationStatus.ACCEPTED);
            if (currentParticipants >= tournament.getMaxParticipants()) {
                throw new RuntimeException("Tournament is full");
            }
        }

        // Создаем регистрацию
        Registration registration = new Registration();
        registration.setTournamentId(tournamentId);
        registration.setParticipantId(participantId);
        registration.setParticipantType(type);
        registration.setSport(tournament.getSport());
        registration.setStatus(RegistrationStatus.ACCEPTED);

        registrationRepository.save(registration);
    }

    @Transactional
    public void unregisterParticipant(UUID tournamentId, UUID participantId, String participantType) {
        ParticipantType type = ParticipantType.valueOf(participantType.toLowerCase());

        // Проверяем, что регистрация существует
        if (!registrationRepository.existsByTournamentIdAndParticipantIdAndParticipantType(
            tournamentId, participantId, type)) {
            throw new RuntimeException("Registration not found");
        }

        // Удаляем регистрацию
        registrationRepository.deleteByTournamentIdAndParticipantIdAndParticipantType(
            tournamentId, participantId, type);
    }

    public List<UUID> getUserTournaments(UUID userId) {
        return registrationRepository.findTournamentIdsByParticipantId(userId);
    }

    // Метод для получения турниров по состоянию
    public List<UUID> getUserTournamentsByState(UUID userId, TournamentInstantState state) {
        return registrationRepository.findTournamentIdsByParticipantIdAndTournamentState(userId, state);
    }

    // Метод для получения турниров по типу участника
    public List<UUID> getUserTournamentsByType(UUID userId, ParticipantType participantType) {
        return registrationRepository.findTournamentIdsByParticipantIdAndType(userId, participantType);
    }

    // Метод для получения турниров по типу участника и состоянию
    public List<UUID> getUserTournamentsByTypeAndState(UUID userId, ParticipantType participantType, TournamentInstantState state) {
        return registrationRepository.findTournamentIdsByParticipantIdAndTypeAndTournamentState(userId, participantType, state);
    }

    // Альтернативный метод для получения полной информации о турнирах
    public List<TournamentDTO> getUserTournamentDetails(UUID userId) {
        List<UUID> tournamentIds = registrationRepository.findTournamentIdsByParticipantId(userId);

        return tournamentIds.stream()
            .map(this::getTournamentById)
            .collect(Collectors.toList());
    }

    // Метод для получения полной информации о турнирах по состоянию
    public List<TournamentDTO> getUserTournamentDetailsByState(UUID userId, TournamentInstantState state) {
        List<UUID> tournamentIds = registrationRepository.findTournamentIdsByParticipantIdAndTournamentState(userId, state);

        return tournamentIds.stream()
            .map(this::getTournamentById)
            .collect(Collectors.toList());
    }

    // Удобные методы для популярных состояний
    public List<UUID> getUserOngoingTournaments(UUID userId) {
        return getUserTournamentsByState(userId, TournamentInstantState.ongoingTournaments);
    }

    public List<UUID> getUserOpenRegistrationTournaments(UUID userId) {
        return getUserTournamentsByState(userId, TournamentInstantState.openedRegistrationTournaments);
    }

    public List<UUID> getUserEndedTournaments(UUID userId) {
        return getUserTournamentsByState(userId, TournamentInstantState.endedTournaments);
    }

    public void notifyParticipants(UUID tournamentId) {
        // Получаем всех зарегистрированных участников
        List<UUID> participantIds = registrationRepository.findParticipantIdsByTournamentIdAndStatus(
            tournamentId, RegistrationStatus.ACCEPTED);

        // Здесь должна быть логика уведомления:
        // 1. Можно использовать message queue (RabbitMQ, Kafka)
        // 2. Можно отправить HTTP запрос в notification-service
        // 3. Можно использовать WebSocket для real-time уведомлений
        // 4. Можно отправить email через email-service

        // Пример с использованием notification service:
        Tournament tournament = tournamentRepository.findById(tournamentId)
            .orElseThrow(() -> new RuntimeException("Tournament not found"));

        NotificationDTO notification = new NotificationDTO();
//        notification.setTitle("Tournament Update");
//        notification.setMessage("Tournament '" + tournament.getTitle() + "' has been updated");
//        notification.setRecipientIds(participantIds);
//        notification.setType("tournament_update");

        // notificationService.sendNotification(notification);

        // Или можно использовать ApplicationEvent для асинхронной обработки:
        // applicationEventPublisher.publishEvent(new TournamentNotificationEvent(tournamentId, participantIds));
    }

    private TournamentDTO toDTO(Tournament entity) {
        TournamentDTO dto = new TournamentDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setSport(entity.getSport());
        dto.setTypeTournament(entity.getTypeTournament());
        dto.setTypeGroup(entity.getTypeGroup());
        dto.setTournirInstantState(entity.getTournirInstantState());
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
        entity.setTournirInstantState(dto.getTournirInstantState());
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
