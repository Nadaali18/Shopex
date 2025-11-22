# Shopex E-commerce Platform

Shopex is a modern, full-stack e-commerce platform built with **Vaadin**, **Spring Boot**, **MySQL**, and **Spring Security** providing seamless shopping experiences for customers and powerful management tools for administrators.

## 🚀 Features

### 👥 Customer Features
- **User Authentication** - Secure login and registration
- **Home Page** - Product showcases with image slider and categories
- **Product Browsing** - Browse products by categories with real-time stock information
- **Shopping Cart** - Add, update, and manage cart items (database-backed)
- **Checkout Process** - Multiple payment methods (Cash on Delivery, E-Wallet, Credit Card)
- **Order Management** - Track and manage personal orders
- **About Us** - Company information and team details

### 👨‍💼 Admin Features
- **Admin Dashboard** - Overview of store management
- **Product Management** - Add, update, and delete products with image upload
- **Inventory Control** - Real-time stock management and tracking
- **Order Processing** - View and manage all customer orders
- **User Management** - Admin access control

## 🏗️ Technology Stack

### Backend
- **Spring Boot 3.5.7** - Application framework
- **Spring Data JPA** - Database persistence
- **Spring Security** - Authentication and authorization
- **MySQL 8** - Relational database
- **Hibernate** - ORM framework

### Frontend
- **Vaadin 24.9.5** - Java-based UI framework
- **Responsive Design** - Mobile-friendly interface

### Security
- **BCrypt Password Encoding** - Secure password storage
- **Form-based Authentication** - Session-based login
- **Role-based Access Control** - USER and ADMIN roles

## 📁 Backend Architecture

### Entity Layer
```
entity/
├── User.java          - User accounts with roles
├── Product.java       - Product catalog
├── Cart.java          - Shopping cart container
├── CartItem.java      - Individual cart items
├── Order.java         - Customer orders
└── OrderItem.java     - Order line items
```

### Repository Layer
```
repository/
├── UserRepository.java
├── ProductRepository.java
├── CartRepository.java
├── CartItemRepository.java
├── OrderRepository.java
└── OrderItemRepository.java
```

### Service Layer
```
service/
├── UserServiceImpl.java       - User management & authentication
├── ProductServiceImpl.java    - Product CRUD operations
├── CartServiceImpl.java       - Shopping cart logic
├── CheckoutServiceImpl.java   - Order creation & checkout
└── OrderServiceImpl.java      - Order management
```

### Security Layer
```
security/
├── SecurityConfiguration.java      - Spring Security config
├── CustomUserDetailsService.java   - User authentication
└── SecurityService.java            - Security utilities
```

## 📦 Installation & Setup

### Prerequisites
- **Java 21** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Database Setup

1. **Install MySQL** (if not already installed)

2. **Create Database** (Optional - auto-created by application)
```sql
CREATE DATABASE shopex;
```

3. **Configure Database Connection**

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopex?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

⚠️ **Important**: Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL root password.

### Running the Application

#### Option 1: Using Maven
```bash
mvn spring-boot:run
```

#### Option 2: Using Maven Wrapper
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### Option 3: Using IDE
- Import project as Maven project
- Run `Application.java` main class

### Access the Application

Once started, open your browser and navigate to:
```
http://localhost:8080
```

## 👤 Default Credentials

The application comes with pre-configured test accounts:

### Admin Account
- **Username**: `admin`
- **Password**: `admin123`
- **Access**: Full admin panel access

### User Account
- **Username**: `user`
- **Password**: `user123`
- **Access**: Customer shopping features

## 📊 Database Schema

### Key Tables
- `users` - User accounts and credentials
- `user_roles` - User role assignments
- `products` - Product catalog
- `carts` - Shopping carts
- `cart_items` - Cart contents
- `orders` - Customer orders
- `order_items` - Order details

### Relationships
- User ↔ Cart (One-to-One)
- Cart ↔ CartItems (One-to-Many)
- User ↔ Orders (One-to-Many)
- Order ↔ OrderItems (One-to-Many)
- Product ← CartItems/OrderItems (Many-to-One)

## 🔐 Security Features

- **Password Encryption**: BCrypt hashing
- **Session Management**: Spring Security sessions
- **CSRF Protection**: Enabled by default
- **Role-based Access**: 
  - `/admin-*` routes require ADMIN role
  - `/cart`, `/checkout` require authentication
  - `/login`, `/signup` are public

## 🛠️ Development Features

### Auto-populated Sample Data
The application automatically initializes with:
- 8 sample products across different categories
- Admin and user test accounts
- Categories: Clothes, Shoes, Electronics, Accessories

### Hot Reload
Vaadin supports hot reload in development mode for rapid development.

## 📝 API Endpoints

### Authentication
- `GET /login` - Login page
- `POST /login` - Process login
- `GET /signup` - Registration page
- `GET /logout` - Logout

### User Routes
- `GET /home` - Product catalog
- `GET /cart` - Shopping cart
- `GET /checkout` - Checkout page
- `GET /about-us` - About page

### Admin Routes (Requires ADMIN role)
- `GET /admin-home` - Admin dashboard
- `GET /add-product` - Add new product
- `GET /update-product` - Update products
- `GET /delete-product` - Delete products

## 🚀 Production Deployment

### Build for Production
```bash
mvn clean package -Pproduction
```

### Run Production Build
```bash
java -jar target/app-1.0-SNAPSHOT.jar
```

### Environment Variables
```bash
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://your-db-host:3306/shopex
SPRING_DATASOURCE_USERNAME=your_username
SPRING_DATASOURCE_PASSWORD=your_password

# Server
SERVER_PORT=8080
```

## 🐳 Docker Support

