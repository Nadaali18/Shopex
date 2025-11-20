package com.example.examplefeature.model;

import java.io.Serializable;

public class ProductData implements Serializable {
    private String name;
    private String category;
    private String price;
    private int numericPrice;
    private String imagePath;
    private int quantity;

    public ProductData() {
        // constructor فارغ مطلوب للـ serialization
    }

    public ProductData(String name, String category, String price, String imagePath) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imagePath = imagePath;
        this.quantity = 1;
        this.numericPrice = convertPriceToInt(price);
    }

    private int convertPriceToInt(String price) {
        try {
            return (int) Double.parseDouble(price.replace("$", "").trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getPrice() { return price; }
    public void setPrice(String price) { 
        this.price = price;
        this.numericPrice = convertPriceToInt(price);
    }
    
    public int getNumericPrice() { return numericPrice; }
    public void setNumericPrice(int numericPrice) { 
        this.numericPrice = numericPrice;
        this.price = "$" + numericPrice;
    }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public int getTotal() { return numericPrice * quantity; }
}