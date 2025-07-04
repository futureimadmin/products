package com.futureim.voicecommerce.product.api.controller;

import com.futureim.voicecommerce.product.api.dto.ApiResponse;
import com.futureim.voicecommerce.product.api.dto.ProductDto;
import com.futureim.voicecommerce.product.model.Product;
import com.futureim.voicecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getAllProducts(Pageable pageable) {
        Page<ProductDto> products = productService.getAllProducts(pageable)
            .map(ProductDto::fromEntity);
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProduct(@PathVariable Long id) {
        return productService.getProductById(id)
            .map(product -> ResponseEntity.ok(ApiResponse.success(ProductDto.fromEntity(product))))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Product not found", HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "false") boolean includeSubcategories,
            Pageable pageable) {
        Page<ProductDto> products = productService.getProductsByCategory(categoryId, includeSubcategories, pageable)
            .map(ProductDto::fromEntity);
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> searchProducts(
            @RequestParam String query,
            Pageable pageable) {
        Page<ProductDto> products = productService.searchProducts(query, pageable)
            .map(ProductDto::fromEntity);
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<Page<ProductDto>>> getAvailableProducts(Pageable pageable) {
        Page<ProductDto> products = productService.getAvailableProducts(pageable)
            .map(ProductDto::fromEntity);
        return ResponseEntity.ok(ApiResponse.success(products));
    }

    @GetMapping("/brands")
    public ResponseEntity<ApiResponse<List<String>>> getAllBrands() {
        List<String> brands = productService.getAllBrands();
        return ResponseEntity.ok(ApiResponse.success(brands));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductDto>> createProduct(@Valid @RequestBody ProductDto productDto) {
        Product product = productService.createProduct(ProductDto.toEntity(productDto));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(ProductDto.fromEntity(product), "Product created successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProductDto>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(id, ProductDto.toEntity(productDto));
        return ResponseEntity.ok(ApiResponse.success(ProductDto.fromEntity(product), "Product updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Product deleted successfully"));
    }

    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateStock(
            @PathVariable Long id,
            @RequestParam int quantity) {
        productService.updateStock(id, quantity);
        return ResponseEntity.ok(ApiResponse.success(null, "Stock updated successfully"));
    }
}
