package io.dr4w.marketplace.order.adapter.out.persistence;

import io.dr4w.marketplace.order.domain.model.Order;
import io.dr4w.marketplace.order.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class OrderEntity {

    @Id
    private UUID id;

    @Column(name = "buyer_id", nullable = false)
    private UUID buyerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @Builder.Default
    private List<OrderItemEntity> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    Order toDomain() {
        return Order.builder()
                .id(id)
                .buyerId(buyerId)
                .status(status)
                .items(items.stream().map(OrderItemEntity::toDomain).toList())
                .totalAmount(totalAmount)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    static OrderEntity fromDomain(Order order) {
        List<OrderItemEntity> itemEntities = order.getItems().stream()
                .map(item -> OrderItemEntity.fromDomain(item, order.getId()))
                .toList();
        return OrderEntity.builder()
                .id(order.getId())
                .buyerId(order.getBuyerId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .items(new ArrayList<>(itemEntities))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
