package io.dr4w.marketplace.catalog.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

    public Money {
        if (amount == null) throw new IllegalArgumentException("Amount must not be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Amount must not be negative");
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    public static Money of(BigDecimal value) { return new Money(value); }
    public static Money of(double value)     { return new Money(BigDecimal.valueOf(value)); }

    public Money applyDiscount(int discountPercentage) {
        if (discountPercentage < 0 || discountPercentage > 100) throw new IllegalArgumentException("Discount must be 0-100");
        BigDecimal factor = BigDecimal.valueOf(100 - discountPercentage).divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
        return new Money(amount.multiply(factor));
    }

    @Override
    public String toString() { return amount.toPlainString(); }
}
