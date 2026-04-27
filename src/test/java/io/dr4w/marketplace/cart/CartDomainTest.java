package io.dr4w.marketplace.cart;

import io.dr4w.marketplace.cart.domain.model.Cart;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CartDomainTest {

    private Cart newCart() {
        return Cart.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    void addItemCreatesNewEntry() {
        Cart cart = newCart();
        UUID productId = UUID.randomUUID();

        cart.addItem(productId, 2, new BigDecimal("100.00"));

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.totalItems()).isEqualTo(2);
        assertThat(cart.total()).isEqualByComparingTo("200.00");
    }

    @Test
    void addSameProductIncreasesQuantity() {
        Cart cart = newCart();
        UUID productId = UUID.randomUUID();

        cart.addItem(productId, 2, new BigDecimal("50.00"));
        cart.addItem(productId, 3, new BigDecimal("50.00"));

        assertThat(cart.getItems()).hasSize(1);
        assertThat(cart.totalItems()).isEqualTo(5);
    }

    @Test
    void removeItemDeletesEntry() {
        Cart cart = newCart();
        UUID productId = UUID.randomUUID();
        cart.addItem(productId, 1, new BigDecimal("10.00"));

        cart.removeItem(productId);

        assertThat(cart.isEmpty()).isTrue();
    }

    @Test
    void clearRemovesAllItems() {
        Cart cart = newCart();
        cart.addItem(UUID.randomUUID(), 1, new BigDecimal("10.00"));
        cart.addItem(UUID.randomUUID(), 2, new BigDecimal("20.00"));

        cart.clear();

        assertThat(cart.isEmpty()).isTrue();
        assertThat(cart.total()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void updateQuantityToZeroThrows() {
        Cart cart = newCart();
        UUID productId = UUID.randomUUID();
        cart.addItem(productId, 1, new BigDecimal("10.00"));

        assertThatThrownBy(() -> cart.getItems().getFirst().updateQuantity(0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
