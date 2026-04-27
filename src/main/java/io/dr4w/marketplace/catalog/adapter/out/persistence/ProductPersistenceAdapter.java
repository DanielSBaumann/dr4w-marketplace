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
        boolean hasTerm = term != null && !term.isBlank();
        boolean hasCategory = category != null;
        Page<ProductEntity> page;
        if (hasTerm && hasCategory) {
            page = jpa.findActiveByCategoryAndTerm(category, term, pageable);
        } else if (hasCategory) {
            page = jpa.findActiveByCategory(category, pageable);
        } else if (hasTerm) {
            page = jpa.findActiveByTerm(term, pageable);
        } else {
            page = jpa.findAllActive(pageable);
        }
        return page.map(ProductEntity::toDomain);
    }

    @Override
    public Page<Product> findByVendorId(UUID vendorId, Pageable pageable) {
        return jpa.findByVendorId(vendorId, pageable).map(ProductEntity::toDomain);
    }
}
