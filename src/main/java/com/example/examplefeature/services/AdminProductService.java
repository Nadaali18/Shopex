package com.example.examplefeature.services;

import com.example.examplefeature.entity.Product;
import com.example.examplefeature.service.ProductServiceImpl;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AdminProductService {

    private final ProductServiceImpl productService;

    public AdminProductService(ProductServiceImpl productService) {
        this.productService = productService;
    }

    public void saveProduct(String name, String category, Double price, 
                          Double stock, String description, String imageName, 
                          byte[] imageData) {
        // Save product to database
        System.out.println("=== Saving Product to Database ===");
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + stock);
        System.out.println("Description: " + description);
        System.out.println("Image: " + (imageName != null ? imageName : "No image"));
        
        // Save image if exists
        if (imageData != null && imageName != null) {
            saveProductImage(imageData, imageName);
        }
        
        // Create and save product entity
        Product product = new Product();
        product.setTitle(name);
        product.setCategory(category);
        product.setPrice(BigDecimal.valueOf(price));
        product.setStock(stock.intValue());
        product.setDescription(description);
        product.setImage(imageName != null ? imageName : "default.jpg");
        
        productService.create(product);
        
        System.out.println("Product saved successfully!");
        System.out.println("=====================");
    }

    private void saveProductImage(byte[] imageData, String imageName) {
        try {
            // Create images folder if it doesn't exist
            Path imagesDir = Paths.get("src/main/resources/META-INF/resources/images");
            if (!Files.exists(imagesDir)) {
                Files.createDirectories(imagesDir);
            }
            
            // Save image
            Path imagePath = imagesDir.resolve(imageName);
            try (FileOutputStream fos = new FileOutputStream(imagePath.toFile())) {
                fos.write(imageData);
            }
            
            System.out.println("Image saved: " + imagePath);
        } catch (IOException e) {
            System.err.println("Error saving image: " + e.getMessage());
        }
    }

    public boolean validateProductData(String name, String category, Double price, Double stock) {
        return name != null && !name.trim().isEmpty() &&
               category != null && !category.trim().isEmpty() &&
               price != null && price > 0 &&
               stock != null && stock >= 0;
    }
}
