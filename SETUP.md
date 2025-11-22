# Shopex Quick Setup Guide

## 🚀 Quick Start (5 Minutes)

### Step 1: Install MySQL
If you don't have MySQL installed:
- **Windows**: Download from [MySQL Downloads](https://dev.mysql.com/downloads/installer/)
- **Mac**: `brew install mysql`
- **Linux**: `sudo apt-get install mysql-server`

### Step 2: Start MySQL
```bash
# Windows - MySQL should start automatically
# Mac
brew services start mysql

# Linux
sudo service mysql start
```

### Step 3: Configure Database Password
Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.password=YOUR_MYSQL_ROOT_PASSWORD
```

If you don't have a MySQL password set, leave it empty:
```properties
spring.datasource.password=
```

### Step 4: Run the Application
```bash
mvn spring-boot:run
```

Or on Windows:
```bash
mvnw.cmd spring-boot:run
```

### Step 5: Access the Application
Open your browser and go to:
```
http://localhost:8080
```

## 🔑 Login Credentials

### Admin Access
- Username: `admin`
- Password: `admin123`
- URL: After login, you'll be redirected to admin dashboard

### User Access
- Username: `user`
- Password: `user123`
- URL: After login, you'll be redirected to home page

## 📋 What Happens on First Run?

1. **Database Creation**: The application automatically creates the `shopex` database
2. **Tables Creation**: All necessary tables are created via Hibernate
3. **Sample Data**: 
   - 2 user accounts (admin and user)
   - 8 sample products with images
4. **Ready to Use**: The application is immediately ready for testing

## 🎯 Quick Test Flow

### As a Customer (User):
1. Login with `user / user123`
2. Browse products on home page
3. Add products to cart
4. Go to cart and update quantities
5. Proceed to checkout
6. Complete order with payment method

### As an Admin:
1. Login with `admin / admin123`
2. View admin dashboard
3. Add new products
4. Update existing products
5. Delete products
6. View orders (future feature)

## ⚠️ Common Issues

### Issue: "Access denied for user 'root'@'localhost'"
**Solution**: Update MySQL password in `application.properties`

### Issue: "Unknown database 'shopex'"
**Solution**: Ensure `createDatabaseIfNotExist=true` is in the datasource URL

### Issue: Port 8080 already in use
**Solution**: Change port in `application.properties`:
```properties
server.port=9090
```

### Issue: Application won't start
**Solutions**:
1. Ensure Java 21+ is installed: `java -version`
2. Ensure Maven is installed: `mvn -version`
3. Ensure MySQL is running
4. Check MySQL credentials

## 🔧 Advanced Configuration

### Using Different Database
To use a different database name:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/my_shop?createDatabaseIfNotExist=true
```

### Using Different Port
```properties
server.port=8081
```

### Disable Auto-Browser Launch
```properties
vaadin.launch-browser=false
```

## 📁 Project Structure Overview

```
src/main/java/com/example/examplefeature/
├── entity/          - Database entities (Product, User, Cart, Order)
├── repository/      - Data access layer
├── service/         - Business logic
├── security/        - Authentication & authorization
├── ui/             - Vaadin views
│   ├── admin/      - Admin panel views
│   ├── user/       - Customer views
│   └── auth/       - Login & signup
└── config/         - Application configuration
```

## 🎓 Next Steps

1. **Explore the Code**: Start with `Application.java`
2. **Check Entities**: Look at `entity/` package to understand data model
3. **Review Services**: See `service/` for business logic
4. **Customize UI**: Modify Vaadin views in `ui/` package
5. **Add Features**: Extend the application with new functionality

## 📚 Learning Resources

- [Vaadin Documentation](https://vaadin.com/docs)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security Guide](https://spring.io/guides/gs/securing-web/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)

## 🐛 Need Help?

- Check the main README.md for detailed documentation
- Review error logs in the console
- Verify MySQL connection and credentials
- Ensure all prerequisites are installed

---

**Happy Coding! 🎉**
