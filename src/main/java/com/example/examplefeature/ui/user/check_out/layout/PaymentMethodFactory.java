package com.example.examplefeature.ui.user.check_out.layout;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PaymentMethodFactory {
    
    public VerticalLayout createPaymentMethod(String methodType) {
        switch (methodType) {
            case "E-Wallet":
                return new EWalletPayment().create();
            case "Credit Card":
                return new CreditCardPayment().create();
            case "Cash on Delivery":
            default:
                return new CashOnDeliveryPayment().create();
        }
    }
}