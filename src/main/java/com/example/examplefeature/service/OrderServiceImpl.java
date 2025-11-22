package com.example.examplefeature.service;

import com.example.examplefeature.entity.Order;
import com.example.examplefeature.entity.OrderItem;
import com.example.examplefeature.entity.User;
import com.example.examplefeature.repository.OrderItemRepository;
import com.example.examplefeature.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    
    public OrderServiceImpl(OrderRepository orderRepository, 
                           OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }
    
    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }
    
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }
    
    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
    
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Only pending orders can be cancelled");
        }
        
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
    
    public long countOrders() {
        return orderRepository.count();
    }
    
    public long countUserOrders(User user) {
        return orderRepository.findByUser(user).size();
    }
}
