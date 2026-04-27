package io.dr4w.marketplace.cart.adapter.in.web;

import io.dr4w.marketplace.cart.adapter.in.web.dto.AddItemRequest;
import io.dr4w.marketplace.cart.adapter.in.web.dto.CartResponse;
import io.dr4w.marketplace.cart.domain.port.in.AddItemToCartUseCase;
import io.dr4w.marketplace.cart.domain.port.in.GetCartUseCase;
import io.dr4w.marketplace.cart.domain.port.in.RemoveItemFromCartUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final GetCartUseCase getCartUseCase;
    private final AddItemToCartUseCase addItemToCartUseCase;
    private final RemoveItemFromCartUseCase removeItemFromCartUseCase;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal UserDetails user) {
        UUID userId = UUID.fromString(user.getUsername());
        var cart = getCartUseCase.execute(new GetCartUseCase.Query(userId));
        return ResponseEntity.ok(CartResponse.from(cart));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody AddItemRequest request
    ) {
        UUID userId = UUID.fromString(user.getUsername());
        var cart = addItemToCartUseCase.execute(
                new AddItemToCartUseCase.Command(userId, request.productId(), request.quantity()));
        return ResponseEntity.ok(CartResponse.from(cart));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponse> removeItem(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable UUID productId
    ) {
        UUID userId = UUID.fromString(user.getUsername());
        var cart = removeItemFromCartUseCase.execute(
                new RemoveItemFromCartUseCase.Command(userId, productId));
        return ResponseEntity.ok(CartResponse.from(cart));
    }
}
