package io.dr4w.marketplace.identity.adapter.in.web.dto;

import io.dr4w.marketplace.identity.domain.model.User;

import java.util.UUID;

public record AuthResponse(UUID userId, String name, String email, String role, String accessToken) {

    public static AuthResponse of(User user, String token) {
        return new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail().value(),
                user.getRole().name(),
                token
        );
    }
}
