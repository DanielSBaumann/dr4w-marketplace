package io.dr4w.marketplace.payment.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class Payment {

    private final UUID id;
    private final UUID orderId;
    private PaymentStatus status;
    private final String paymentMethod;
    private final BigDecimal amount;
    private String gatewayReference;
    private final Instant createdAt;
}
