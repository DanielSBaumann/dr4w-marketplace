package io.dr4w.marketplace.identity.application;

import io.dr4w.marketplace.identity.domain.model.User;
import io.dr4w.marketplace.identity.domain.port.in.AuthenticateUserUseCase;
import io.dr4w.marketplace.identity.domain.port.out.UserRepository;
import io.dr4w.marketplace.shared.infrastructure.web.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserService implements AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User execute(Command command) {
        User user = userRepository.findByEmail(command.email().toLowerCase())
                .orElseThrow(() -> DomainException.badRequest("Invalid credentials"));

        if (!passwordEncoder.matches(command.password(), user.getPasswordHash())) {
            throw DomainException.badRequest("Invalid credentials");
        }

        return user;
    }
}
