package io.dr4w.marketplace.order.domain.port.in;

import io.dr4w.marketplace.order.domain.model.Order;

import java.util.UUID;

public interface GetOrderUseCase {

    record Query(UUID orderId, UUID userId) {}

    Order execute(Query query);
}
