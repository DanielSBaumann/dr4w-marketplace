package io.dr4w.marketplace.identity.adapter.in.web.dto;

import io.dr4w.marketplace.identity.domain.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(min = 2, max = 200) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8, max = 100) String password,
        UserRole role
) {
    public RegisterRequest {
        if (role == null) role = UserRole.BUYER;
    }
}
