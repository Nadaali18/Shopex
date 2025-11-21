package com.example.examplefeature.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AdminProductService {

    public void saveProduct(String name, String category, Double price, 
                          Double stock, String description, String imageName, 
                          byte[] imageData) {
        // حفظ بيانات المنتج في قاعدة البيانات
        System.out.println("=== Saving Product to Database ===");
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + stock);
        System.out.println("Description: " + description);
        System.out.println("Image: " + (imageName != null ? imageName : "No image"));
        
        // حفظ الصورة إذا كانت موجودة
        if (imageData != null && imageName != null) {
            saveProductImage(imageData, imageName);
        }
        
        System.out.println("=====================");
    }

    private void saveProductImage(byte[] imageData, String imageName) {
        try {
            // إنشاء مجلد الصور إذا لم يكن موجوداً
            Path imagesDir = Paths.get("src/main/resources/META-INF/resources/images/products");
            if (!Files.exists(imagesDir)) {
                Files.createDirectories(imagesDir);
            }
            
            // حفظ الصورة
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