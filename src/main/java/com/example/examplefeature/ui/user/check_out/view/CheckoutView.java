package com.example.examplefeature.ui.user.check_out.view;

import com.example.examplefeature.services.CheckoutService;
import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.AppHeader;
import com.example.examplefeature.ui.user.check_out.layout.OrderSummary;
import com.example.examplefeature.ui.user.check_out.layout.PaymentSection;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("checkout")
@PermitAll
public class CheckoutView extends VerticalLayout {

    private final CheckoutService checkoutService;

    public CheckoutView(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
        setupUI();
    }

    private void setupUI() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(new AppHeader("user","checkout"));
        add(createPageTitle());
        add(createMainContent());
        add(new AppFooter());
    }

    private H1 createPageTitle() {
        H1 pageTitle = new H1("Checkout");
        pageTitle.getStyle()
                .set("text-align", "center")
                .set("margin", "40px 0 20px 0")
                .set("color", "#3f0d50");
        return pageTitle;
    }

    private HorizontalLayout createMainContent() {
        HorizontalLayout mainContent = new HorizontalLayout();
        mainContent.setWidthFull();
        mainContent.setPadding(true);
        mainContent.setSpacing(true);

        PaymentSection paymentSection = new PaymentSection();
        OrderSummary orderSummary = new OrderSummary(checkoutService);

        mainContent.add(paymentSection, orderSummary);
        return mainContent;
    }
}