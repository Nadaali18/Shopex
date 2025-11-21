package com.example.examplefeature.ui.user.check_out.view;
import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.UserHeader;
import com.example.examplefeature.ui.user.check_out.layout.OrderSummary;
import com.example.examplefeature.ui.user.check_out.layout.PaymentSection;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("checkout")
public class CheckoutView extends VerticalLayout {

    public CheckoutView() {
        setupUI();
    }

    private void setupUI() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(new UserHeader("checkout"));
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
        OrderSummary orderSummary = new OrderSummary();

        mainContent.add(paymentSection, orderSummary);
        return mainContent;
    }
}