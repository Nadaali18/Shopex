package com.example.examplefeature.ui.user.cart.layout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class CartItem {
    private HorizontalLayout row;
    private int price;
    private int quantity;
    private String name;
    private String category;
    private String imagePath;
    private Paragraph totalTxt;
    private Paragraph qtyText;
    private CartContainer cartContainer;
    public CartItem(String name, int price, String category, String imagePath, 
                   int initialQuantity, CartContainer cartContainer) {
        this.price = price;
        this.name = name;
        this.category = category;
        this.imagePath = imagePath;
        this.quantity = initialQuantity;
        this.cartContainer = cartContainer;    
        createRow();
    }
    private void createRow() {
        row = new HorizontalLayout();
        row.setWidthFull();
        row.setAlignItems(FlexComponent.Alignment.CENTER);
        row.getStyle()
            .set("padding", "15px 20px")
            .set("border-bottom", "1px solid #eee");

        row.add(createProductColumn(), createPriceColumn(), 
                createQuantityColumn(), createTotalColumn(), createActionsColumn());
        row.setFlexGrow(1, createProductColumn());
    }
    private HorizontalLayout createProductColumn() {
        HorizontalLayout productInfo = new HorizontalLayout();
        productInfo.setSpacing(true);
        productInfo.setPadding(false);
        productInfo.setAlignItems(FlexComponent.Alignment.CENTER);
        productInfo.setWidth("35%");
        Image img = new Image(imagePath, name);
        img.setWidth("120px");
        img.setHeight("120px");
        img.getStyle()
            .set("object-fit", "cover")
            .set("border-radius", "8px");
        VerticalLayout productDetails = new VerticalLayout();
        productDetails.setSpacing(false);
        productDetails.setPadding(false);
        H4 productName = new H4(name);
        productName.getStyle()
            .set("margin", "0 0 5px 0")
            .set("font-size", "16px")
            .set("font-weight", "bold");
        Paragraph categoryText = new Paragraph(category);
        categoryText.getStyle()
            .set("margin", "0")
            .set("color", "#666")
            .set("font-size", "14px");
        productDetails.add(productName, categoryText);
        productInfo.add(img, productDetails);
        return productInfo;
    }
    private VerticalLayout createPriceColumn() {
        VerticalLayout priceCol = new VerticalLayout();
        priceCol.setSpacing(false);
        priceCol.setPadding(false);
        priceCol.setWidth("15%");
        priceCol.setAlignItems(FlexComponent.Alignment.CENTER);
        priceCol.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Paragraph priceText = new Paragraph("$" + price);
        priceText.getStyle()
            .set("margin", "0")
            .set("font-weight", "bold")
            .set("font-size", "16px")
            .set("text-align", "center");
        priceCol.add(priceText);
        return priceCol;
    }
    private VerticalLayout createQuantityColumn() {
        VerticalLayout qtyCol = new VerticalLayout();
        qtyCol.setSpacing(false);
        qtyCol.setPadding(false);
        qtyCol.setWidth("20%");
        qtyCol.setAlignItems(FlexComponent.Alignment.CENTER);
        qtyCol.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        HorizontalLayout qtyBox = new HorizontalLayout();
        qtyBox.setSpacing(true);
        qtyBox.setAlignItems(FlexComponent.Alignment.CENTER);
        qtyBox.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Button minus = createQuantityButton("-", true);
        qtyText = new Paragraph(String.valueOf(quantity));
        qtyText.getStyle()
            .set("margin", "0 10px")
            .set("font-weight", "bold")
            .set("font-size", "16px")
            .set("text-align", "center");
        Button plus = createQuantityButton("+", false);
        qtyBox.add(minus, qtyText, plus);
        qtyCol.add(qtyBox);
        return qtyCol;
    }
    private Button createQuantityButton(String symbol, boolean isMinus) {
        Button button = new Button(symbol, e -> {
            if (isMinus && quantity > 1) {
                quantity--;
            } else if (!isMinus) {
                quantity++;
            }
            updateRowTotal();
            cartContainer.updateSessionData();
        });
        button.getStyle()
            .set("min-width", "35px")
            .set("min-height", "35px")
            .set("font-weight", "bold")
            .set("background-color", "#f0f0f0")
            .set("border", "1px solid #ddd");  
        return button;
    }
    private VerticalLayout createTotalColumn() {
        VerticalLayout totalCol = new VerticalLayout();
        totalCol.setSpacing(false);
        totalCol.setPadding(false);
        totalCol.setWidth("15%");
        totalCol.setAlignItems(FlexComponent.Alignment.CENTER);
        totalCol.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        totalTxt = new Paragraph("$" + (price * quantity));
        totalTxt.getStyle()
            .set("margin", "0")
            .set("font-weight", "bold")
            .set("font-size", "16px")
            .set("color", "#3f0d50")
            .set("text-align", "center");
        totalCol.add(totalTxt);
        return totalCol;
    }
    private VerticalLayout createActionsColumn() {
        VerticalLayout actionsCol = new VerticalLayout();
        actionsCol.setSpacing(false);
        actionsCol.setPadding(false);
        actionsCol.setWidth("15%");
        actionsCol.setAlignItems(FlexComponent.Alignment.CENTER);
        actionsCol.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        Button delete = new Button(new Icon(VaadinIcon.TRASH), e -> {
            cartContainer.removeCartItem(this);
        });    
        delete.getStyle()
            .set("color", "red")
            .set("background", "none")
            .set("border", "none")
            .set("cursor", "pointer");
        actionsCol.add(delete);
        return actionsCol;
    }

    private void updateRowTotal() {
        int result = price * quantity;
        totalTxt.setText("$" + result);
        qtyText.setText(String.valueOf(quantity));
    }

    // Getters
    public HorizontalLayout getRow() { return row; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public String getImagePath() { return imagePath; }
    public int getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getTotal() { return price * quantity; }
}