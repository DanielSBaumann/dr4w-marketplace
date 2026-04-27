package io.dr4w.marketplace.cart.adapter.out.persistence;

import io.dr4w.marketplace.cart.domain.model.CartItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CartItemEntity {

    @Id
    private UUID id;

    @Column(name = "cart_id", nullable = false)
    private UUID cartId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "price_snapshot", nullable = false, precision = 10, scale = 2)
    private BigDecimal priceSnapshot;

    @Column(name = "added_at", nullable = false)
    private Instant addedAt;

    CartItem toDomain() {
        return CartItem.builder()
                .id(id)
                .cartId(cartId)
                .productId(productId)
                .quantity(quantity)
                .priceSnapshot(priceSnapshot)
                .addedAt(addedAt)
                .build();
    }

    static CartItemEntity fromDomain(CartItem item) {
        return CartItemEntity.builder()
                .id(item.getId())
                .cartId(item.getCartId())
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .priceSnapshot(item.getPriceSnapshot())
                .addedAt(item.getAddedAt())
                .build();
    }
}