```bash
# Build image
docker build -t shopex .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/shopex \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  shopex
```

## 🧪 Testing

The project includes:
- Spring Boot Test framework
- Spring Security Test support

Run tests:
```bash
mvn test
```

## 📚 Additional Configuration

### Change Default Port
In `application.properties`:
```properties
server.port=9090
```

### Enable SQL Logging
```properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Production Database Settings
```properties
spring.jpa.hibernate.ddl-auto=validate
# Use Flyway or Liquibase for schema migrations
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

See LICENSE.md file for details.

## 🆘 Troubleshooting

### MySQL Connection Issues
- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure database exists or `createDatabaseIfNotExist=true` is set

### Port Already in Use
- Change port in `application.properties`
- Or stop the process using port 8080

### Login Not Working
- Verify default credentials (admin/admin123 or user/user123)
- Check browser console for errors
- Ensure Spring Security is properly configured

## 📞 Support

For issues and questions, please open an issue on the GitHub repository.

---

**Built with ❤️ using Vaadin and Spring Boot**

```
Shopex/
├── src/
│   └── main/
│       ├── frontend/
│       │   ├── index.html
│       │   └── themes/
│       │       └── default/
│       │           ├── styles.css
│       │           └── theme.json
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           ├── Application.java
│       │           └── examplefeature/
│       │               ├── package-info.java
│       │               ├── model/
│       │               │   ├── Product.java
│       │               │   └── ProductData.java
│       │               ├── services/
│       │               │   ├── AdminProductService.java
│       │               │   ├── CartService.java
│       │               │   ├── CheckoutService.java
│       │               │   ├── HomeCartService.java
│       │               │   └── ProductService.java
│       │               └── ui/
│       │                   ├── HomeRedirect.java
│       │                   ├── admin/
│       │                   │   ├── home/
│       │                   │   │   ├── layout/
│       │                   │   │   │   ├── AdminActionBox.java
│       │                   │   │   │   ├── AdminActionsSection.java
│       │                   │   │   │   └── AdminHeroSection.java
│       │                   │   │   └── view/
│       │                   │   │       └── AdminHomeView.java
│       │                   │   ├── add_product/
│       │                   │   │   ├── layout/
│       │                   │   │   │   ├── ProductForm.java
│       │                   │   │   │   └── ProductImageSection.java
│       │                   │   │   └── view/
│       │                   │   │       └── AddProductView.java
│       │                   │   ├── delete_product/
│       │                   │   │   ├── layout/
│       │                   │   │   │   ├── DeleteButton.java
│       │                   │   │   │   └── ProductGrid.java
│       │                   │   │   └── view/
│       │                   │   │       └── DeleteProductView.java
│       │                   │   └── update_product/
│       │                   │       ├── layout/
│       │                   │       │   ├── ProductSelectionGrid.java
│       │                   │       │   └── ProductUpdateForm.java
│       │                   │       └── view/
│       │                   │           └── UpdateProductView.java
│       │                   ├── auth/
│       │                   │   ├── login/
│       │                   │   │   └── LoginView.java
│       │                   │   └── signup/
│       │                   │       └── SignupView.java
│       │                   ├── layout/
│       │                   │   ├── AppFooter.java
│       │                   │   └── AppHeader.java
│       │                   └── user/
│       │                       ├── home/
│       │                       │   ├── layout/
│       │                       │   │   ├── BestProductsSection.java
│       │                       │   │   ├── HomeCategoriesBar.java
│       │                       │   │   ├── HomeImageSlider.java
│       │                       │   │   └── ProductCard.java
│       │                       │   └── view/
│       │                       │       └── Home.java
│       │                       ├── cart/
│       │                       │   ├── layout/
│       │                       │   │   ├── CartContainer.java
│       │                       │   │   ├── CartItem.java
│       │                       │   │   └── CheckoutBox.java
│       │                       │   └── view/
│       │                       │       └── CartView.java
│       │                       ├── check_out/
│       │                       │   ├── layout/
│       │                       │   │   ├── CashOnDeliveryPayment.java
│       │                       │   │   ├── CreditCardPayment.java
│       │                       │   │   ├── EWalletPayment.java
│       │                       │   │   ├── OrderSummary.java
│       │                       │   │   ├── PaymentMethodFactory.java
│       │                       │   │   ├── PaymentSection.java
│       │                       │   │   └── ScreenshotUpload.java
│       │                       │   └── view/
│       │                       │       └── CheckoutView.java
│       │                       └── about_us/
│       │                           ├── layout/
│       │                           │   ├── AboutHeader.java
│       │                           │   ├── AboutTeamSection.java
│       │                           │   ├── InfoBox.java
│       │                           │   └── MissionSection.java
│       │                           └── view/
│       │                               └── AboutUsView.java
│       └── resources/
│           ├── application.properties
│           └── META-INF/
│               └── resources/
│                   └── images/
│                       ├── abaya.jpg
│                       ├── add.jpg
│                       ├── admin.jpg
│                       ├── bg.jpg
│                       ├── bg1.jpg
│                       ├── bg2.jpg
│                       ├── bg3.jpg
│                       ├── bg4.jpg
│                       ├── cocochanel.jpg
│                       ├── converse.jpg
│                       ├── delete.jpg
│                       ├── menshirt.jpg
│                       ├── slide1.jpg
│                       ├── slide2.jpg
│                       ├── slide3.jpg
│                       ├── sneakers.jpg
│                       ├── sweatshirt.jpg
│                       ├── totebag.jpg
│                       ├── update.jpg
│                       ├── watch.jpg
│                       ├── white_logo.jpg
│                       └── white_logo.png
├── Dockerfile
├── LICENSE.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
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
