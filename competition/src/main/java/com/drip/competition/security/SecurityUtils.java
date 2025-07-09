package com.drip.competition.security;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("securityUtils")
public class SecurityUtils {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityUtils(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Проверить, может ли текущий пользователь изменять команду
     * (админ или владелец команды)
     */
    public boolean canModifyTeam(UUID teamOwnerId) {
        return isAdmin() || jwtTokenProvider.isCurrentUserOwner(teamOwnerId);
    }

    /**
     * Проверить, может ли текущий пользователь регистрироваться на турнир
     */
    public boolean canRegisterForTournament(UUID participantId) {
        return isAdmin() || jwtTokenProvider.isCurrentUserOwner(participantId);
    }

    /**
     * Проверить, является ли пользователь админом
     */
    public boolean isAdmin() {
        return jwtTokenProvider.isCurrentUserAdmin();
    }

    /**
     * Получить ID текущего пользователя
     */
    public UUID getCurrentUserId() {
        return jwtTokenProvider.getCurrentUserId();
    }
}
