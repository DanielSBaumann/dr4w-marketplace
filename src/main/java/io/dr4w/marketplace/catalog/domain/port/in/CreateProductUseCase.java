package io.dr4w.marketplace.catalog.domain.port.in;

import io.dr4w.marketplace.catalog.domain.model.Category;
import io.dr4w.marketplace.catalog.domain.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateProductUseCase {

    record Command(UUID vendorId, String name, String brand, String description,
                   Category category, BigDecimal price, int stockQuantity,
                   String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4) {}

    Product execute(Command command);
}
