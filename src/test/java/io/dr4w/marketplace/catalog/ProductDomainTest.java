package io.dr4w.marketplace.catalog;

import io.dr4w.marketplace.catalog.domain.model.Category;
import io.dr4w.marketplace.catalog.domain.model.Money;
import io.dr4w.marketplace.catalog.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductDomainTest {

    private Product activeProduct(int stock) {
        Money price = new Money(new BigDecimal("100.00"));
        return Product.builder()
                .id(UUID.randomUUID())
                .vendorId(UUID.randomUUID())
                .name("Test Product")
                .brand("TestBrand")
                .category(Category.ELECTRONICS)
                .price(price)
                .discountPercentage(0)
                .finalPrice(price)
                .stockQuantity(stock)
                .soldQuantity(0)
                .active(true)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    void hasStockReturnsTrueWhenSufficient() {
        Product product = activeProduct(10);
        assertThat(product.hasStock(5)).isTrue();
        assertThat(product.hasStock(10)).isTrue();
    }

    @Test
    void hasStockReturnsFalseWhenInsufficient() {
        Product product = activeProduct(3);
        assertThat(product.hasStock(4)).isFalse();
    }

    @Test
    void hasStockReturnsFalseWhenInactive() {
        Money price = new Money(new BigDecimal("100.00"));
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .vendorId(UUID.randomUUID())
                .name("Inactive")
                .brand("B")
                .category(Category.ELECTRONICS)
                .price(price)
                .discountPercentage(0)
                .finalPrice(price)
                .stockQuantity(100)
                .soldQuantity(0)
                .active(false)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        assertThat(product.hasStock(1)).isFalse();
    }

    @Test
    void decrementStockReducesQuantityAndIncreasesSold() {
        Product product = activeProduct(10);

        product.decrementStock(3);

        assertThat(product.getStockQuantity()).isEqualTo(7);
        assertThat(product.getSoldQuantity()).isEqualTo(3);
    }

    @Test
    void decrementStockBeyondAvailableThrows() {
        Product product = activeProduct(2);

        assertThatThrownBy(() -> product.decrementStock(5))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Insufficient stock");
    }

    @Test
    void applyDiscountCalculatesCorrectFinalPrice() {
        Money price = new Money(new BigDecimal("200.00"));
        assertThat(price.applyDiscount(10).amount()).isEqualByComparingTo("180.00");
        assertThat(price.applyDiscount(0).amount()).isEqualByComparingTo("200.00");
        assertThat(price.applyDiscount(100).amount()).isEqualByComparingTo("0.00");
    }
}
