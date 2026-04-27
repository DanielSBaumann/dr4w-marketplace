package io.dr4w.marketplace.payment.adapter.out.persistence;

import io.dr4w.marketplace.payment.domain.model.Payment;
import io.dr4w.marketplace.payment.domain.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentEntity {

    @Id
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "gateway_reference", length = 200)
    private String gatewayReference;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    Payment toDomain() {
        return Payment.builder()
                .id(id)
                .orderId(orderId)
                .status(status)
                .paymentMethod(paymentMethod)
                .amount(amount)
                .gatewayReference(gatewayReference)
                .createdAt(createdAt)
                .build();
    }

    static PaymentEntity fromDomain(Payment payment) {
        return PaymentEntity.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .status(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .amount(payment.getAmount())
                .gatewayReference(payment.getGatewayReference())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
