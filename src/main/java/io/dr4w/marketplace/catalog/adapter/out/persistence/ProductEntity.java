package io.dr4w.marketplace.catalog.adapter.out.persistence;

import io.dr4w.marketplace.catalog.domain.model.Category;
import io.dr4w.marketplace.catalog.domain.model.Money;
import io.dr4w.marketplace.catalog.domain.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductEntity {

    @Id
    private UUID id;

    @Column(name = "vendor_id", nullable = false)
    private UUID vendorId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 200)
    private String brand;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "discount_percentage", nullable = false)
    private int discountPercentage;

    @Column(name = "final_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal finalPrice;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "sold_quantity", nullable = false)
    private int soldQuantity;

    @Column(name = "image_url_1", length = 500)
    private String imageUrl1;
    @Column(name = "image_url_2", length = 500)
    private String imageUrl2;
    @Column(name = "image_url_3", length = 500)
    private String imageUrl3;
    @Column(name = "image_url_4", length = 500)
    private String imageUrl4;

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Product toDomain() {
        return Product.builder()
                .id(id).vendorId(vendorId).name(name).brand(brand).description(description)
                .category(category).price(Money.of(price)).discountPercentage(discountPercentage)
                .finalPrice(Money.of(finalPrice)).stockQuantity(stockQuantity).soldQuantity(soldQuantity)
                .imageUrl1(imageUrl1).imageUrl2(imageUrl2).imageUrl3(imageUrl3).imageUrl4(imageUrl4)
                .active(active).createdAt(createdAt).updatedAt(updatedAt)
                .build();
    }

    public static ProductEntity fromDomain(Product p) {
        return ProductEntity.builder()
                .id(p.getId()).vendorId(p.getVendorId()).name(p.getName()).brand(p.getBrand())
                .description(p.getDescription()).category(p.getCategory())
                .price(p.getPrice().amount()).discountPercentage(p.getDiscountPercentage())
                .finalPrice(p.getFinalPrice().amount()).stockQuantity(p.getStockQuantity())
                .soldQuantity(p.getSoldQuantity()).imageUrl1(p.getImageUrl1()).imageUrl2(p.getImageUrl2())
                .imageUrl3(p.getImageUrl3()).imageUrl4(p.getImageUrl4()).active(p.isActive())
                .createdAt(p.getCreatedAt()).updatedAt(p.getUpdatedAt())
                .build();
    }
}
