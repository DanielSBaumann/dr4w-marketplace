package io.dr4w.marketplace.cart.application;

import io.dr4w.marketplace.cart.domain.model.Cart;
import io.dr4w.marketplace.cart.domain.port.in.RemoveItemFromCartUseCase;
import io.dr4w.marketplace.cart.domain.port.out.CartRepository;
import io.dr4w.marketplace.shared.infrastructure.web.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveItemFromCartService implements RemoveItemFromCartUseCase {

    private final CartRepository cartRepository;

    @Override
    @Transactional
    public Cart execute(Command command) {
        Cart cart = cartRepository.findByUserId(command.userId())
                .orElseThrow(() -> DomainException.notFound("Cart not found"));
        cart.removeItem(command.productId());
        return cartRepository.save(cart);
    }
}
