package com.example.examplefeature.ui.admin.home.layout;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class AdminHeroSection extends VerticalLayout {

    public AdminHeroSection() {
        createHeroSection();
    }

    private void createHeroSection() {
        setWidthFull();
        setHeight("400px");
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        getStyle()
                .set("background", "linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('/images/admin.jpg')")
                .set("background-size", "cover")
                .set("background-position", "center")
                .set("color", "white")
                .set("position", "relative");

        H1 heroTitle = new H1("Admin Dashboard");
        heroTitle.getStyle()
                .set("font-size", "3.5rem")
                .set("font-weight", "bold")
                .set("text-align", "center")
                .set("margin", "0")
                .set("text-shadow", "2px 2px 8px rgba(255, 255, 255, 0.8)");

        Paragraph heroSubtitle = new Paragraph("Manage Your E-commerce Store Efficiently");
        heroSubtitle.getStyle()
                .set("font-size", "1.5rem")
                .set("text-align", "center")
                .set("margin", "20px 0 0 0")
                .set("text-shadow", "2px 2px 6px rgba(0,0,0,0.6)");

        add(heroTitle, heroSubtitle);
    }
}