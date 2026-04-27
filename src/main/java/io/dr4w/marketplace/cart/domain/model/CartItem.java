package io.dr4w.marketplace.cart.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class CartItem {
    private final UUID id;
    private final UUID cartId;
    private final UUID productId;
    private int quantity;
    private final BigDecimal priceSnapshot;
    private final Instant addedAt;

    public BigDecimal subtotal() {
        return priceSnapshot.multiply(BigDecimal.valueOf(quantity));
    }

    public void updateQuantity(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.quantity = quantity;
    }
}
