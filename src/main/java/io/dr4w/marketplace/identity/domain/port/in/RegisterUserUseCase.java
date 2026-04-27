package io.dr4w.marketplace.identity.domain.port.in;

import io.dr4w.marketplace.identity.domain.model.User;
import io.dr4w.marketplace.identity.domain.model.UserRole;

public interface RegisterUserUseCase {

    record Command(String name, String email, String password, UserRole role) {}

    User execute(Command command);
}
