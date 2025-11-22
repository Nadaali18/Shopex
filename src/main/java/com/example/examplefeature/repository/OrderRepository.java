package com.example.examplefeature.repository;

import com.example.examplefeature.entity.Order;
import com.example.examplefeature.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUser(User user);
    
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    List<Order> findByStatus(String status);
    
    List<Order> findByUserAndStatus(User user, String status);
}
