package com.example.examplefeature.ui.user;
import com.example.examplefeature.ui.layout.user.CartListComponent;
import com.example.examplefeature.ui.layout.user.CartSummary;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

@Route("cart")
public class CartView extends HorizontalLayout {

    public CartView() {

        setWidthFull();
        setSpacing(true);

        CartListComponent list = new CartListComponent();
        CartSummary summary = new CartSummary();

        add(list, summary);
    }
}
