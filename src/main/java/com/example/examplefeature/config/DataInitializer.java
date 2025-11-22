package com.example.examplefeature.config;

import com.example.examplefeature.entity.Product;
import com.example.examplefeature.entity.User;
import com.example.examplefeature.repository.ProductRepository;
import com.example.examplefeature.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    
    public DataInitializer(UserRepository userRepository, 
                          ProductRepository productRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
        initializeProducts();
    }
    
    private void initializeUsers() {
        // Check if admin already exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User("admin", passwordEncoder.encode("admin123"), "admin@shopex.com");
            admin.addRole("ADMIN");
            userRepository.save(admin);
            System.out.println("✓ Admin user created - Username: admin, Password: admin123");
        }
        
        // Create test user
        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User("user", passwordEncoder.encode("user123"), "user@shopex.com");
            userRepository.save(user);
            System.out.println("✓ Test user created - Username: user, Password: user123");
        }
    }
    
    private void initializeProducts() {
        if (productRepository.count() == 0) {
            // Clothes
            productRepository.save(new Product(
                "Sweatshirt",
                new BigDecimal("300"),
                "Clothes",
                50,
                "sweatshirt.jpg",
                "Comfortable and stylish sweatshirt for everyday wear"
            ));
            
            productRepository.save(new Product(
                "Men's Shirt",
                new BigDecimal("250"),
                "Clothes",
                40,
                "menshirt.jpg",
                "Classic men's shirt perfect for office or casual occasions"
            ));
            
            productRepository.save(new Product(
                "Abaya",
                new BigDecimal("400"),
                "Clothes",
                30,
                "abaya.jpg",
                "Elegant traditional abaya with modern design"
            ));
            
            // Shoes
            productRepository.save(new Product(
                "Sneakers",
                new BigDecimal("150"),
                "Shoes",
                60,
                "sneakers.jpg",
                "Comfortable sports sneakers for active lifestyle"
            ));
            
            productRepository.save(new Product(
                "Converse Shoes",
                new BigDecimal("180"),
                "Shoes",
                45,
                "converse.jpg",
                "Classic Converse shoes for casual style"
            ));
            
            // Electronics
            productRepository.save(new Product(
                "Smart Watch",
                new BigDecimal("500"),
                "Electronics",
                25,
                "watch.jpg",
                "Latest smartwatch with health tracking features"
            ));
            
            // Accessories
            productRepository.save(new Product(
                "Tote Bag",
                new BigDecimal("120"),
                "Accessories",
                70,
                "totebag.jpg",
                "Spacious and durable tote bag for daily use"
            ));
            
            productRepository.save(new Product(
                "Coco Chanel Perfume",
                new BigDecimal("350"),
                "Accessories",
                35,
                "cocochanel.jpg",
                "Luxury fragrance for elegant style"
            ));
            
            System.out.println("✓ Sample products created");
        }
    }
}
