package io.dr4w.marketplace.cart.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Builder
public class Cart {

    private final UUID id;
    private final UUID userId;
    @Builder.Default
    private final List<CartItem> items = new ArrayList<>();
    private final Instant createdAt;
    private Instant updatedAt;

    public void addItem(UUID productId, int quantity, BigDecimal priceSnapshot) {
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();
        if (existing.isPresent()) {
            existing.get().updateQuantity(existing.get().getQuantity() + quantity);
        } else {
            items.add(CartItem.builder()
                    .id(UUID.randomUUID()).cartId(id).productId(productId)
                    .quantity(quantity).priceSnapshot(priceSnapshot).addedAt(Instant.now())
                    .build());
        }
        this.updatedAt = Instant.now();
    }

    public void removeItem(UUID productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
        this.updatedAt = Instant.now();
    }

    public void clear() {
        items.clear();
        this.updatedAt = Instant.now();
    }

    public BigDecimal total() {
        return items.stream()
                .map(CartItem::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int totalItems() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
