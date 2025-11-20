package com.example.examplefeature.ui.user;

import com.example.examplefeature.model.ProductData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("home")
public class Home extends VerticalLayout {

    public Home() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // ===================== HEADER =====================
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.getStyle().set("background-color", "#3f0d50ff");
        header.getStyle().set("color", "white");
        header.setPadding(true);
        header.setSpacing(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        // Logo + site name
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("/images/white_logo.png", "Shop Wheel");
        logo.setHeight("40px");
        H1 siteName = new H1("Shopex");
        siteName.getStyle().set("margin", "0");
        siteName.getStyle().set("color", "pink");
        logoLayout.add(logo, siteName);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Navigation menu بالمنتصف
        HorizontalLayout navMenu = new HorizontalLayout();
        navMenu.setSpacing(true);
        Anchor homeLink = new Anchor("home", "Home");
        Anchor cartLink = new Anchor("cart", "Cart");
        Anchor aboutLink = new Anchor("aboutUs", "About Us");
        homeLink.getStyle().set("color", "pink").set("font-weight", "bold");
        cartLink.getStyle().set("color", "white");
        aboutLink.getStyle().set("color", "white");
        homeLink.getStyle().set("margin-right", "40px");
        cartLink.getStyle().set("margin-right", "40px");
        navMenu.add(homeLink, cartLink, aboutLink);
        navMenu.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        navMenu.setAlignItems(FlexComponent.Alignment.CENTER);

        // Right icons
        HorizontalLayout rightIcons = new HorizontalLayout();

        // Cart Icon
        Icon cartIcon = VaadinIcon.CART.create();
        cartIcon.getStyle()
            .set("color", "pink")
            .set("cursor", "pointer")
            .set("transition", "color 0.3s");

        cartIcon.getElement().addEventListener("mouseenter", e -> {
            cartIcon.getStyle().set("color", "#ff66cc");
        });
        cartIcon.getElement().addEventListener("mouseleave", e -> {
            cartIcon.getStyle().set("color", "pink");
        });

        // Logout Icon
        Icon logoutIcon = VaadinIcon.SIGN_OUT.create();
        logoutIcon.getStyle()
            .set("color", "pink")
            .set("cursor", "pointer")
            .set("transition", "color 0.3s");

        logoutIcon.getElement().addEventListener("mouseenter", e -> {
            logoutIcon.getStyle().set("color", "#ff66cc");
        });
        logoutIcon.getElement().addEventListener("mouseleave", e -> {
            logoutIcon.getStyle().set("color", "pink");
        });

        logoutIcon.addClickListener(e -> {
            UI.getCurrent().navigate("login");
        });

        cartIcon.addClickListener(e -> {
            UI.getCurrent().navigate("cart");
        });

        rightIcons.add(cartIcon, logoutIcon);
        rightIcons.setSpacing(true);

        header.add(logoLayout, navMenu, rightIcons);
        header.expand(navMenu);
        add(header);

        // ===================== IMAGE SLIDER =====================
        Div sliderContainer = new Div();
        sliderContainer.setWidth("1200px");
        sliderContainer.getStyle().set("min-height","400px");
        sliderContainer.getStyle().set("overflow", "hidden");
        sliderContainer.getStyle().set("position", "relative");
        sliderContainer.getStyle().set("margin", "30px auto");

        String[] sliderImages = {"/images/slide1.jpg", "/images/slide2.jpg", "/images/slide3.jpg"};
        Image sliderImage = new Image(sliderImages[0], "Slide 1");
        sliderImage.setWidthFull();
        sliderImage.setHeightFull();
        sliderImage.getStyle().set("object-fit", "fill");

        sliderContainer.add(sliderImage);

        // ---------- إنشاء الأزرار ----------
        Button leftButton = new Button("<");
        Button rightButton = new Button(">");

        // تصميم أزرار التنقل
        leftButton.getStyle()
                .set("position", "absolute")
                .set("top", "50%")
                .set("left", "10px")
                .set("transform", "translateY(-50%)")
                .set("z-index", "10")
                .set("background-color", "#3f0d50a4")
                .set("color", "white")
                .set("border", "none")
                .set("border-radius", "50%")
                .set("width", "40px")
                .set("height", "40px")
                .set("cursor", "pointer");

        rightButton.getStyle()
                .set("position", "absolute")
                .set("top", "50%")
                .set("right", "10px")
                .set("transform", "translateY(-50%)")
                .set("z-index", "10")
                .set("background-color", "#3f0d50a4")
                .set("color", "white")
                .set("border", "none")
                .set("border-radius", "50%")
                .set("width", "40px")
                .set("height", "40px")
                .set("cursor", "pointer");

        sliderContainer.add(leftButton, rightButton);

        // ---------- التحكم في التنقل ----------
        final int[] currentIndex = {0};

        leftButton.addClickListener(e -> {
            currentIndex[0] = (currentIndex[0] - 1 + sliderImages.length) % sliderImages.length;
            sliderImage.setSrc(sliderImages[currentIndex[0]]);
        });

        rightButton.addClickListener(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % sliderImages.length;
            sliderImage.setSrc(sliderImages[currentIndex[0]]);
        });

