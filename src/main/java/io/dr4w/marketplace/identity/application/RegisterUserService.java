package io.dr4w.marketplace.identity.application;

import io.dr4w.marketplace.identity.domain.model.Email;
import io.dr4w.marketplace.identity.domain.model.User;
import io.dr4w.marketplace.identity.domain.port.in.RegisterUserUseCase;
import io.dr4w.marketplace.identity.domain.port.out.UserRepository;
import io.dr4w.marketplace.shared.infrastructure.web.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User execute(Command command) {
        Email email = new Email(command.email());

        if (userRepository.existsByEmail(email.value())) {
            throw DomainException.conflict("Email already registered");
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .name(command.name())
                .email(email)
                .passwordHash(passwordEncoder.encode(command.password()))
                .role(command.role())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return userRepository.save(user);
    }
}
