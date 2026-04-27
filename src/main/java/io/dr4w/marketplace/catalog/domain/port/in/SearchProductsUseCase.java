package io.dr4w.marketplace.catalog.domain.port.in;

import io.dr4w.marketplace.catalog.domain.model.Category;
import io.dr4w.marketplace.catalog.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductsUseCase {

    record Query(String term, Category category) {}

    Page<Product> execute(Query query, Pageable pageable);
}
