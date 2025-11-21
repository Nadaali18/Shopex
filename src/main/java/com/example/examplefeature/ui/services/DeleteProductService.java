package com.example.examplefeature.ui.services;

import com.example.examplefeature.model.ProductsDeletePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeleteProductService {

    private List<ProductsDeletePage> products;
    public DeleteProductService() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        products = new ArrayList<>(Arrays.asList(
            new ProductsDeletePage("SweetShirt", "Clothes", 300, 50),
            new ProductsDeletePage("Sneakers", "Shoes", 150, 30),
            new ProductsDeletePage("Smart Watch", "Electronics", 500, 20),
            new ProductsDeletePage("Backpack", "Accessories", 80, 40),
            new ProductsDeletePage("Water Bottle", "Home", 25, 100)
        ));
    }

    public List<ProductsDeletePage> getAllProducts() {
        return new ArrayList<>(products);      
    }

    public void deleteProduct(ProductsDeletePage product) {
        products.removeIf(p -> p.getName().equals(product.getName()) && 
                              p.getCategory().equals(product.getCategory()));
        
        System.out.println("Product deleted: " + product.getName());
    }

    public ProductsDeletePage getProductByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(ProductsDeletePage product) {
        products.add(product);
    }

    public void updateProduct(ProductsDeletePage updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            ProductsDeletePage product = products.get(i);
            if (product.getName().equals(updatedProduct.getName())) {
                products.set(i, updatedProduct);
                break;
            }
        }
    }
}