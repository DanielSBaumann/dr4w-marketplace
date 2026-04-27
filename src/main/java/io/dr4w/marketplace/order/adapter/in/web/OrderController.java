package io.dr4w.marketplace.order.adapter.in.web;

import io.dr4w.marketplace.order.adapter.in.web.dto.OrderResponse;
import io.dr4w.marketplace.order.adapter.in.web.dto.PlaceOrderRequest;
import io.dr4w.marketplace.order.domain.port.in.GetOrderUseCase;
import io.dr4w.marketplace.order.domain.port.in.ListBuyerOrdersUseCase;
import io.dr4w.marketplace.order.domain.port.in.PlaceOrderUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final ListBuyerOrdersUseCase listBuyerOrdersUseCase;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody PlaceOrderRequest request
    ) {
        UUID userId = UUID.fromString(user.getUsername());
        var order = placeOrderUseCase.execute(new PlaceOrderUseCase.Command(userId, request.paymentMethod()));
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable UUID orderId
    ) {
        UUID userId = UUID.fromString(user.getUsername());
        var order = getOrderUseCase.execute(new GetOrderUseCase.Query(orderId, userId));
        return ResponseEntity.ok(OrderResponse.from(order));
    }

    @GetMapping
    public ResponseEntity<Page<OrderResponse>> listOrders(
            @AuthenticationPrincipal UserDetails user,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        UUID userId = UUID.fromString(user.getUsername());
        Page<OrderResponse> page = listBuyerOrdersUseCase
                .execute(new ListBuyerOrdersUseCase.Query(userId, pageable))
                .map(OrderResponse::from);
        return ResponseEntity.ok(page);
    }
}
