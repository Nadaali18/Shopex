package com.example.examplefeature.ui.layout.user;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class HomeCategoriesBar extends HorizontalLayout {

    public HomeCategoriesBar() {
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        getStyle().set("margin-top", "25px");

        addCategory("Shoes", "/images/shoes.png", "shoes");
        addCategory("Bags", "/images/bags.png", "bags");
        addCategory("Clothes", "/images/clothes.png", "clothes");
    }

    private void addCategory(String name, String image, String route) {

        VerticalLayout box = new VerticalLayout();
        box.setAlignItems(Alignment.CENTER);
        box.getStyle().set("background-color", "white")
                .set("border-radius", "15px")
                .set("padding", "15px")
                .set("width", "150px")
                .set("cursor", "pointer")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.15)");

        Image img = new Image(image, name);
        img.setWidth("80px");
        img.setHeight("80px");

        Span label = new Span(name);
        label.getStyle().set("font-weight", "bold");

        box.add(img, label);

        box.addClickListener(e -> UI.getCurrent().navigate(route));

        add(box);
    }
}
