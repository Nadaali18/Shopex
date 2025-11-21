package com.example.examplefeature.services;

import com.example.examplefeature.model.ProductData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import java.util.ArrayList;
import java.util.List;

public class HomeCartService {
    
    @SuppressWarnings("unchecked")
    public static void addProductToCart(String name, String category, String price, String imagePath) {
        List<ProductData> cartItems = (List<ProductData>) UI.getCurrent().getSession()
                .getAttribute("cartItems");
        
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        
        boolean productExists = false;
        for (ProductData item : cartItems) {
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() + 1);
                productExists = true;
                break;
            }
        }
        
        if (!productExists) {
            ProductData newProduct = new ProductData(name, category, price, imagePath);
            cartItems.add(newProduct);
        }
        
        UI.getCurrent().getSession().setAttribute("cartItems", cartItems);
        updateSubtotal(cartItems);
        
        Notification.show("✓ " + name + " added to cart!", 2000, Notification.Position.BOTTOM_START);
    }
    
    private static void updateSubtotal(List<ProductData> cartItems) {
        double subtotal = cartItems.stream()
                .mapToDouble(ProductData::getTotal)
                .sum();
        UI.getCurrent().getSession().setAttribute("subtotal", subtotal);
    }
}