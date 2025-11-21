package com.example.examplefeature.ui.user.about_us.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class InfoBox extends VerticalLayout {
    
    public InfoBox(String title, VaadinIcon iconType, Component content) {
        createBox(title, iconType, content);
    }
    
    private void createBox(String title, VaadinIcon iconType, Component content) {
        setWidth("45%");
        setSpacing(false);
        setPadding(true);
        getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("border", "2px solid #e0e0e0")
                .set("min-height", "400px");

        // العنوان
        H2 boxTitle = new H2(title);
        boxTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "20px 0 30px 0")
                .set("font-size", "2rem")
                .set("font-weight", "bold");

        // أيقونة
        Icon icon = new Icon(iconType);
        icon.setSize("50px");
        icon.getStyle()
                .set("color", "#ff66cc")
                .set("margin", "0 auto 20px auto");

        add(icon, boxTitle, content);
        setAlignItems(Alignment.CENTER);
    }
}