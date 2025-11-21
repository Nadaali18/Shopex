package com.example.examplefeature.ui.user.cart.view;

import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.AppHeader;
import com.example.examplefeature.ui.user.cart.layout.CartContainer;
import com.example.examplefeature.ui.user.cart.layout.CheckoutBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("cart")
public class CartView extends VerticalLayout {

    public CartView() {
        setWidthFull();
        setPadding(false);

        add(new AppHeader("user","cart"));
        add(createTitle());
        
        CartContainer cartContainer = new CartContainer();
        CheckoutBox checkoutBox = new CheckoutBox(cartContainer);
        
        add(cartContainer);
        add(checkoutBox);
        add(new AppFooter());
    }

    private H2 createTitle() {
        H2 title = new H2("My Cart");
        title.getStyle().set("margin", "20px 0 10px 40px");
        return title;
    }
}