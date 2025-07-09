package com.drip.competition.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    public final JwtDecoder jwtDecoder;

    public JwtTokenProvider(@Value("${jwt.secret-file}") String secretFilePath) {
        try {
            byte[] secretBytes = Files.readAllBytes(Path.of(secretFilePath));
            jwtDecoder = NimbusJwtDecoder.withSecretKey(
                new SecretKeySpec(secretBytes, "HmacSHA256")
            ).build();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать секретный ключ для JWT", e);
        }
    }

    /**
     * Получить текущий JWT токен из SecurityContext
     */
    public Jwt getCurrentJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt;
        }
        return null;
    }

    /**
     * Получить ID текущего пользователя из JWT (из поля 'sub')
     */
    public UUID getCurrentUserId() {
        Jwt jwt = getCurrentJwt();
        if (jwt != null) {
            String userIdStr = jwt.getSubject(); // Используем 'sub' вместо 'userId'
            return userIdStr != null ? UUID.fromString(userIdStr) : null;
        }
        return null;
    }

    /**
     * Получить роли текущего пользователя из JWT
     */
    public List<String> getCurrentUserRoles() {
        Jwt jwt = getCurrentJwt();
        if (jwt != null) {
            return jwt.getClaimAsStringList("roles");
        }
        return List.of();
    }

    /**
     * Проверить, является ли текущий пользователь админом
     */
    public boolean isCurrentUserAdmin() {
        return getCurrentUserRoles().contains("ROLE_ADMIN");
    }

    /**
     * Проверить, является ли текущий пользователь владельцем ресурса
     */
    public boolean isCurrentUserOwner(UUID resourceOwnerId) {
        UUID currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(resourceOwnerId);
    }
}
