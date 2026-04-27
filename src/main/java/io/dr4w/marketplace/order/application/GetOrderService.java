package io.dr4w.marketplace.order.application;

import io.dr4w.marketplace.order.domain.model.Order;
import io.dr4w.marketplace.order.domain.port.in.GetOrderUseCase;
import io.dr4w.marketplace.order.domain.port.out.OrderRepository;
import io.dr4w.marketplace.shared.infrastructure.web.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetOrderService implements GetOrderUseCase {

    private final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public Order execute(Query query) {
        Order order = orderRepository.findById(query.orderId())
                .orElseThrow(() -> DomainException.notFound("Order not found"));
        if (!order.getBuyerId().equals(query.userId())) {
            throw DomainException.forbidden("Access denied");
        }
        return order;
    }
}
