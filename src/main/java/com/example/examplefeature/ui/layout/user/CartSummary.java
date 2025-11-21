package com.example.examplefeature.ui.layout.user;
import com.example.examplefeature.ui.services.CartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CartSummary extends VerticalLayout {

    public CartSummary() {

        setWidth("300px");
        getStyle()
                .set("padding", "20px")
                .set("border", "1px solid #eee")
                .set("border-radius", "12px")
                .set("background-color", "white")
                .set("box-shadow", "0 4px 8px rgba(0,0,0,0.1)");

        double subtotal = CartService.calculateSubtotal();
        double shipping = 10;
        double total = subtotal + shipping;

        H3 title = new H3("Order Summary");

        Paragraph sub = new Paragraph("Subtotal: $" + subtotal);
        Paragraph ship = new Paragraph("Shipping: $" + shipping);
        Paragraph tot = new Paragraph("Total: $" + total);

        Button checkout = new Button("Checkout");

        checkout.getStyle()
                .set("background-color", "#3f0d50ff")
                .set("color", "white");

        add(title, sub, ship, tot, checkout);
    }
}
