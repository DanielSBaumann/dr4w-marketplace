package io.dr4w.marketplace.payment.adapter.out.persistence;

import io.dr4w.marketplace.payment.domain.model.Payment;
import io.dr4w.marketplace.payment.domain.port.out.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        return jpaRepository.save(PaymentEntity.fromDomain(payment)).toDomain();
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return jpaRepository.findByOrderId(orderId).map(PaymentEntity::toDomain);
    }
}
