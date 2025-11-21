package com.example.examplefeature.ui.user.check_out.layout;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CashOnDeliveryPayment {

    public VerticalLayout create() {
        VerticalLayout cashSection = new VerticalLayout();
        cashSection.setSpacing(true);
        cashSection.setPadding(true);
        cashSection.getStyle()
                .set("background-color", "#f5f5f5")
                .set("border-radius", "8px")
                .set("border", "1px solid #ddd");

        Paragraph description = new Paragraph(
            "Pay cash when your order is delivered. Our delivery agent will collect the payment at your doorstep."
        );
        description.getStyle()
                .set("color", "#666")
                .set("font-size", "14px")
                .set("margin", "0");

        cashSection.add(description);
        return cashSection;
    }
}