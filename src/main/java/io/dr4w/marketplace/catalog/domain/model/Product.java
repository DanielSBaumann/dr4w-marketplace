package io.dr4w.marketplace.catalog.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class Product {

    private final UUID id;
    private final UUID vendorId;
    private String name;
    private String brand;
    private String description;
    private Category category;
    private Money price;
    private int discountPercentage;
    private Money finalPrice;
    private int stockQuantity;
    private int soldQuantity;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;

    public void update(int stockQuantity, int discountPercentage, boolean active) {
        this.stockQuantity    = stockQuantity;
        this.discountPercentage = discountPercentage;
        this.finalPrice       = price.applyDiscount(discountPercentage);
        this.active           = active;
        this.updatedAt        = Instant.now();
    }

    public void decrementStock(int quantity) {
        if (quantity > stockQuantity) throw new IllegalStateException("Insufficient stock");
        this.stockQuantity -= quantity;
        this.soldQuantity  += quantity;
        this.updatedAt      = Instant.now();
    }

    public boolean hasStock(int quantity) {
        return active && stockQuantity >= quantity;
    }
}
