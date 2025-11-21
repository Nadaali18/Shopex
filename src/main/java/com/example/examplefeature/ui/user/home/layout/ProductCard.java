package com.example.examplefeature.ui.user.home.layout;

import com.example.examplefeature.ui.services.HomeCartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class ProductCard extends VerticalLayout {
    private static final String PRIMARY_COLOR = "#3f0d50ff";
    
    public ProductCard(String name, String category, String price, String imagePath) {
        createCard(name, category, price, imagePath);
    }
    
    private void createCard(String name, String category, String price, String imagePath) {
        applyCardStyle();
        
        Image productImage = createProductImage(imagePath, name);
        H4 productName = createProductName(name);
        HorizontalLayout infoLayout = createProductInfo(category, price);
        Button addToCart = createAddToCartButton(name, category, price, imagePath);
        
        add(productImage, productName, infoLayout, addToCart);
    }
    
    private void applyCardStyle() {
        getStyle()
            .set("border", "1px solid #e0e0e0")
            .set("border-radius", "12px")
            .set("width", "280px")
            .set("min-width", "280px")
            .set("padding", "15px")
            .set("background", "white")
            .set("box-shadow", "0 4px 8px rgba(0,0,0,0.1)")
            .set("transition", "transform 0.3s, box-shadow 0.3s");

        // Hover effects
        getElement().addEventListener("mouseenter", e -> {
            getStyle()
                .set("transform", "translateY(-5px)")
                .set("box-shadow", "0 8px 16px rgba(0,0,0,0.2)");
        });
        
        getElement().addEventListener("mouseleave", e -> {
            getStyle()
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

        button.addClickListener(e -> HomeCartService.addProductToCart(name, category, price, imagePath));
        return button;
    }
}