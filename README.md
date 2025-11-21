# Shopex E-commerce Platform

- Shopex is a modern, full-featured e-commerce platform built with Vaadin and Spring Boot
providing seamless shopping experiences for customers and powerful management tools for
administrators.

# 🚀 Features

## 👥 Customer Features
- Home Page - Product showcases with image slider and categories
- Product Browsing - Organized by categories with best products section
- Shopping Cart - Add, update, and manage cart items
- Checkout Process - Multiple payment methods (Cash, E-Wallet, Credit Card)
- Order Management - Track and manage orders
- About Us - Company information and team details

## 👨‍💼 Admin Features
- Dashboard - Overview of store management
- Product Management - Add, update, and delete products
- Inventory Control - Stock management and tracking
- Order Processing - Manage customer orders

# Project Structure

The sources of  Website have the following structure:

```
src/main/java/com/example/examplefeature/
├── ui/
│   ├── user/                          
│   │   ├── Home.java                  
│   │   ├── CartView.java              
│   │   ├── CheckoutView.java          
│   │   └── AboutUsView.java           
│   ├── admin/                         
│   │   ├── AdminHomeView.java         
│   │   ├── add_product/
│   │   │   └── view/
│   │   │       └── AddProductView.java
│   │   ├── delete_product/
│   │   │   └── view/
│   │   │       └── DeleteProductView.java
│   │   └── update_product/
│   │       └── view/
│   │           └── UpdateProductView.java
│   └── components/                    
│       ├── user/
│       │   ├── HomeImageSlider.java   
│       │   ├── CategoriesSection.java 
│       │   └── BestProductsSection.java
│       ├── admin/
│       │   ├── ProductForm.java       
│       │   ├── ProductGrid.java       
│       │   └── AdminActionBox.java    
│       └── checkout/
│           ├── PaymentSection.java
│           ├── OrderSummary.java
│           └── PaymentMethodFactory.java
├── model/                             
│   ├── ProductData.java              
│   └── Product.java                  
├── service/                          
│   ├── ProductService.java           
│   ├── CartService.java              
│   └── CheckoutService.java          
└── layout/                           
    ├── CommonHeader.java             
    └── AppFooter.java                                
```
# 📦 Installation & Setup

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Development Mode

- Clone and import the project into your IDE
- Start the application in development mode:

```bash
spring-boot:run
```

