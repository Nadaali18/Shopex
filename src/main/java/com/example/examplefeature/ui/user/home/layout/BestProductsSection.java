package com.example.examplefeature.ui.user.home.layout;

import com.example.examplefeature.model.ProductData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.util.ArrayList;
import java.util.List;

public class BestProductsSection extends VerticalLayout {
    
    private static final String PRIMARY_COLOR = "#3f0d50ff";
    private static final int PRODUCTS_PER_ROW = 4;
    
    // بيانات المنتجات - ممكن تنقل لداتابيز بعدين
    private static final String[] PRODUCT_NAMES = {
        "Tote Bag", "Abaya", "Converse", "SweatShirt", 
        "Coco Chanel", "Men Shirt", "Sneakers", "Watch"
    };
    
    private static final String[] PRODUCT_CATEGORIES = {
        "Bags", "Clothes", "Shoes", "Clothes", 
        "Perfumes", "Clothes", "Shoes", "Accessories"
    };
    
    private static final String[] PRODUCT_PRICES = {
        "$120", "$800", "$150", "$600", 
        "$250", "$300", "$200", "$500"
    };
    
    private static final String[] PRODUCT_IMAGES = {
        "/images/totebag.jpg", "/images/abaya.jpg", "/images/converse.jpg",
        "/images/sweatshirt.jpg", "/images/cocochanel.jpg", "/images/menshirt.jpg",
        "/images/sneakers.jpg", "/images/watch.jpg"
    };

    public BestProductsSection() {
        createProductsSection();
    }
    
    private void createProductsSection() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        H3 bestTitle = new H3("Best Products");
        bestTitle.getStyle()
            .set("margin", "30px 0 30px 40px")
            .set("color", PRIMARY_COLOR)
            .set("font-size", "24px");
        add(bestTitle);

        VerticalLayout productsContainer = new VerticalLayout();
        productsContainer.setWidthFull();
        productsContainer.setPadding(false);
        productsContainer.setSpacing(true);
        productsContainer.getStyle().set("margin", "0 10px");

        for (int i = 0; i < PRODUCT_NAMES.length; i += PRODUCTS_PER_ROW) {
            HorizontalLayout productRow = createProductRow(i);
            productsContainer.add(productRow);
        }

        add(productsContainer);
    }
    
    private HorizontalLayout createProductRow(int startIndex) {
        HorizontalLayout productRow = new HorizontalLayout();
        productRow.setWidthFull();
        productRow.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        productRow.getStyle()
            .set("gap", "15px")
            .set("margin-bottom", "20px")
            .set("flex-wrap", "wrap");

        for (int j = startIndex; j < Math.min(startIndex + PRODUCTS_PER_ROW, PRODUCT_NAMES.length); j++) {
            VerticalLayout card = createProductCard(
                PRODUCT_NAMES[j], 
                PRODUCT_CATEGORIES[j], 
                PRODUCT_PRICES[j], 
                PRODUCT_IMAGES[j]
            );
            productRow.add(card);
        }

        addEmptyPlaceholders(productRow, startIndex);

        return productRow;
    }
    
    private void addEmptyPlaceholders(HorizontalLayout row, int startIndex) {
        int remainingProducts = PRODUCTS_PER_ROW - 
            (Math.min(startIndex + PRODUCTS_PER_ROW, PRODUCT_NAMES.length) - startIndex);
             
        for (int k = 0; k < remainingProducts; k++) {
            Div emptyDiv = new Div();
            emptyDiv.setWidth("280px");
            emptyDiv.getStyle().set("visibility", "hidden");
            row.add(emptyDiv);
        }
    }
    
    private VerticalLayout createProductCard(String name, String category, String price, String imagePath) {
        VerticalLayout card = new VerticalLayout();
        applyCardStyle(card);

        Image productImage = createProductImage(imagePath, name);
        H4 productName = createProductName(name);
        HorizontalLayout infoLayout = createProductInfo(category, price);
        Button addToCart = createAddToCartButton(name, category, price, imagePath);

        card.add(productImage, productName, infoLayout, addToCart);
        return card;
    }
    
    private void applyCardStyle(VerticalLayout card) {
        card.getStyle()
            .set("border", "1px solid #e0e0e0")
            .set("border-radius", "12px")
            .set("width", "280px")
            .set("min-width", "280px")
            .set("padding", "15px")
            .set("background", "white")
            .set("box-shadow", "0 4px 8px rgba(0,0,0,0.1)")
            .set("transition", "transform 0.3s, box-shadow 0.3s");

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
    }
    
    private Image createProductImage(String imagePath, String altText) {
        Image image = new Image(imagePath, altText);
        image.setWidthFull();
        image.setHeight("180px");
        image.getStyle()
            .set("object-fit", "fill")
            .set("border-radius", "8px");
        return image;
    }
    
    private H4 createProductName(String name) {
        H4 productName = new H4(name);
        productName.getStyle()
            .set("margin", "10px 0 5px 0")
            .set("font-size", "16px")
            .set("color", "#333");
        return productName;
    }
    
    private HorizontalLayout createProductInfo(String category, String price) {
        Span categoryName = new Span(category);
        categoryName.getStyle()
            .set("color", "#666")
            .set("font-size", "14px");

        Span priceSpan = new Span(price);
        priceSpan.getStyle()
            .set("font-weight", "bold")
            .set("color", PRIMARY_COLOR)
            .set("font-size", "18px");

        HorizontalLayout infoLayout = new HorizontalLayout(categoryName, priceSpan);
        infoLayout.setWidthFull();
        infoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        infoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        
        return infoLayout;
    }
    
    private Button createAddToCartButton(String name, String category, String price, String imagePath) {
        Button button = new Button("Add to Cart", VaadinIcon.CART.create());
        button.setWidthFull();
        button.getStyle()
            .set("background-color", PRIMARY_COLOR)
            .set("color", "white")
            .set("border", "none")
            .set("border-radius", "6px")
            .set("padding", "12px")
            .set("cursor", "pointer")
            .set("font-weight", "bold")
            .set("margin-top", "10px");

        button.addClickListener(e -> addProductToCart(name, category, price, imagePath));
        return button;
    }
    
    // خدمة إضافة المنتج للسلة
    private void addProductToCart(String name, String category, String price, String imagePath) {
        @SuppressWarnings("unchecked")
        List<ProductData> cartItems = (List<ProductData>) UI.getCurrent().getSession()
                .getAttribute("cartItems");
        
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        
        boolean productExists = false;
        for (ProductData item : cartItems) {
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() + 1);
                productExists = true;
                break;
            }
        }
        
        if (!productExists) {
            ProductData newProduct = new ProductData(name, category, price, imagePath);
            cartItems.add(newProduct);
        }
        
        UI.getCurrent().getSession().setAttribute("cartItems", cartItems);
        
        double subtotal = cartItems.stream()
                .mapToDouble(ProductData::getTotal)
                .sum();
        UI.getCurrent().getSession().setAttribute("subtotal", subtotal);
        
        Notification.show("✓ " + name + " added to cart!", 2000, Notification.Position.BOTTOM_START);
        
        System.out.println("Product added to cart: " + name);
        System.out.println("Total items in cart: " + cartItems.size());
    }
    
    public static String[] getProductNames() {
        return PRODUCT_NAMES;
    }
    
    public static String[] getProductCategories() {
        return PRODUCT_CATEGORIES;
    }
    
    public static String[] getProductPrices() {
        return PRODUCT_PRICES;
    }
    
    public static String[] getProductImages() {
        return PRODUCT_IMAGES;
    }
    
    public static int getTotalProducts() {
        return PRODUCT_NAMES.length;
    }
}