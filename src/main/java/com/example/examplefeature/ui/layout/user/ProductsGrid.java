package com.example.examplefeature.ui.layout.user;
import com.example.examplefeature.ui.services.CartService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;

public class ProductsGrid extends Div {

    public ProductsGrid() {

        setWidthFull();
        getStyle().set("margin-top", "30px");

        add(createCard("Nike Shoes", "/images/product1.jpg", 40));
        add(createCard("Red Bag", "/images/product2.jpg", 35));
        add(createCard("T-shirt White", "/images/product3.jpg", 25));
        add(createCard("Jeans Blue", "/images/product4.jpg", 30));
    }

    private Div createCard(String title, String imagePath, double price) {

        Div card = new Div();
        card.getStyle()
                .set("width", "250px")
                .set("padding", "15px")
                .set("margin", "10px")
                .set("border-radius", "12px")
                .set("background-color", "white")
                .set("box-shadow", "0 3px 10px rgba(0,0,0,0.1)")
                .set("display", "inline-block");

        Image img = new Image(imagePath, title);
        img.setWidth("100%");
        img.setHeight("200px");
        img.getStyle().set("object-fit", "cover");

        Paragraph name = new Paragraph(title);
        name.getStyle().set("font-weight", "bold");

        Paragraph priceLabel = new Paragraph("$" + price);
        priceLabel.getStyle().set("color", "green");

        Button btn = new Button("Add to Cart", event -> {
            CartService.addProduct(title, price, 1);
            UI.getCurrent().navigate("cart");
        });

        card.add(img, name, priceLabel, btn);

        return card;
    }
}
