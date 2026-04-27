package io.dr4w.marketplace.order.domain.port.out;

import io.dr4w.marketplace.order.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(UUID id);

    Page<Order> findByBuyerId(UUID buyerId, Pageable pageable);
}
