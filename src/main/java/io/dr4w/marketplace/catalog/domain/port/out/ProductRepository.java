package io.dr4w.marketplace.catalog.domain.port.out;

import io.dr4w.marketplace.catalog.domain.model.Category;
import io.dr4w.marketplace.catalog.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Page<Product> findActiveByFilters(String term, Category category, Pageable pageable);

    Page<Product> findByVendorId(UUID vendorId, Pageable pageable);
}
