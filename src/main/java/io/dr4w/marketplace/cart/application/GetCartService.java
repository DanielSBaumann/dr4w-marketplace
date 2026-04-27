package io.dr4w.marketplace.cart.application;

import io.dr4w.marketplace.cart.domain.model.Cart;
import io.dr4w.marketplace.cart.domain.port.in.GetCartUseCase;
import io.dr4w.marketplace.cart.domain.port.out.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCartService implements GetCartUseCase {

    private final CartRepository cartRepository;

    @Override
    @Transactional(readOnly = true)
    public Cart execute(Query query) {
        return cartRepository.findOrCreateByUserId(query.userId());
    }
}
