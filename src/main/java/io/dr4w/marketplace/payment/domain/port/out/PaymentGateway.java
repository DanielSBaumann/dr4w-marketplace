package io.dr4w.marketplace.payment.domain.port.out;

import java.math.BigDecimal;

public interface PaymentGateway {

    record ChargeResult(boolean approved, String gatewayReference) {}

    ChargeResult charge(BigDecimal amount, String paymentMethod);
}
