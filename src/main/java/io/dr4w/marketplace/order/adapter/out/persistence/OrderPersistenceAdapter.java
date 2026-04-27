package io.dr4w.marketplace.order.adapter.out.persistence;

import io.dr4w.marketplace.order.domain.model.Order;
import io.dr4w.marketplace.order.domain.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderRepository {

    private final OrderJpaRepository jpaRepository;

    @Override
    public Order save(Order order) {
        return jpaRepository.save(OrderEntity.fromDomain(order)).toDomain();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id).map(OrderEntity::toDomain);
    }

    @Override
    public Page<Order> findByBuyerId(UUID buyerId, Pageable pageable) {
        return jpaRepository.findByBuyerId(buyerId, pageable).map(OrderEntity::toDomain);
    }
}
