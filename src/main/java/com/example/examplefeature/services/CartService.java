package com.example.examplefeature.services;

import com.example.examplefeature.entity.CartItem;
import com.example.examplefeature.entity.Product;
import com.example.examplefeature.entity.User;
import com.example.examplefeature.security.SecurityService;
import com.example.examplefeature.service.CartServiceImpl;
import com.example.examplefeature.service.ProductServiceImpl;
import com.example.examplefeature.service.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {
    
    private final CartServiceImpl cartServiceImpl;
    private final ProductServiceImpl productService;
    private final UserServiceImpl userService;
    private final SecurityService securityService;

    public CartService(CartServiceImpl cartServiceImpl,
                      ProductServiceImpl productService,
                      UserServiceImpl userService,
                      SecurityService securityService) {
        this.cartServiceImpl = cartServiceImpl;
        this.productService = productService;
        this.userService = userService;
        this.securityService = securityService;
    }

    private User getCurrentUser() {
        String username = securityService.getAuthenticatedUsername();
        if (username == null) {
            throw new RuntimeException("User not authenticated");
        }
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Map<String, Object>> getCart() {
        try {
            User user = getCurrentUser();
            List<CartItem> items = cartServiceImpl.getCartItems(user);
            
            return items.stream()
                    .map(item -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", item.getId());
                        map.put("name", item.getProduct().getTitle());
                        map.put("price", item.getProduct().getPrice().doubleValue());
                        map.put("qty", item.getQuantity());
                        map.put("image", item.getProduct().getImage());
                        return map;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    public void addProduct(String name, double price, String image) {
        try {
            User user = getCurrentUser();
            Product product = productService.findAll().stream()
                    .filter(p -> p.getTitle().equals(name))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            
            cartServiceImpl.addItem(user, product.getId(), 1);
        } catch (Exception e) {
            throw new RuntimeException("Error adding product to cart: " + e.getMessage());
        }
    }

    public void removeProduct(String name) {
        try {
            User user = getCurrentUser();
            List<CartItem> items = cartServiceImpl.getCartItems(user);
            
            items.stream()
                    .filter(item -> item.getProduct().getTitle().equals(name))
                    .findFirst()
                    .ifPresent(item -> cartServiceImpl.removeItem(item.getId()));
        } catch (Exception e) {
            throw new RuntimeException("Error removing product from cart: " + e.getMessage());
        }
    }

    public void updateQty(String name, int qty) {
        try {
            User user = getCurrentUser();
            List<CartItem> items = cartServiceImpl.getCartItems(user);
            
            items.stream()
                    .filter(item -> item.getProduct().getTitle().equals(name))
                    .findFirst()
                    .ifPresent(item -> cartServiceImpl.updateItemQuantity(item.getId(), qty));
        } catch (Exception e) {
            throw new RuntimeException("Error updating quantity: " + e.getMessage());
        }
    }

    public void clearCart() {
        try {
            User user = getCurrentUser();
            cartServiceImpl.clearCart(user);
        } catch (Exception e) {
            // Ignore
        }
    }

    public double calculateSubtotal() {
        try {
            User user = getCurrentUser();
            return cartServiceImpl.calculateTotal(user).doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public int getItemCount() {
        try {
            User user = getCurrentUser();
            return cartServiceImpl.getItemCount(user);
        } catch (Exception e) {
            return 0;
        }
    }
}

