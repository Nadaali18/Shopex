package com.example.examplefeature.ui.user.check_out.layout;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class EWalletPayment {

    public VerticalLayout create() {
        VerticalLayout ewalletSection = new VerticalLayout();
        ewalletSection.setSpacing(true);
        ewalletSection.setPadding(true);
        ewalletSection.getStyle()
                .set("background-color", "#f5f5f5")
                .set("border-radius", "8px")
                .set("border", "1px solid #ddd");

        ComboBox<String> walletType = new ComboBox<>("Select E-Wallet");
        walletType.setItems("Vodafone Cash", "Orange Money", "Etisalat Cash", "WePay");
        walletType.setPlaceholder("Choose your e-wallet");
        walletType.setWidthFull();

        Paragraph walletNumber = new Paragraph("Account Number: 0100 123 4567");
        walletNumber.getStyle()
                .set("font-weight", "bold")
                .set("color", "#3f0d50")
                .set("margin", "10px 0")
                .set("text-align", "center");

        Paragraph confirmationMsg = new Paragraph("The number you will transfer to");
        confirmationMsg.getStyle()
                .set("color", "#666")
                .set("font-size", "14px")
                .set("margin", "5px 0")
                .set("text-align", "center");

        ScreenshotUpload screenshotUpload = new ScreenshotUpload();
        VerticalLayout uploadSection = screenshotUpload.create();

        ewalletSection.add(walletType, confirmationMsg, walletNumber, uploadSection);
        return ewalletSection;
    }
}