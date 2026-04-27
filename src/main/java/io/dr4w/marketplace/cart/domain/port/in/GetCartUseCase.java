package io.dr4w.marketplace.cart.domain.port.in;

import io.dr4w.marketplace.cart.domain.model.Cart;

import java.util.UUID;

public interface GetCartUseCase {

    record Query(UUID userId) {}

    Cart execute(Query query);
}
