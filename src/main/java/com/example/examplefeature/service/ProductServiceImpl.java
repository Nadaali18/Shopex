package com.example.examplefeature.service;

import com.example.examplefeature.entity.Product;
import com.example.examplefeature.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl {
    
    private final ProductRepository productRepository;
    
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    public List<Product> searchByTitle(String title) {
        return productRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<Product> findAvailableProducts() {
        return productRepository.findByStockGreaterThan(0);
    }
    
    public List<Product> findAvailableProductsByCategory(String category) {
        return productRepository.findByCategoryAndStockGreaterThan(category, 0);
    }
    
    public Product create(Product product) {
        return productRepository.save(product);
    }
    
    public Product update(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setTitle(updatedProduct.getTitle());
                    product.setPrice(updatedProduct.getPrice());
                    product.setCategory(updatedProduct.getCategory());
                    product.setStock(updatedProduct.getStock());
                    product.setImage(updatedProduct.getImage());
                    product.setDescription(updatedProduct.getDescription());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
    
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }
    
    public long count() {
        return productRepository.count();
    }
    
    public void decreaseStock(Long productId, int quantity) {
        productRepository.findById(productId).ifPresent(product -> {
            int newStock = product.getStock() - quantity;
            if (newStock < 0) {
                throw new RuntimeException("Insufficient stock for product: " + product.getTitle());
            }
            product.setStock(newStock);
            productRepository.save(product);
        });
    }
    
    public void increaseStock(Long productId, int quantity) {
        productRepository.findById(productId).ifPresent(product -> {
            product.setStock(product.getStock() + quantity);
            productRepository.save(product);
        });
    }
}
