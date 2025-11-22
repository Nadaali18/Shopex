package com.example.examplefeature.mapper;

import com.example.examplefeature.entity.Product;
import com.example.examplefeature.model.ProductData;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductMapper {
    
    public ProductData toProductData(Product entity) {
        if (entity == null) {
            return null;
        }
        
        ProductData data = new ProductData();
        data.setName(entity.getTitle());
        data.setCategory(entity.getCategory());
        data.setPrice("$" + entity.getPrice().toString());
        data.setImagePath(entity.getImage());
        data.setQuantity(1);
        
        return data;
    }
    
    public Product toEntity(ProductData data) {
        if (data == null) {
            return null;
        }
        
        Product entity = new Product();
        entity.setTitle(data.getName());
        entity.setCategory(data.getCategory());
        entity.setPrice(BigDecimal.valueOf(data.getNumericPrice()));
        entity.setImage(data.getImagePath());
        entity.setStock(50); // Default stock
        
        return entity;
    }
    
    public com.example.examplefeature.model.Product toModel(Product entity) {
        if (entity == null) {
            return null;
        }
        
        com.example.examplefeature.model.Product model = new com.example.examplefeature.model.Product();
        model.setName(entity.getTitle());
        model.setCategory(entity.getCategory());
        model.setPrice(entity.getPrice().doubleValue());
        model.setStock(entity.getStock());
        
        return model;
    }
    
    public Product toEntity(com.example.examplefeature.model.Product model) {
        if (model == null) {
            return null;
        }
        
        Product entity = new Product();
        entity.setTitle(model.getName());
        entity.setCategory(model.getCategory());
        entity.setPrice(BigDecimal.valueOf(model.getPrice()));
        entity.setStock(model.getStock());
        
        return entity;
    }
}
