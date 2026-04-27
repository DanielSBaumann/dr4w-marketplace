package io.dr4w.marketplace.cart.application;

import io.dr4w.marketplace.cart.domain.model.Cart;
import io.dr4w.marketplace.cart.domain.port.in.AddItemToCartUseCase;
import io.dr4w.marketplace.cart.domain.port.out.CartRepository;
import io.dr4w.marketplace.catalog.domain.port.out.ProductRepository;
import io.dr4w.marketplace.shared.infrastructure.web.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddItemToCartService implements AddItemToCartUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Cart execute(Command command) {
        var product = productRepository.findById(command.productId())
                .orElseThrow(() -> DomainException.notFound("Product not found"));

        if (!product.hasStock(command.quantity())) {
            throw DomainException.badRequest("Insufficient stock for product: " + product.getName());
        }

        Cart cart = cartRepository.findOrCreateByUserId(command.userId());
        cart.addItem(product.getId(), command.quantity(), product.getFinalPrice().amount());
        return cartRepository.save(cart);
    }
}
