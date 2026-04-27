package io.dr4w.marketplace.cart.adapter.out.persistence;

import io.dr4w.marketplace.cart.domain.model.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CartEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    @Builder.Default
    private List<CartItemEntity> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    Cart toDomain() {
        Cart cart = Cart.builder()
                .id(id)
                .userId(userId)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        items.stream().map(CartItemEntity::toDomain).forEach(cart.getItems()::add);
        return cart;
    }

    static CartEntity fromDomain(Cart cart) {
        List<CartItemEntity> itemEntities = cart.getItems().stream()
                .map(CartItemEntity::fromDomain)
                .toList();
        return CartEntity.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(new ArrayList<>(itemEntities))
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt())
                .build();
    }
}
