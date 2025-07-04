package com.futureim.voicecommerce.product.api.controller;

import com.futureim.voicecommerce.product.api.dto.ApiResponse;
import com.futureim.voicecommerce.product.api.dto.CategoryDto;
import com.futureim.voicecommerce.product.model.Category;
import com.futureim.voicecommerce.product.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/root")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getRootCategories() {
        List<CategoryDto> categories = categoryService.getAllRootCategories().stream()
            .map(CategoryDto::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategoryTree() {
        List<CategoryDto> categories = categoryService.getAllRootCategoriesWithSubcategories().stream()
            .map(CategoryDto::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
            .map(category -> ResponseEntity.ok(ApiResponse.success(CategoryDto.fromEntity(category))))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Category not found", HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/{id}/subcategories")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getSubcategories(@PathVariable Long id) {
        List<CategoryDto> subcategories = categoryService.getSubcategories(id).stream()
            .map(CategoryDto::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(subcategories));
    }

    @GetMapping("/{id}/path")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getCategoryPath(@PathVariable Long id) {
        List<CategoryDto> path = categoryService.getCategoryPath(id).stream()
            .map(CategoryDto::fromEntity)
            .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(path));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(CategoryDto.toEntity(categoryDto));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(CategoryDto.fromEntity(category), "Category created successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.updateCategory(id, CategoryDto.toEntity(categoryDto));
        return ResponseEntity.ok(ApiResponse.success(CategoryDto.fromEntity(category), "Category updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Category deleted successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<CategoryDto>>> searchCategories(
            @RequestParam String query,
            Pageable pageable) {
        Page<CategoryDto> categories = categoryService.searchCategories(query, pageable)
            .map(CategoryDto::fromEntity);
        return ResponseEntity.ok(ApiResponse.success(categories));
    }
}
