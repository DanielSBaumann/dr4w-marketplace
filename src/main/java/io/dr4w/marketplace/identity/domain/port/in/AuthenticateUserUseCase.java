package io.dr4w.marketplace.identity.domain.port.in;

import io.dr4w.marketplace.identity.domain.model.User;

public interface AuthenticateUserUseCase {

    record Command(String email, String password) {}

    User execute(Command command);
}
