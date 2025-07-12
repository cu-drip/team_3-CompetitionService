package com.drip.competition.controller;

import com.drip.competition.dto.TournamentDTO;
import com.drip.competition.entity.ParticipantType;
import com.drip.competition.entity.TournamentInstantState;
import com.drip.competition.service.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public List<TournamentDTO> getAllTournaments() {
        return tournamentService.getAllTournaments();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public TournamentDTO createTournament(@RequestBody TournamentDTO dto) {
        return tournamentService.createTournament(dto);
    }

    @GetMapping("/{tournamentId}")
    public TournamentDTO getTournamentById(@PathVariable UUID tournamentId) {
        return tournamentService.getTournamentById(tournamentId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{tournamentId}")
    public TournamentDTO updateTournament(@PathVariable UUID tournamentId, @RequestBody TournamentDTO dto) {
        return tournamentService.updateTournament(tournamentId, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{tournamentId}")
    public TournamentDTO partialUpdateTournament(@PathVariable UUID tournamentId, @RequestBody TournamentDTO dto) {
        return tournamentService.partialUpdateTournament(tournamentId, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{tournamentId}")
    public ResponseEntity<Void> deleteTournament(@PathVariable UUID tournamentId) {
        tournamentService.deleteTournament(tournamentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tournamentId}/participants/{participantId}")
    public ResponseEntity<String> registerParticipant(@PathVariable UUID tournamentId,
                                                      @PathVariable UUID participantId,
                                                      @RequestParam String participantType) {
        try {
            tournamentService.registerParticipant(tournamentId, participantId, participantType);
            return ResponseEntity.ok("Registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{tournamentId}/participants/{participantId}")
    public ResponseEntity<String> unregisterParticipant(@PathVariable UUID tournamentId,
                                                        @PathVariable UUID participantId,
                                                        @RequestParam String participantType) {
        try {
            tournamentService.unregisterParticipant(tournamentId, participantId, participantType);
            return ResponseEntity.ok("Unregistered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{tournamentId}/participants")
    public ResponseEntity<List<UUID>> getTournamentParticipants(@PathVariable UUID tournamentId) {
        try {
            List<UUID> participants = tournamentService.getAllTournamentParticipants(tournamentId);
            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Новый эндпоинт для получения турниров пользователя
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<UUID>> getUserTournaments(@PathVariable UUID userId,
                                                         @RequestParam(required = false) String state,
                                                         @RequestParam(required = false) String participantType) {
        try {
            List<UUID> tournamentIds;

            if (state != null && participantType != null) {
                TournamentInstantState tournamentState = TournamentInstantState.valueOf(state);
                ParticipantType pType = ParticipantType.valueOf(participantType.toUpperCase());
                tournamentIds = tournamentService.getUserTournamentsByTypeAndState(userId, pType, tournamentState);
            } else if (state != null) {
                TournamentInstantState tournamentState = TournamentInstantState.valueOf(state);
                tournamentIds = tournamentService.getUserTournamentsByState(userId, tournamentState);
            } else if (participantType != null) {
                ParticipantType pType = ParticipantType.valueOf(participantType.toUpperCase());
                tournamentIds = tournamentService.getUserTournamentsByType(userId, pType);
            } else {
                tournamentIds = tournamentService.getUserTournaments(userId);
            }

            return ResponseEntity.ok(tournamentIds);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Альтернативный вариант, если хотите получать полную информацию о турнирах
    @GetMapping("/users/{userId}/details")
    public ResponseEntity<List<TournamentDTO>> getUserTournamentDetails(@PathVariable UUID userId,
                                                                        @RequestParam(required = false) String state) {
        try {
            List<TournamentDTO> tournaments;

            if (state != null) {
                TournamentInstantState tournamentState = TournamentInstantState.valueOf(state);
                tournaments = tournamentService.getUserTournamentDetailsByState(userId, tournamentState);
            } else {
                tournaments = tournamentService.getUserTournamentDetails(userId);
            }

            return ResponseEntity.ok(tournaments);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Удобные эндпоинты для популярных состояний
    @GetMapping("/users/{userId}/ongoing")
    public ResponseEntity<List<UUID>> getUserOngoingTournaments(@PathVariable UUID userId) {
        try {
            List<UUID> tournamentIds = tournamentService.getUserOngoingTournaments(userId);
            return ResponseEntity.ok(tournamentIds);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/users/{userId}/open-registration")
    public ResponseEntity<List<UUID>> getUserOpenRegistrationTournaments(@PathVariable UUID userId) {
        try {
            List<UUID> tournamentIds = tournamentService.getUserOpenRegistrationTournaments(userId);
            return ResponseEntity.ok(tournamentIds);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/users/{userId}/ended")
    public ResponseEntity<List<UUID>> getUserEndedTournaments(@PathVariable UUID userId) {
        try {
            List<UUID> tournamentIds = tournamentService.getUserEndedTournaments(userId);
            return ResponseEntity.ok(tournamentIds);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{tournamentId}/notify")
    public ResponseEntity<String> notifyParticipants(@PathVariable UUID tournamentId) {
        try {
            tournamentService.notifyParticipants(tournamentId);
            return ResponseEntity.ok("Notifications sent");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
