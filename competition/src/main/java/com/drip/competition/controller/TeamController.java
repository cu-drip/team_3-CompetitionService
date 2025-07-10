package com.drip.competition.controller;

import com.drip.competition.dto.TeamDTO;
import com.drip.competition.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<TeamDTO> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/{id}")
    public TeamDTO getTeamById(@PathVariable UUID id) {
        return teamService.getTeamById(id);
    }

    @GetMapping("/{id}/participants")
    public List<UUID> getAllTeamParticipants(@PathVariable UUID id) {
        return teamService.getAllTeamParticipants(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public TeamDTO createTeam(@RequestBody TeamDTO dto) {
        return teamService.createTeam(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public TeamDTO updateTeam(@PathVariable UUID id, @RequestBody TeamDTO dto) {
        return teamService.updateTeam(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public TeamDTO partialUpdateTeam(@PathVariable UUID id, @RequestBody TeamDTO dto) {
        return teamService.partialUpdateTeam(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable UUID id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{teamId}/participants/{userId}")
    public ResponseEntity<String> addUserToTeam(@PathVariable UUID teamId,
                                                @PathVariable UUID userId) {
        teamService.addUserToTeam(teamId, userId);
        return ResponseEntity.ok("Successfully added a new team member!");
    }

    @DeleteMapping("/{teamId}/participants/{userId}")
    public ResponseEntity<String> removeUserFromTeam(@PathVariable UUID teamId,
                                                     @PathVariable UUID userId) {
        teamService.removeUserFromTeam(teamId, userId);
        return ResponseEntity.ok("Successfully removed member from the team.");
    }
}
