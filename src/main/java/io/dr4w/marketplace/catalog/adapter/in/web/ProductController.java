package io.dr4w.marketplace.catalog.adapter.in.web;

import io.dr4w.marketplace.catalog.adapter.in.web.dto.CreateProductRequest;
import io.dr4w.marketplace.catalog.adapter.in.web.dto.ProductResponse;
import io.dr4w.marketplace.catalog.domain.model.Category;
import io.dr4w.marketplace.catalog.domain.model.Product;
import io.dr4w.marketplace.catalog.domain.port.in.CreateProductUseCase;
import io.dr4w.marketplace.catalog.domain.port.in.SearchProductsUseCase;
import io.dr4w.marketplace.catalog.domain.port.out.ProductRepository;
import io.dr4w.marketplace.shared.infrastructure.security.JwtService;
import io.dr4w.marketplace.shared.infrastructure.web.DomainException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product catalog management")
public class ProductController {

    private final CreateProductUseCase createProduct;
    private final SearchProductsUseCase searchProducts;
    private final ProductRepository productRepository;
    private final JwtService jwtService;

    @GetMapping
    @Operation(summary = "Search products")
    public Page<ProductResponse> search(
            @RequestParam(required = false) String term,
            @RequestParam(required = false) Category category,
            @PageableDefault(size = 20) Pageable pageable) {
        return searchProducts.execute(new SearchProductsUseCase.Query(term, category), pageable)
                .map(ProductResponse::from);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ProductResponse getById(@PathVariable UUID id) {
        return productRepository.findById(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> DomainException.notFound("Product not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('VENDOR') or hasRole('ADMIN')")
    @Operation(summary = "Create a new product", security = @SecurityRequirement(name = "Bearer"))
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request,
                                  HttpServletRequest httpRequest) {
        UUID vendorId = extractUserId(httpRequest);
        Product product = createProduct.execute(new CreateProductUseCase.Command(
                vendorId, request.name(), request.brand(), request.description(),
                request.category(), request.price(), request.stockQuantity(),
                request.imageUrl1(), request.imageUrl2(), request.imageUrl3(), request.imageUrl4()
        ));
        return ProductResponse.from(product);
    }

    private UUID extractUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return jwtService.extractUserId(token);
    }
}
