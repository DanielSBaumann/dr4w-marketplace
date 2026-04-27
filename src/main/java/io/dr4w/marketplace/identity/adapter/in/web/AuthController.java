package io.dr4w.marketplace.identity.adapter.in.web;

import io.dr4w.marketplace.identity.adapter.in.web.dto.AuthResponse;
import io.dr4w.marketplace.identity.adapter.in.web.dto.LoginRequest;
import io.dr4w.marketplace.identity.adapter.in.web.dto.RegisterRequest;
import io.dr4w.marketplace.identity.domain.model.User;
import io.dr4w.marketplace.identity.domain.port.in.AuthenticateUserUseCase;
import io.dr4w.marketplace.identity.domain.port.in.RegisterUserUseCase;
import io.dr4w.marketplace.shared.infrastructure.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Registration and authentication")
public class AuthController {

    private final RegisterUserUseCase registerUser;
    private final AuthenticateUserUseCase authenticateUser;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        User user = registerUser.execute(new RegisterUserUseCase.Command(
                request.name(), request.email(), request.password(), request.role()
        ));
        String token = jwtService.generateToken(user.getId(), user.getEmail().value(), user.getRole().name());
        return AuthResponse.of(user, token);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate and receive JWT token")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        User user = authenticateUser.execute(new AuthenticateUserUseCase.Command(request.email(), request.password()));
        String token = jwtService.generateToken(user.getId(), user.getEmail().value(), user.getRole().name());
        return AuthResponse.of(user, token);
    }
}
