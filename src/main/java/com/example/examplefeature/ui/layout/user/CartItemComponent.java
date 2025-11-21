package com.example.examplefeature.ui.layout.user;
import com.example.examplefeature.ui.services.CartService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.Map;

public class CartItemComponent extends HorizontalLayout {

    public CartItemComponent(Map<String, Object> item) {
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        getStyle()
                .set("padding", "15px")
                .set("border-bottom", "1px solid #eee");

        String name = (String) item.get("name");
        double price = (double) item.get("price");
        int qty = (int) item.get("qty");

        Image img = new Image("/images/product1.jpg", "Product");
        img.setWidth("80px");
        img.setHeight("80px");
        img.getStyle().set("object-fit", "cover");

        VerticalLayout info = new VerticalLayout();
        info.setPadding(false);
        info.setSpacing(false);

        H4 title = new H4(name);
        title.getStyle().set("margin", "0");

        Span priceLabel = new Span("$" + price);
        priceLabel.getStyle().set("color", "green");

        info.add(title, priceLabel);

        Span qtyLabel = new Span("Qty: " + qty);

        Button deleteBtn = new Button("Remove", e -> {
            CartService.removeProduct(name);
            getParent().ifPresent(parent -> parent.getElement().callJsFunction("location.reload"));
        });

        deleteBtn.getStyle()
                .set("background-color", "red")
                .set("color", "white");

        add(img, info, qtyLabel, deleteBtn);
        setJustifyContentMode(JustifyContentMode.BETWEEN);
    }
}
