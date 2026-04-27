package io.dr4w.marketplace.order.domain.port.in;

import io.dr4w.marketplace.order.domain.model.Order;

import java.util.UUID;

public interface PlaceOrderUseCase {

    record Command(UUID userId, String paymentMethod) {}

    Order execute(Command command);
}
