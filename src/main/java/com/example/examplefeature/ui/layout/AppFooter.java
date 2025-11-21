package com.example.examplefeature.ui.layout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AppFooter extends VerticalLayout {

    public AppFooter() {
        setWidthFull();
        setPadding(true);
        getStyle()
                .set("background-color", "#2a0a3f")
                .set("color", "white")
                .set("text-align", "center")
                .set("margin-top", "50px");

        H3 shopName = new H3("Shopex");
        shopName.getStyle().set("color", "pink").set("margin", "0");

        Paragraph copy = new Paragraph("© 2024 Shopex. All rights reserved.");
        copy.getStyle().set("color", "#ccc").set("font-size", "14px");

        Paragraph contact = new Paragraph("Contact: info@shopex.com | +20 100 123 4567");
        contact.getStyle().set("color", "#ccc").set("font-size", "14px");

        add(shopName, copy, contact);
        setAlignItems(Alignment.CENTER);
    }
}
