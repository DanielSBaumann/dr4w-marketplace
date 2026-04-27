package io.dr4w.marketplace.order.application;

import io.dr4w.marketplace.order.domain.model.Order;
import io.dr4w.marketplace.order.domain.port.in.ListBuyerOrdersUseCase;
import io.dr4w.marketplace.order.domain.port.out.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ListBuyerOrdersService implements ListBuyerOrdersUseCase {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Order> execute(Query query) {
        return orderRepository.findByBuyerId(query.userId(), query.pageable());
    }
}
