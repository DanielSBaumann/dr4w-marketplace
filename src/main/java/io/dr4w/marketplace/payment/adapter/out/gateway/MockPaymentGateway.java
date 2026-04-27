package io.dr4w.marketplace.payment.adapter.out.gateway;

import io.dr4w.marketplace.payment.domain.port.out.PaymentGateway;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class MockPaymentGateway implements PaymentGateway {

    @Override
    public ChargeResult charge(BigDecimal amount, String paymentMethod) {
        return new ChargeResult(true, "MOCK-" + UUID.randomUUID());
    }
}
