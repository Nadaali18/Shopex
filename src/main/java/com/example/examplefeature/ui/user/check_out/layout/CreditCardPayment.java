package com.example.examplefeature.ui.user.check_out.layout;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CreditCardPayment {

    public VerticalLayout create() {
        VerticalLayout cardSection = new VerticalLayout();
        cardSection.setSpacing(true);
        cardSection.setPadding(true);
        cardSection.getStyle()
                .set("background-color", "#f5f5f5")
                .set("border-radius", "8px")
                .set("border", "1px solid #ddd");

        TextField cardNumber = createTextField("Card Number", "1234 5678 9012 3456", true);
        TextField expiryDate = createTextField("Expiry Date", "MM/YY", true);
        TextField cvv = createTextField("CVV", "123", true);
        TextField cardHolder = createTextField("Card Holder Name", "John Doe", true);

        HorizontalLayout cardDetails = new HorizontalLayout();
        cardDetails.setWidthFull();
        cardDetails.setSpacing(true);
        expiryDate.setWidth("50%");
        cvv.setWidth("50%");
        cardDetails.add(expiryDate, cvv);

        cardSection.add(cardNumber, cardDetails, cardHolder);
        return cardSection;
    }

    private TextField createTextField(String label, String placeholder, boolean required) {
        TextField field = new TextField(label);
        field.setPlaceholder(placeholder);
        field.setWidthFull();
        field.setRequired(required);
        return field;
    }
}