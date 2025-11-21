package com.example.examplefeature.ui.layout.user;
import com.example.examplefeature.ui.services.CartService;
import com.vaadin.flow.component.html.Div;
import java.util.List;
import java.util.Map;

public class CartListComponent extends Div {

    public CartListComponent() {

        setWidthFull();

        List<Map<String, Object>> cart = CartService.getCart();

        if (cart == null || cart.isEmpty()) {
            setText("Your cart is empty.");
            return;
        }

        cart.forEach(item -> add(new CartItemComponent(item)));
    }
}
