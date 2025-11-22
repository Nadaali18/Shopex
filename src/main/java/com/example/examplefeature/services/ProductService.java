package com.example.examplefeature.services;

import com.example.examplefeature.entity.Product;
import com.example.examplefeature.mapper.ProductMapper;
import com.example.examplefeature.service.ProductServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductServiceImpl productServiceImpl;
    private final ProductMapper productMapper;

    public ProductService(ProductServiceImpl productServiceImpl, ProductMapper productMapper) {
        this.productServiceImpl = productServiceImpl;
        this.productMapper = productMapper;
    }

    public List<com.example.examplefeature.model.Product> getAllProducts() {
        return productServiceImpl.findAll().stream()
                .map(productMapper::toModel)
                .collect(Collectors.toList());
    }

    public void updateProduct(com.example.examplefeature.model.Product updatedProduct) {
        List<Product> products = productServiceImpl.findAll();
        
        Optional<Product> existingProduct = products.stream()
                .filter(p -> p.getTitle().equals(updatedProduct.getName()))
                .findFirst();

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(BigDecimal.valueOf(updatedProduct.getPrice()));
            product.setStock(updatedProduct.getStock());
            
            productServiceImpl.update(product.getId(), product);
            System.out.println("Product updated: " + updatedProduct.getName());
        } else {
            // If product doesn't exist, add it
            Product newProduct = productMapper.toEntity(updatedProduct);
            productServiceImpl.create(newProduct);
            System.out.println("New product added: " + updatedProduct.getName());
        }
    }

    public com.example.examplefeature.model.Product getProductByName(String name) {
        return productServiceImpl.findAll().stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(name))
                .findFirst()
                .map(productMapper::toModel)
                .orElse(null);
    }

    public void deleteProduct(com.example.examplefeature.model.Product product) {
        productServiceImpl.findAll().stream()
                .filter(p -> p.getTitle().equals(product.getName()))
                .findFirst()
                .ifPresent(p -> productServiceImpl.delete(p.getId()));
    }

    public void addProduct(com.example.examplefeature.model.Product product) {
        Product entity = productMapper.toEntity(product);
        productServiceImpl.create(entity);
    }
    
    // New methods for direct entity access
    public Product getEntityByName(String name) {
        return productServiceImpl.findAll().stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    
    public Product getEntityById(Long id) {
        return productServiceImpl.findById(id).orElse(null);
    }
}
