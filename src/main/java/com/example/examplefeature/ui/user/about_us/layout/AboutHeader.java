package com.example.examplefeature.ui.user.about_us.layout;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AboutHeader extends VerticalLayout {

    public AboutHeader() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(Alignment.CENTER);

        H1 mainTitle = new H1("About Us");
        mainTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0 50px 0")
                .set("font-size", "3rem")
                .set("font-weight", "bold")
                .set("text-shadow", "2px 2px 4px rgba(0,0,0,0.1)");

        add(mainTitle);
    }
}