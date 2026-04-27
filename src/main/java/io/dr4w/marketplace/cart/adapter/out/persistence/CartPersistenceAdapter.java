package io.dr4w.marketplace.cart.adapter.out.persistence;

import io.dr4w.marketplace.cart.domain.model.Cart;
import io.dr4w.marketplace.cart.domain.port.out.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartPersistenceAdapter implements CartRepository {

    private final CartJpaRepository jpaRepository;

    @Override
    public Cart save(Cart cart) {
        CartEntity entity = CartEntity.fromDomain(cart);
        return jpaRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Cart> findByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId).map(CartEntity::toDomain);
    }

    @Override
    public Cart findOrCreateByUserId(UUID userId) {
        return jpaRepository.findByUserId(userId)
                .map(CartEntity::toDomain)
                .orElseGet(() -> {
                    Instant now = Instant.now();
                    Cart newCart = Cart.builder()
                            .id(UUID.randomUUID())
                            .userId(userId)
                            .createdAt(now)
                            .updatedAt(now)
                            .build();
                    return save(newCart);
                });
    }
}
