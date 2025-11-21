package com.example.examplefeature.services;
import com.example.examplefeature.model.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private List<Product> products;

    public ProductService() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        products = new ArrayList<>(Arrays.asList(
            new Product("SweetShirt", "Clothes", 300, 50),
            new Product("Sneakers", "Shoes", 150, 30),
            new Product("Smart Watch", "Electronics", 500, 20),
            new Product("Backpack", "Accessories", 80, 40),
            new Product("Water Bottle", "Home", 25, 100)
        ));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public void updateProduct(Product updatedProduct) {
        Optional<Product> existingProduct = products.stream()
                .filter(p -> p.getName().equals(updatedProduct.getName()))
                .findFirst();

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(updatedProduct.getPrice());
            product.setStock(updatedProduct.getStock());
            
            System.out.println("Product updated: " + updatedProduct.getName());
        } else {
            // إذا المنتج غير موجود، أضفه جديد
            products.add(updatedProduct);
            System.out.println("New product added: " + updatedProduct.getName());
        }
    }

    public Product getProductByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void deleteProduct(Product product) {
        products.removeIf(p -> p.getName().equals(product.getName()));
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}