package com.drip.competition.service;

import com.drip.competition.dto.TeamDTO;
import com.drip.competition.entity.Team;
import com.drip.competition.entity.TeamPlayerRelation;
import com.drip.competition.repository.TeamPlayerRelationRepository;
import com.drip.competition.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamPlayerRelationRepository teamPlayerRelationRepository;

    public TeamService(TeamRepository teamRepository,
                       TeamPlayerRelationRepository teamPlayerRelationRepository) {
        this.teamRepository = teamRepository;
        this.teamPlayerRelationRepository = teamPlayerRelationRepository;
    }

    public List<TeamDTO> getAllTeams() {
        return teamRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    public TeamDTO getTeamById(UUID id) {
        Team team = teamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team not found: " + id));
        return toDTO(team);
    }

    public TeamDTO createTeam(TeamDTO dto) {
        Team team = toEntity(dto);
        return toDTO(teamRepository.save(team));
    }

    public TeamDTO updateTeam(UUID id, TeamDTO dto) {
        Team existing = teamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team not found: " + id));

        existing.setName(dto.getName());
        existing.setAvatar(dto.getAvatar());

        return toDTO(teamRepository.save(existing));
    }

    public TeamDTO partialUpdateTeam(UUID id, TeamDTO dto) {
        Team existing = teamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team not found: " + id));

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }
        if (dto.getAvatar() != null) {
            existing.setAvatar(dto.getAvatar());
        }

        return toDTO(teamRepository.save(existing));
    }

    public void deleteTeam(UUID id) {
        Team team = teamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team not found: " + id));
        teamRepository.delete(team);
    }

    public List<UUID> getAllTeamParticipants(UUID id) {
        Team team = teamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team not found: " + id));

        return teamPlayerRelationRepository.findByTeamId(id)
            .stream()
            .map(TeamPlayerRelation::getUserId)
            .collect(Collectors.toList());
    }

    public void addUserToTeam(UUID teamId, UUID userId) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new RuntimeException("Team not found: " + teamId));

        if (teamPlayerRelationRepository.findByTeamIdAndUserId(teamId, userId).isPresent()) {
            throw new RuntimeException("User is already a member of this team");
        }

        TeamPlayerRelation relation = new TeamPlayerRelation();
        relation.setTeamId(teamId);
        relation.setUserId(userId);
        teamPlayerRelationRepository.save(relation);
    }

    public void removeUserFromTeam(UUID teamId, UUID userId) {
        TeamPlayerRelation relation = teamPlayerRelationRepository.findByTeamIdAndUserId(teamId, userId)
            .orElseThrow(() -> new RuntimeException("User is not a member of this team"));
        teamPlayerRelationRepository.delete(relation);
    }

    private TeamDTO toDTO(Team entity) {
        TeamDTO dto = new TeamDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAvatar(entity.getAvatar());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

    private Team toEntity(TeamDTO dto) {
        Team entity = new Team();

        entity.setName(dto.getName());
        entity.setAvatar(dto.getAvatar());

        return entity;
    }
}
