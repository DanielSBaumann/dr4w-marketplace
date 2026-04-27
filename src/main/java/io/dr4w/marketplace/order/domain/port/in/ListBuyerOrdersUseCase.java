package io.dr4w.marketplace.order.domain.port.in;

import io.dr4w.marketplace.order.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ListBuyerOrdersUseCase {

    record Query(UUID userId, Pageable pageable) {}

    Page<Order> execute(Query query);
}
