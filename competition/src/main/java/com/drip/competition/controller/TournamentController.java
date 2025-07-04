package com.drip.competition.controller;

import com.drip.competition.dto.TournamentDTO;
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

    @GetMapping("/{id}")
    public TournamentDTO getTournamentById(@PathVariable UUID id) {
        return tournamentService.getTournamentById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public TournamentDTO updateTournament(@PathVariable UUID id, @RequestBody TournamentDTO dto) {
        return tournamentService.updateTournament(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public TournamentDTO partialUpdateTournament(@PathVariable UUID id, @RequestBody TournamentDTO dto) {
        return tournamentService.partialUpdateTournament(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable UUID id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<String> registerParticipant(@PathVariable UUID id,
                                                      @RequestParam UUID participantId,
                                                      @RequestParam String participantType) {
        tournamentService.registerParticipant(id, participantId, participantType);
        return ResponseEntity.ok("Registered successfully");
    }

    @PostMapping("/{id}/unregister")
    public ResponseEntity<String> unregisterParticipant(@PathVariable UUID id,
                                                        @RequestParam UUID participantId,
                                                        @RequestParam String participantType) {
        tournamentService.unregisterParticipant(id, participantId, participantType);
        return ResponseEntity.ok("Unregistered successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/notify")
    public ResponseEntity<String> notifyParticipants(@PathVariable UUID id) {
        tournamentService.notifyParticipants(id);
        return ResponseEntity.ok("Notifications sent");
    }
}
