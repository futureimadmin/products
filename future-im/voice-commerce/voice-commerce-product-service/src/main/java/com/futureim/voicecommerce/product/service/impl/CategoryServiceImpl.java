package com.futureim.voicecommerce.product.service.impl;

import com.futureim.voicecommerce.product.model.Category;
import com.futureim.voicecommerce.product.service.CategoryService;
import com.futureim.voicecommerce.product.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllRootCategories() {
        return categoryRepository.findAllRootCategories();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllRootCategoriesWithSubcategories() {
        return categoryRepository.findAllRootCategoriesWithSubcategories();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getSubcategories(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryWithProducts(Long id) {
        return categoryRepository.findByIdWithProducts(id);
    }

    @Override
    public Category createCategory(Category category) {
        if (category.getParent() != null) {
            categoryRepository.findById(category.getParent().getId())
                .orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setImageUrl(category.getImageUrl());
        existingCategory.setDisplayOrder(category.getDisplayOrder());
        existingCategory.setActive(category.isActive());

        if (category.getParent() != null && !category.getParent().getId().equals(existingCategory.getParent().getId())) {
            categoryRepository.findById(category.getParent().getId())
                .orElseThrow(() -> new EntityNotFoundException("Parent category not found"));
            existingCategory.setParent(category.getParent());
        }

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        if (!category.getSubcategories().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with subcategories");
        }

        if (!category.getProducts().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with products");
        }

        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoryPath(Long categoryId) {
        List<Category> path = new ArrayList<>();
        Optional<Category> currentCategory = categoryRepository.findById(categoryId);

        while (currentCategory.isPresent()) {
            path.add(0, currentCategory.get());
            currentCategory = Optional.ofNullable(currentCategory.get().getParent());
        }

        return path;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Category> searchCategories(String query, Pageable pageable) {
        // TODO: Implement search functionality
        return categoryRepository.findAll(pageable);
    }
}
