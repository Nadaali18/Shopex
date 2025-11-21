package com.example.examplefeature.ui.user.cart.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

public class CheckoutBox extends HorizontalLayout {
    private CartContainer cartContainer;
    private double shippingCost = 0;
    private Paragraph subtotalTxt;
    private Paragraph shippingTxt;
    private H3 finalTotalTxt;

    public CheckoutBox(CartContainer cartContainer) {
        this.cartContainer = cartContainer;
        setWidthFull();
        setPadding(true);
        setSpacing(true);       
        add(createShippingSection(), createSummarySection());
    }
    private VerticalLayout createShippingSection() {
        VerticalLayout shippingSection = new VerticalLayout();
        shippingSection.setWidth("70%");
        shippingSection.getStyle()
            .set("background-color", "#f5f5f5")
            .set("border-radius", "10px")
            .set("padding", "20px");
        H3 title = new H3("Choose shipping mode:");
        title.getStyle().set("margin-top", "0").set("text-align", "center");
        RadioButtonGroup<String> shippingOptions = createShippingOptions();
        shippingSection.add(title, shippingOptions);
        shippingSection.setAlignItems(FlexComponent.Alignment.CENTER);
        return shippingSection;
    }
    private RadioButtonGroup<String> createShippingOptions() {
        RadioButtonGroup<String> options = new RadioButtonGroup<>();
        options.setItems("Free Pickup", "Delivery ($10)");
        options.setValue("Free Pickup");
        options.addValueChangeListener(v -> {
            shippingCost = v.getValue().equals("Delivery ($10)") ? 10 : 0;
            updateTotals();
        });
        options.getStyle()
            .set("display", "flex")
            .set("flex-direction", "column")
            .set("align-items", "center");
            
        return options;
    }
    private VerticalLayout createSummarySection() {
        VerticalLayout summary = new VerticalLayout();
        summary.setWidth("30%");
        summary.getStyle()
            .set("background-color", "white")
            .set("border-radius", "10px")
            .set("padding", "20px")
            .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");
        subtotalTxt = new Paragraph("Subtotal: $0");
        subtotalTxt.getStyle().set("margin", "10px 0").set("text-align", "center");
        shippingTxt = new Paragraph("Shipping: $0");
        shippingTxt.getStyle().set("margin", "10px 0").set("text-align", "center");
        finalTotalTxt = new H3("Total: $0");
        finalTotalTxt.getStyle()
            .set("color", "#ff66cc")
            .set("margin", "20px 0 10px 0")
            .set("text-align", "center");
        Button checkoutBtn = createCheckoutButton();
        Button continueShoppingBtn = createContinueShoppingButton();
        summary.add(subtotalTxt, shippingTxt, finalTotalTxt, checkoutBtn, continueShoppingBtn);
        summary.setSpacing(false);
        summary.setAlignItems(FlexComponent.Alignment.CENTER);   
        updateTotals();
        return summary;
    }
    private Button createCheckoutButton() {
        Button checkout = new Button("Checkout", e -> {
            if (cartContainer.getItemCount() == 0) {
                Notification.show("Your cart is empty! Add some products first.", 
                    3000, Notification.Position.MIDDLE);
                return;
            }     
            UI.getCurrent().getSession().setAttribute("shippingCost", shippingCost);
            UI.getCurrent().getSession().setAttribute("total", 
                cartContainer.getSubtotal() + shippingCost);         
            UI.getCurrent().navigate("checkout");
        });
        checkout.getStyle()
            .set("background-color", "#3f0d50")
            .set("color", "white")
            .set("width", "100%")
            .set("padding", "15px")
            .set("margin-top", "10px")
            .set("border-radius", "8px")
            .set("font-weight", "bold")
            .set("cursor", "pointer");
            
        return checkout;
    }
    private Button createContinueShoppingButton() {
        Button continueShopping = new Button("Continue Shopping", e -> {
            UI.getCurrent().navigate("home");
        });
        continueShopping.getStyle()
            .set("background-color", "transparent")
            .set("color", "#3f0d50")
            .set("width", "100%")
            .set("padding", "12px")
            .set("margin-top", "10px")
            .set("border", "2px solid #3f0d50")
            .set("border-radius", "8px")
            .set("font-weight", "bold")
            .set("cursor", "pointer");     
        return continueShopping;
    }
    public void updateTotals() {
        double subtotal = cartContainer.getSubtotal();
        subtotalTxt.setText("Subtotal: $" + subtotal);
        shippingTxt.setText("Shipping: $" + shippingCost);
        finalTotalTxt.setText("Total: $" + (subtotal + shippingCost));
    }
}