package com.futureim.voicecommerce.product.service.impl;

import com.futureim.voicecommerce.product.model.Category;
import com.futureim.voicecommerce.product.model.Product;
import com.futureim.voicecommerce.product.service.ProductService;
import com.futureim.voicecommerce.product.repository.CategoryRepository;
import com.futureim.voicecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getProductsByCategory(Long categoryId, boolean includeSubcategories, Pageable pageable) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException("Category not found");
        }

        if (includeSubcategories) {
            return productRepository.findByCategoryIdIncludingSubcategories(categoryId, pageable);
        } else {
            return productRepository.findByCategoryId(categoryId, pageable);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        validateCategory(product.getCategory().getId());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        validateCategory(product.getCategory().getId());

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setModel(product.getModel());
        existingProduct.setSku(product.getSku());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setDiscountPrice(product.getDiscountPrice());
        existingProduct.setActive(product.isActive());
        existingProduct.getImageUrls().clear();
        existingProduct.getImageUrls().addAll(product.getImageUrls());

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> searchProducts(String query, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(query, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getAvailableProducts(Pageable pageable) {
        return productRepository.findAllAvailable(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllBrands() {
        return productRepository.findAllBrands();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getProductsByBrand(String brand, Pageable pageable) {
        return productRepository.findAll(pageable); // TODO: Implement brand filtering
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public void updateStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.updateStock(quantity);
        productRepository.save(product);
    }

    private void validateCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        if (!category.isActive()) {
            throw new IllegalStateException("Category is not active");
        }
    }
}
