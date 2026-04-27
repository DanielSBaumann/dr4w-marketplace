package io.dr4w.marketplace.catalog.application;

import io.dr4w.marketplace.catalog.domain.model.Money;
import io.dr4w.marketplace.catalog.domain.model.Product;
import io.dr4w.marketplace.catalog.domain.port.in.CreateProductUseCase;
import io.dr4w.marketplace.catalog.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateProductService implements CreateProductUseCase {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product execute(Command command) {
        Money price = Money.of(command.price());
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .vendorId(command.vendorId())
                .name(command.name())
                .brand(command.brand())
                .description(command.description())
                .category(command.category())
                .price(price)
                .discountPercentage(0)
                .finalPrice(price)
                .stockQuantity(command.stockQuantity())
                .soldQuantity(0)
                .imageUrl1(command.imageUrl1())
                .imageUrl2(command.imageUrl2())
                .imageUrl3(command.imageUrl3())
                .imageUrl4(command.imageUrl4())
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return productRepository.save(product);
    }
}
