package com.example.examplefeature.services;

import com.example.examplefeature.entity.User;
import com.example.examplefeature.security.SecurityService;
import com.example.examplefeature.service.CartServiceImpl;
import com.example.examplefeature.service.ProductServiceImpl;
import com.example.examplefeature.service.UserServiceImpl;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;

@Service
public class HomeCartService {
    
    private final CartServiceImpl cartService;
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;
    private final SecurityService securityService;
    
    public HomeCartService(CartServiceImpl cartService, 
                          ProductServiceImpl productService,
                          UserServiceImpl userService,
                          SecurityService securityService) {
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
        this.securityService = securityService;
    }
    
    public void addProductToCart(String name, String category, String price, String imagePath) {
        try {
            // Get current user
            String username = securityService.getAuthenticatedUsername();
            if (username == null) {
                Notification.show("Please login to add items to cart", 3000, Notification.Position.MIDDLE);
                return;
            }
            
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Find product by name
            var product = productService.findAll().stream()
                    .filter(p -> p.getTitle().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
            // Add to cart
            cartService.addItem(user, product.getId(), 1);
            
            Notification.show("✓ " + name + " added to cart!", 2000, Notification.Position.BOTTOM_START);
        } catch (Exception e) {
            Notification.show("Error adding to cart: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }
}
