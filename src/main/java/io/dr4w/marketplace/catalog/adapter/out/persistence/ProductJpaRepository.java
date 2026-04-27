package io.dr4w.marketplace.catalog.adapter.out.persistence;

import io.dr4w.marketplace.catalog.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("SELECT p FROM ProductEntity p WHERE p.active = true")
    Page<ProductEntity> findAllActive(Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.active = true AND p.category = :category")
    Page<ProductEntity> findActiveByCategory(@Param("category") Category category, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.active = true AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :term, '%')))")
    Page<ProductEntity> findActiveByTerm(@Param("term") String term, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.active = true AND p.category = :category AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :term, '%')))")
    Page<ProductEntity> findActiveByCategoryAndTerm(@Param("category") Category category, @Param("term") String term, Pageable pageable);

    Page<ProductEntity> findByVendorId(UUID vendorId, Pageable pageable);
}
