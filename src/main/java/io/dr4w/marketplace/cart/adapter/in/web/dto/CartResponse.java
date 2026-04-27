package io.dr4w.marketplace.cart.adapter.in.web.dto;

import io.dr4w.marketplace.cart.domain.model.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CartResponse(
        UUID id,
        UUID userId,
        List<CartItemResponse> items,
        BigDecimal total,
        int totalItems
) {
    public static CartResponse from(Cart cart) {
        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                cart.getItems().stream().map(CartItemResponse::from).toList(),
                cart.total(),
                cart.totalItems()
        );
    }
}
