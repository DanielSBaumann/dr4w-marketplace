package io.dr4w.marketplace.order.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class Order {

    private final UUID id;
    private final UUID buyerId;
    private OrderStatus status;
    private final List<OrderItem> items;
    private final BigDecimal totalAmount;
    private final Instant createdAt;
    private Instant updatedAt;

    public void markPaid() {
        this.status = OrderStatus.PAID;
        this.updatedAt = Instant.now();
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = Instant.now();
    }
}
