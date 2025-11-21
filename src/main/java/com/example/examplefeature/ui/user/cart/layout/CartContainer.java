package com.example.examplefeature.ui.user.cart.layout;

import com.example.examplefeature.model.ProductData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import java.util.ArrayList;
import java.util.List;

public class CartContainer extends VerticalLayout {
    private List<CartItem> cartItems = new ArrayList<>();

    public CartContainer() {
        setWidthFull();
        setPadding(true);
        getStyle()
            .set("background-color", "white")
            .set("border-radius", "10px")
            .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)")
            .set("margin", "15px");
        add(createHeaderRow());
        loadCartProducts();
    }
    private HorizontalLayout createHeaderRow() {
        HorizontalLayout headerRow = new HorizontalLayout();
        headerRow.setWidthFull();
        headerRow.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerRow.getStyle()
            .set("font-weight", "bold")
            .set("color", "#444")
            .set("padding", "15px 20px")
            .set("border-bottom", "1px solid #eee");
        String[] headers = {"PRODUCT", "PRICE", "QTY", "TOTAL", "ACTIONS"};
        String[] widths = {"35%", "15%", "20%", "15%", "15%"};
        for (int i = 0; i < headers.length; i++) {
            Div header = new Div(new com.vaadin.flow.component.Text(headers[i]));
            header.setWidth(widths[i]);
            header.getStyle().set("text-align", "center");
            headerRow.add(header);
        }
        return headerRow;
    }
    public void loadCartProducts() {
        cartItems.clear();
        List<com.vaadin.flow.component.Component> toRemove = new ArrayList<>();
        for (com.vaadin.flow.component.Component comp : getChildren()
                .filter(c -> c != getComponentAt(0))
                .toList()) {
            toRemove.add(comp);
        }
        toRemove.forEach(this::remove);
        @SuppressWarnings("unchecked")
        List<ProductData> sessionItems = (List<ProductData>) UI.getCurrent().getSession()
                .getAttribute("cartItems");
        if (sessionItems != null && !sessionItems.isEmpty()) {
            for (ProductData productData : sessionItems) {
                CartItem cartItem = new CartItem(
                    productData.getName(),
                    productData.getNumericPrice(),
                    productData.getCategory(),
                    productData.getImagePath(),
                    productData.getQuantity(),
                    this
                );
                cartItems.add(cartItem);
                addComponentAtIndex(1, cartItem.getRow());
            }
        } else {
            showEmptyCartMessage();
        }
    }
    private void showEmptyCartMessage() {
        Div emptyCartMessage = new Div();
        emptyCartMessage.setText("Your cart is empty");
        emptyCartMessage.getStyle()
            .set("text-align", "center")
            .set("padding", "40px")
            .set("color", "#666")
            .set("font-size", "18px");
        add(emptyCartMessage);
    }
    public void removeCartItem(CartItem item) {
        remove(item.getRow());
        cartItems.remove(item);
        updateSessionData();
    }
    public void updateSessionData() {
        List<ProductData> sessionItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            ProductData productData = new ProductData(
                cartItem.getName(),
                cartItem.getCategory(),
                "$" + cartItem.getPrice(),
                cartItem.getImagePath()
            );
            productData.setQuantity(cartItem.getQuantity());
            sessionItems.add(productData);
        }  
        UI.getCurrent().getSession().setAttribute("cartItems", sessionItems);
        
        double subtotal = cartItems.stream()
                .mapToInt(CartItem::getTotal)
                .sum();
        UI.getCurrent().getSession().setAttribute("subtotal", subtotal);
    }
    public double getSubtotal() {
        return cartItems.stream()
                .mapToInt(CartItem::getTotal)
                .sum();
    }
    public int getItemCount() {
        return cartItems.size();
    }
}