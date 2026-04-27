package io.dr4w.marketplace.order.application;

import io.dr4w.marketplace.cart.domain.model.Cart;
import io.dr4w.marketplace.cart.domain.model.CartItem;
import io.dr4w.marketplace.cart.domain.port.out.CartRepository;
import io.dr4w.marketplace.catalog.domain.model.Product;
import io.dr4w.marketplace.catalog.domain.port.out.ProductRepository;
import io.dr4w.marketplace.order.domain.model.Order;
import io.dr4w.marketplace.order.domain.model.OrderItem;
import io.dr4w.marketplace.order.domain.model.OrderStatus;
import io.dr4w.marketplace.order.domain.port.in.PlaceOrderUseCase;
import io.dr4w.marketplace.order.domain.port.out.OrderRepository;
import io.dr4w.marketplace.payment.application.PlatformFeePolicy;
import io.dr4w.marketplace.payment.domain.model.Payment;
import io.dr4w.marketplace.payment.domain.model.PaymentStatus;
import io.dr4w.marketplace.payment.domain.port.out.PaymentGateway;
import io.dr4w.marketplace.payment.domain.port.out.PaymentRepository;
import io.dr4w.marketplace.shared.infrastructure.web.DomainException;
import io.dr4w.marketplace.wallet.domain.model.Wallet;
import io.dr4w.marketplace.wallet.domain.port.out.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final WalletRepository walletRepository;
    private final PlatformFeePolicy feePolicy;

    @Override
    @Transactional
    public Order execute(Command command) {
        Cart cart = cartRepository.findByUserId(command.userId())
                .orElseThrow(() -> DomainException.notFound("Cart not found"));

        if (cart.isEmpty()) {
            throw DomainException.badRequest("Cart is empty");
        }

        List<OrderItem> orderItems = buildAndValidateOrderItems(cart, command.userId());
        BigDecimal total = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Instant now = Instant.now();
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .buyerId(command.userId())
                .status(OrderStatus.PENDING)
                .items(orderItems)
                .totalAmount(total)
                .createdAt(now)
                .updatedAt(now)
                .build();

        order = orderRepository.save(order);

        PaymentGateway.ChargeResult chargeResult = paymentGateway.charge(total, command.paymentMethod());

        Payment payment = Payment.builder()
                .id(UUID.randomUUID())
                .orderId(order.getId())
                .status(chargeResult.approved() ? PaymentStatus.APPROVED : PaymentStatus.REJECTED)
                .paymentMethod(command.paymentMethod())
                .amount(total)
                .gatewayReference(chargeResult.gatewayReference())
                .createdAt(Instant.now())
                .build();

        paymentRepository.save(payment);

        if (chargeResult.approved()) {
            order.markPaid();
            order = orderRepository.save(order);
            creditVendorWallets(orderItems, order.getId());
            clearCart(cart);
        } else {
            throw DomainException.badRequest("Payment was rejected");
        }

        return order;
    }

    private List<OrderItem> buildAndValidateOrderItems(Cart cart, UUID buyerId) {
        List<OrderItem> items = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> DomainException.notFound("Product not found: " + cartItem.getProductId()));

            if (!product.hasStock(cartItem.getQuantity())) {
                throw DomainException.badRequest("Insufficient stock for: " + product.getName());
            }

            product.decrementStock(cartItem.getQuantity());
            productRepository.save(product);

            BigDecimal unitPrice = cartItem.getPriceSnapshot();
            items.add(OrderItem.builder()
                    .id(UUID.randomUUID())
                    .productId(product.getId())
                    .vendorId(product.getVendorId())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(unitPrice)
                    .totalPrice(unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                    .build());
        }
        return items;
    }

    private void creditVendorWallets(List<OrderItem> items, UUID orderId) {
        Map<UUID, BigDecimal> vendorTotals = items.stream()
                .collect(Collectors.groupingBy(
                        OrderItem::getVendorId,
                        Collectors.reducing(BigDecimal.ZERO, OrderItem::getTotalPrice, BigDecimal::add)
                ));

        vendorTotals.forEach((vendorId, amount) -> {
            Wallet wallet = walletRepository.findOrCreateByUserId(vendorId);
            wallet.credit(feePolicy.vendorShare(amount), "Order payment", orderId);
            walletRepository.save(wallet);
        });
    }

    private void clearCart(Cart cart) {
        cart.clear();
        cartRepository.save(cart);
    }
}
