package io.dr4w.marketplace.cart.domain.port.in;

import io.dr4w.marketplace.cart.domain.model.Cart;

import java.util.UUID;

public interface RemoveItemFromCartUseCase {

    record Command(UUID userId, UUID productId) {}

    Cart execute(Command command);
}
