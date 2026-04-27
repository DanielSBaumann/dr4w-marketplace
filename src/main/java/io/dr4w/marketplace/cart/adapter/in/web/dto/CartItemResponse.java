package io.dr4w.marketplace.cart.adapter.in.web.dto;

import io.dr4w.marketplace.cart.domain.model.CartItem;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponse(
        UUID productId,
        int quantity,
        BigDecimal priceSnapshot,
        BigDecimal subtotal
) {
    public static CartItemResponse from(CartItem item) {
        return new CartItemResponse(
                item.getProductId(),
                item.getQuantity(),
                item.getPriceSnapshot(),
                item.subtotal()
        );
    }
}
