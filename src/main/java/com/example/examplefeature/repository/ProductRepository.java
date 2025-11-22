package com.example.examplefeature.repository;

import com.example.examplefeature.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByCategory(String category);
    
    List<Product> findByTitleContainingIgnoreCase(String title);
    
    List<Product> findByStockGreaterThan(Integer stock);
    
    List<Product> findByCategoryAndStockGreaterThan(String category, Integer stock);
}
