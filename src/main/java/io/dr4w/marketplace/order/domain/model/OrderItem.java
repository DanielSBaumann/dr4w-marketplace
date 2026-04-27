package io.dr4w.marketplace.order.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class OrderItem {

    private final UUID id;
    private final UUID orderId;
    private final UUID productId;
    private final UUID vendorId;
    private final int quantity;
    private final BigDecimal unitPrice;
    private final BigDecimal totalPrice;
}
