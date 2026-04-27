package io.dr4w.marketplace.catalog.adapter.in.web.dto;

import io.dr4w.marketplace.catalog.domain.model.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id, UUID vendorId, String name, String brand, String description,
        String category, BigDecimal price, int discountPercentage,
        BigDecimal finalPrice, int stockQuantity, int soldQuantity,
        String imageUrl1, String imageUrl2, String imageUrl3, String imageUrl4,
        boolean active, Instant createdAt
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getId(), p.getVendorId(), p.getName(), p.getBrand(), p.getDescription(),
                p.getCategory().name(), p.getPrice().amount(), p.getDiscountPercentage(),
                p.getFinalPrice().amount(), p.getStockQuantity(), p.getSoldQuantity(),
                p.getImageUrl1(), p.getImageUrl2(), p.getImageUrl3(), p.getImageUrl4(),
                p.isActive(), p.getCreatedAt()
        );
    }
}