        // ---------- التنقل التلقائي ----------
        UI.getCurrent().access(() -> {
            new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(5000); // الانتظار 5 ثواني
                        UI.getCurrent().access(() -> {
                            currentIndex[0] = (currentIndex[0] + 1) % sliderImages.length;
                            sliderImage.setSrc(sliderImages[currentIndex[0]]);
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        add(sliderContainer);

        // ===================== CATEGORIES =====================
        H3 categoriesTitle = new H3("Categories");
        categoriesTitle.getStyle()
            .set("margin", "30px 0 30px 40px")
            .set("color", "#3f0d50ff")
            .set("font-size", "24px");
        add(categoriesTitle);

        Div categoriesContainer = new Div();
        categoriesContainer.setWidthFull();
        categoriesContainer.getStyle()
            .set("overflow-x", "auto")
            .set("overflow-y", "hidden")
            .set("white-space", "nowrap")
            .set("padding", "15px")
            .set("background", "#ffffffff")
            .set("border-radius", "10px")
            .set("margin", "0 10px 10px 40px")
            .set("min-height", "80px");

        // فئات متنوعة
        String[] categories = {"Shoes", "Clothes", "Accessories", "Bags", "Perfumes", "Electronics", "Home", "Beauty"};
        
        for (String category : categories) {
            Button categoryButton = new Button(category);
            categoryButton.getStyle()
                .set("display", "inline-block")
                .set("width", "200px")
                .set("height", "60px")
                .set("background-color", "white")
                .set("border", "2px solid #3f0d5076")
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("color", "#3f0d50ff")
                .set("cursor", "pointer")
                .set("margin-right", "15px")
                .set("font-size", "14px");
            
            categoryButton.addClickListener(e -> {
                System.out.println("تم اختيار فئة: " + category);
            });
            
            categoriesContainer.add(categoryButton);
        }

        add(categoriesContainer);

        // ===================== BEST PRODUCTS =====================
        H3 bestTitle = new H3("Best Products");
        bestTitle.getStyle()
            .set("margin", "30px 0 30px 40px")
            .set("color", "#3f0d50ff")
            .set("font-size", "24px");
        add(bestTitle);

        VerticalLayout productsContainer = new VerticalLayout();
        productsContainer.setWidthFull();
        productsContainer.setPadding(false);
        productsContainer.setSpacing(true);
        productsContainer.getStyle().set("margin", "0 10px");

        String[] productNames = {"Tote Bag", "Abaya", "Converse", "SweatShirt", "Coco Chanel", "Men Shirt", "Sneakers", "Watch"};
        String[] categoryStrings = {"Bags", "Clothes", "Shoes", "Clothes", "Perfumes", "Clothes", "Shoes", "Accessories"};
        String[] prices = {"$120", "$800", "$150", "$600", "$250", "$300", "$200", "$500"};
        String[] images = {
            "/images/totebag.jpg",
            "/images/abaya.jpg",
            "/images/converse.jpg",
            "/images/sweatshirt.jpg",
            "/images/cocochanel.jpg",
            "/images/menshirt.jpg",
            "/images/sneakers.jpg",
            "/images/watch.jpg"
        };

        // ترتيب المنتجات في صفوف كل صف يحتوي على 4 منتجات
        for (int i = 0; i < productNames.length; i += 4) {
            HorizontalLayout productRow = new HorizontalLayout();
            productRow.setWidthFull();
            productRow.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            productRow.getStyle()
                .set("gap", "15px")
                .set("margin-bottom", "20px")
                .set("flex-wrap", "wrap");

            // إضافة 4 منتجات في كل صف
            for (int j = i; j < Math.min(i + 4, productNames.length); j++) {
                VerticalLayout card = createProductCard(
                    productNames[j], 
                    categoryStrings[j], 
                    prices[j], 
                    images[j]
                );
                productRow.add(card);
            }

            // إضافة عناصر فارغة إذا كان عدد المنتجات أقل من 4 في الصف الأخير
            int remainingProducts = 4 - (Math.min(i + 4, productNames.length) - i);
            for (int k = 0; k < remainingProducts; k++) {
                Div emptyDiv = new Div();
                emptyDiv.setWidth("300px");
                emptyDiv.getStyle().set("visibility", "hidden");
                productRow.add(emptyDiv);
            }

            productsContainer.add(productRow);
        }

        add(productsContainer);

        // ===================== FOOTER =====================
        VerticalLayout footer = new VerticalLayout();
        footer.setWidthFull();
        footer.setPadding(true);
        footer.getStyle()
                .set("background-color", "#2a0a3f")
                .set("color", "white")
                .set("text-align", "center")
                .set("margin-top", "50px");

        H3 shopName = new H3("Shopex");
        shopName.getStyle().set("color", "pink").set("margin", "0");

        Paragraph copyright = new Paragraph("© 2024 Shopex. All rights reserved.");
        copyright.getStyle().set("color", "#ccc").set("font-size", "14px");

        Paragraph contact = new Paragraph("Contact us: info@shopex.com | +20 100 123 4567");
        contact.getStyle().set("color", "#ccc").set("font-size", "14px");

        footer.add(shopName, copyright, contact);
        footer.setAlignItems(Alignment.CENTER);
        add(footer);
    }

    // دالة مساعدة لإنشاء كارد المنتج
    private VerticalLayout createProductCard(String name, String category, String price, String imagePath) {
        VerticalLayout card = new VerticalLayout();
        card.getStyle()
            .set("border", "1px solid #e0e0e0")
            .set("border-radius", "12px")
            .set("width", "280px")
            .set("min-width", "280px")
            .set("padding", "15px")
            .set("background", "white")
            .set("box-shadow", "0 4px 8px rgba(0,0,0,0.1)")
            .set("transition", "transform 0.3s, box-shadow 0.3s");

        // تأثير عند المرور بالفأرة
        card.getElement().addEventListener("mouseenter", e -> {
            card.getStyle()
                .set("transform", "translateY(-5px)")
                .set("box-shadow", "0 8px 16px rgba(0,0,0,0.2)");
        });
        
        card.getElement().addEventListener("mouseleave", e -> {
            card.getStyle()
                .set("transform", "translateY(0)")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.1)");
        });

        Image productImage = new Image(imagePath, name);
        productImage.setWidthFull();
        productImage.setHeight("180px");
        productImage.getStyle()
            .set("object-fit", "fill")
            .set("border-radius", "8px");

        H4 productName = new H4(name);
        productName.getStyle()
            .set("margin", "10px 0 5px 0")
            .set("font-size", "16px")
            .set("color", "#333");

        Span categoryName = new Span(category);
        categoryName.getStyle()
            .set("color", "#666")
            .set("font-size", "14px");

        Span priceSpan = new Span(price);
        priceSpan.getStyle()
            .set("font-weight", "bold")
            .set("color", "#3f0d50ff")
            .set("font-size", "18px");

        HorizontalLayout infoLayout = new HorizontalLayout(categoryName, priceSpan);
        infoLayout.setWidthFull();
        infoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        infoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        Button addToCart = new Button("Add to Cart", VaadinIcon.CART.create());
        addToCart.setWidthFull();
        addToCart.getStyle()
            .set("background-color", "#3f0d50ff")
            .set("color", "white")
            .set("border", "none")
            .set("border-radius", "6px")
            .set("padding", "12px")
            .set("cursor", "pointer")
            .set("font-weight", "bold")
            .set("margin-top", "10px");

        // إضافة حدث الزر الجديد
        addToCart.addClickListener(e -> {
            addProductToCart(name, category, price, imagePath);
        });

        card.add(productImage, productName, infoLayout, addToCart);
        return card;
    }

    // دالة جديدة لإضافة المنتج إلى الكارت
    private void addProductToCart(String name, String category, String price, String imagePath) {
        // الحصول على قائمة المنتجات الحالية من Session
        @SuppressWarnings("unchecked")
        List<ProductData> cartItems = (List<ProductData>) UI.getCurrent().getSession()
                .getAttribute("cartItems");
        
        // إذا لم تكن موجودة، إنشاء قائمة جديدة
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        
        // التحقق إذا كان المنتج موجود مسبقاً
        boolean productExists = false;
        for (ProductData item : cartItems) {
            if (item.getName().equals(name)) {
                // زيادة الكمية إذا المنتج موجود
                item.setQuantity(item.getQuantity() + 1);
                productExists = true;
                break;
            }
        }
        
        // إذا المنتج غير موجود، إضافته جديد
        if (!productExists) {
            ProductData newProduct = new ProductData(name, category, price, imagePath);
            cartItems.add(newProduct);
        }
        
        // حفظ القائمة المحدثة في Session
        UI.getCurrent().getSession().setAttribute("cartItems", cartItems);
        
        // حساب الـ subtotal
        double subtotal = cartItems.stream()
                .mapToDouble(ProductData::getTotal)
                .sum();
        UI.getCurrent().getSession().setAttribute("subtotal", subtotal);
        
        // إشعار للمستخدم
        Notification.show("✓ " + name + " added to cart!", 2000, Notification.Position.BOTTOM_START);
        
        System.out.println("Product added to cart: " + name);
        System.out.println("Total items in cart: " + cartItems.size());
    }
}