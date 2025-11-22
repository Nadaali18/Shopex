package com.example.examplefeature.service;

import com.example.examplefeature.entity.Cart;
import com.example.examplefeature.entity.CartItem;
import com.example.examplefeature.entity.Product;
import com.example.examplefeature.entity.User;
import com.example.examplefeature.repository.CartItemRepository;
import com.example.examplefeature.repository.CartRepository;
import com.example.examplefeature.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    
    public CartServiceImpl(CartRepository cartRepository, 
                          CartItemRepository cartItemRepository,
                          ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }
    
    public Cart getUserCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart(user);
                    return cartRepository.save(cart);
                });
    }
    
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
    
    public CartItem addItem(User user, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        
        Cart cart = getUserCart(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            int newQuantity = item.getQuantity() + quantity;
            
            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Insufficient stock");
            }
            
            item.setQuantity(newQuantity);
            return cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(product, quantity);
            cart.addItem(newItem);
            return cartItemRepository.save(newItem);
        }
    }
    
    public CartItem updateItemQuantity(Long cartItemId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }
        
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        
        if (item.getProduct().getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        
        item.setQuantity(quantity);
        return cartItemRepository.save(item);
    }
    
    public void removeItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    
    public void clearCart(User user) {
        Cart cart = getUserCart(user);
        cartItemRepository.deleteByCart(cart);
        cart.clearItems();
        cartRepository.save(cart);
    }
    
    public BigDecimal calculateTotal(User user) {
        Cart cart = getUserCart(user);
        return cart.getTotalPrice();
    }
    
    public int getItemCount(User user) {
        Cart cart = getUserCart(user);
        return cart.getTotalItems();
    }
    
    public List<CartItem> getCartItems(User user) {
        Cart cart = getUserCart(user);
        return cart.getItems();
    }
}
