package io.dr4w.marketplace.payment.application;

import io.dr4w.marketplace.shared.infrastructure.config.MarketplaceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class PlatformFeePolicy {

    private final MarketplaceProperties properties;

    public BigDecimal calculateFee(BigDecimal amount) {
        return amount
                .multiply(BigDecimal.valueOf(properties.platform().feePercentage()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal vendorShare(BigDecimal amount) {
        return amount.subtract(calculateFee(amount));
    }
}
