package io.dr4w.marketplace.catalog.adapter.out.persistence;

import io.dr4w.marketplace.catalog.domain.model.Category;
import io.dr4w.marketplace.catalog.domain.model.Product;
import io.dr4w.marketplace.catalog.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepository {

    private final ProductJpaRepository jpa;

    @Override
    public Product save(Product product) {
        return jpa.save(ProductEntity.fromDomain(product)).toDomain();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpa.findById(id).map(ProductEntity::toDomain);
    }

    @Override
    public Page<Product> findActiveByFilters(String term, Category category, Pageable pageable) {
        return jpa.findActiveByFilters(term, category, pageable).map(ProductEntity::toDomain);
    }

    @Override
    public Page<Product> findByVendorId(UUID vendorId, Pageable pageable) {
        return jpa.findByVendorId(vendorId, pageable).map(ProductEntity::toDomain);
    }
}
