package io.dr4w.marketplace.catalog.application;

import io.dr4w.marketplace.catalog.domain.model.Product;
import io.dr4w.marketplace.catalog.domain.port.in.SearchProductsUseCase;
import io.dr4w.marketplace.catalog.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchProductsService implements SearchProductsUseCase {

    private final ProductRepository productRepository;

    @Override
    public Page<Product> execute(Query query, Pageable pageable) {
        return productRepository.findActiveByFilters(query.term(), query.category(), pageable);
    }
}
