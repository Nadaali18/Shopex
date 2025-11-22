package com.example.examplefeature.services;

import com.example.examplefeature.entity.CartItem;
import com.example.examplefeature.entity.User;
import com.example.examplefeature.mapper.ProductMapper;
import com.example.examplefeature.model.ProductData;
import com.example.examplefeature.security.SecurityService;
import com.example.examplefeature.service.CartServiceImpl;
import com.example.examplefeature.service.UserServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final CartServiceImpl cartService;
    private final UserServiceImpl userService;
    private final SecurityService securityService;
    private final ProductMapper productMapper;

    public CheckoutService(CartServiceImpl cartService, 
                          UserServiceImpl userService,
                          SecurityService securityService,
                          ProductMapper productMapper) {
        this.cartService = cartService;
        this.userService = userService;
        this.securityService = securityService;
        this.productMapper = productMapper;
    }

    public List<ProductData> getCartItemsFromSession() {
        try {
            String username = securityService.getAuthenticatedUsername();
            if (username == null) {
                return List.of();
            }
            
            User user = userService.findByUsername(username).orElse(null);
            if (user == null) {
                return List.of();
            }
            
            List<CartItem> cartItems = cartService.getCartItems(user);
            return cartItems.stream()
                    .map(item -> {
                        ProductData data = productMapper.toProductData(item.getProduct());
                        data.setQuantity(item.getQuantity());
                        return data;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return List.of();
        }
    }

    public double getSubtotalFromSession() {
        try {
            String username = securityService.getAuthenticatedUsername();
            if (username == null) {
                return 0.0;
            }
            
            User user = userService.findByUsername(username).orElse(null);
            if (user == null) {
                return 0.0;
            }
            
            return cartService.calculateTotal(user).doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public double getShippingCostFromSession() {
        double subtotal = getSubtotalFromSession();
        return subtotal >= 500 ? 0 : 50;
    }

    public double getTotalFromSession() {
        return getSubtotalFromSession() + getShippingCostFromSession();
    }

    public void clearSessionData() {
        try {
            String username = securityService.getAuthenticatedUsername();
            if (username != null) {
                User user = userService.findByUsername(username).orElse(null);
                if (user != null) {
                    cartService.clearCart(user);
                }
            }
        } catch (Exception e) {
            // Ignore
        }
    }
}
