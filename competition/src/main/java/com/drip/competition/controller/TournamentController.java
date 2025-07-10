package com.drip.competition.controller;

import com.drip.competition.dto.TournamentDTO;
import com.drip.competition.dto.UserDTO;
import com.drip.competition.service.TournamentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
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
