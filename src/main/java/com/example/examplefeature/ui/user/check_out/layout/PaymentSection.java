package com.example.examplefeature.ui.user.check_out.layout;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

public class PaymentSection extends VerticalLayout {

    private PaymentMethodFactory paymentMethodFactory;
    private VerticalLayout paymentContainer;

    public PaymentSection() {
        paymentMethodFactory = new PaymentMethodFactory();
        createPaymentSection();
    }

    private void createPaymentSection() {
        setWidth("60%");
        setPadding(true);
        getStyle()
                .set("background-color", "white")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");

        H2 sectionTitle = new H2("Payment Method");
        sectionTitle.getStyle()
                .set("margin-top", "0")
                .set("color", "#3f0d50");

        RadioButtonGroup<String> paymentOptions = createPaymentOptions();
        paymentContainer = new VerticalLayout();
        paymentContainer.setSpacing(true);
        paymentContainer.setPadding(false);
        paymentContainer.add(paymentOptions, paymentMethodFactory.createPaymentMethod("Cash on Delivery"));

        add(sectionTitle, paymentContainer);
    }

    private RadioButtonGroup<String> createPaymentOptions() {
        RadioButtonGroup<String> paymentOptions = new RadioButtonGroup<>();
        paymentOptions.setItems("Cash on Delivery", "E-Wallet", "Credit Card");
        paymentOptions.setValue("Cash on Delivery");

        paymentOptions.addValueChangeListener(event -> {
            paymentContainer.removeAll();
            paymentContainer.add(paymentOptions);
            paymentContainer.add(paymentMethodFactory.createPaymentMethod(event.getValue()));
        });

        return paymentOptions;
    }
}