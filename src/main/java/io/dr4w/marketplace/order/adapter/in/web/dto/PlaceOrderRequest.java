package io.dr4w.marketplace.order.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaceOrderRequest(
        @NotBlank String paymentMethod
) {}
