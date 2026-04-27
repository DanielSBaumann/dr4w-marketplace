package io.dr4w.marketplace.catalog.adapter.in.web.dto;

import io.dr4w.marketplace.catalog.domain.model.Category;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank @Size(max = 200) String name,
        @NotBlank @Size(max = 200) String brand,
        @NotBlank String description,
        @NotNull Category category,
        @NotNull @DecimalMin("0.01") BigDecimal price,
        @Min(0) int stockQuantity,
        String imageUrl1,
        String imageUrl2,
        String imageUrl3,
        String imageUrl4
) {}
