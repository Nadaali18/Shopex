package com.example.examplefeature.service;

import com.example.examplefeature.entity.*;
import com.example.examplefeature.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CheckoutServiceImpl {
    
    private final CartServiceImpl cartService;
    private final OrderRepository orderRepository;
    private final ProductServiceImpl productService;
    
    public CheckoutServiceImpl(CartServiceImpl cartService, 
                              OrderRepository orderRepository,
                              ProductServiceImpl productService) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }
    
    public Order createOrderFromCart(User user, String paymentMethod, String shippingAddress, String phoneNumber) {
        Cart cart = cartService.getUserCart(user);
        
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        
        // Validate stock availability
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getTitle());
            }
        }
        
        // Create order
        BigDecimal totalPrice = cart.getTotalPrice();
        Order order = new Order(user, totalPrice, paymentMethod);
        order.setShippingAddress(shippingAddress);
        order.setPhoneNumber(phoneNumber);
        
        // Convert cart items to order items
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(
                cartItem.getProduct(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice()
            );
            order.addItem(orderItem);
            
            // Decrease product stock
            productService.decreaseStock(cartItem.getProduct().getId(), cartItem.getQuantity());
        }
        
        // Save order
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart
        cartService.clearCart(user);
        
        return savedOrder;
    }
    
    public BigDecimal calculateTotal(User user) {
        return cartService.calculateTotal(user);
    }
    
    public BigDecimal calculateTax(BigDecimal subtotal) {
        // 15% tax
        return subtotal.multiply(BigDecimal.valueOf(0.15));
    }
    
    public BigDecimal calculateShipping(BigDecimal subtotal) {
        // Free shipping for orders over 500
        if (subtotal.compareTo(BigDecimal.valueOf(500)) >= 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(50);
    }
    
    public BigDecimal calculateGrandTotal(User user) {
        BigDecimal subtotal = calculateTotal(user);
        BigDecimal tax = calculateTax(subtotal);
        BigDecimal shipping = calculateShipping(subtotal);
        return subtotal.add(tax).add(shipping);
    }
}
