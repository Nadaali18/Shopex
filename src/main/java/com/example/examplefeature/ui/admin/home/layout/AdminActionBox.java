package com.example.examplefeature.ui.admin.home.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class AdminActionBox extends VerticalLayout {

    public AdminActionBox(String title, String backgroundImage, String description, String navigateTo) {
        createActionBox(title, backgroundImage, description, navigateTo);
    }

    private void createActionBox(String title, String backgroundImage, String description, String navigateTo) {
        setWidth("800px");
        setHeight("450px");
        setSpacing(false);
        setPadding(true);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        getStyle()
                .set("background", "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('" + backgroundImage + "')")
                .set("background-size", "cover")
                .set("background-position", "center")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.3)")
                .set("border", "2px solid rgba(255,255,255,0.2)")
                .set("transition", "all 0.3s ease")
                .set("cursor", "pointer")
                .set("margin", "15px")
                .set("position", "relative")
                .set("overflow", "hidden");

        setupHoverEffects(backgroundImage);

        H3 actionTitle = new H3(title);
        actionTitle.getStyle()
                .set("color", "white")
                .set("margin", "0 0 10px 0")
                .set("font-size", "2rem")
                .set("font-weight", "bold")
                .set("text-align", "center")
                .set("text-shadow", "2px 2px 6px rgba(0,0,0,0.8)");

        Paragraph actionDescription = new Paragraph(description);
        actionDescription.getStyle()
                .set("color", "rgba(255,255,255,0.9)")
                .set("text-align", "center")
                .set("margin", "0")
                .set("line-height", "1.4")
                .set("font-size", "16px")
                .set("text-shadow", "1px 1px 3px rgba(0,0,0,0.6)")
                .set("max-width", "350px");

        addClickListener(e -> UI.getCurrent().navigate(navigateTo));

        add(actionTitle, actionDescription);
    }

    private void setupHoverEffects(String backgroundImage) {
        getElement().addEventListener("mouseenter", e -> {
            getStyle()
                    .set("transform", "translateY(-10px)")
                    .set("box-shadow", "0 15px 35px rgba(0,0,0,0.4)")
                    .set("background", "linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('" + backgroundImage + "')");
        });

        getElement().addEventListener("mouseleave", e -> {
            getStyle()
                    .set("transform", "translateY(0)")
                    .set("box-shadow", "0 8px 25px rgba(0,0,0,0.3)")
                    .set("background", "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('" + backgroundImage + "')");
        });
    }
}