package io.dr4w.marketplace.catalog.adapter.out.persistence;

import io.dr4w.marketplace.catalog.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("""
            SELECT p FROM ProductEntity p
            WHERE p.active = true
              AND (:category IS NULL OR p.category = :category)
              AND (:term IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%'))
                                 OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :term, '%')))
            """)
    Page<ProductEntity> findActiveByFilters(
            @Param("term") String term,
            @Param("category") Category category,
            Pageable pageable);

    Page<ProductEntity> findByVendorId(UUID vendorId, Pageable pageable);
}
