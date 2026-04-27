package io.dr4w.marketplace.cart.domain.port.out;

import io.dr4w.marketplace.cart.domain.model.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository {

    Cart save(Cart cart);

    Optional<Cart> findByUserId(UUID userId);

    Cart findOrCreateByUserId(UUID userId);
}
