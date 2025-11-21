package com.example.examplefeature.ui.user.check_out.layout;

import com.example.examplefeature.model.ProductData;
import com.example.examplefeature.ui.services.CheckoutService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.util.List;

public class OrderSummary extends VerticalLayout {

    private CheckoutService checkoutService;
    private List<ProductData> cartItems;
    private double subtotal;
    private double shippingCost;
    private double total;

    public OrderSummary() {
        checkoutService = new CheckoutService();
        loadCartData();
        createOrderSummary();
    }

    private void loadCartData() {
        this.cartItems = checkoutService.getCartItemsFromSession();
        this.subtotal = checkoutService.getSubtotalFromSession();
        this.shippingCost = checkoutService.getShippingCostFromSession();
        this.total = checkoutService.getTotalFromSession();
    }

    private void createOrderSummary() {
        setWidth("40%");
        setPadding(true);
        getStyle()
                .set("background-color", "white")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");

        H2 title = new H2("Order Summary");
        title.getStyle()
                .set("margin-top", "0")
                .set("color", "#3f0d50");

        // تفاصيل المنتجات
        VerticalLayout productsList = createProductsList();

        // الحسابات
        Hr divider = createDivider();
        Hr divider2 = createDivider();

        HorizontalLayout subtotalLayout = createPriceRow("Subtotal:", "$" + subtotal, false);
        HorizontalLayout shippingLayout = createPriceRow("Shipping:", "$" + shippingCost, false);
        HorizontalLayout totalLayout = createPriceRow("Total:", "$" + total, true);

        // الأزرار
        Button confirmOrder = createConfirmOrderButton();
        Button continueShopping = createContinueShoppingButton();

        add(title, productsList, divider, subtotalLayout, shippingLayout, divider2, totalLayout, confirmOrder, continueShopping);
        setSpacing(false);
    }

    private VerticalLayout createProductsList() {
        VerticalLayout productsList = new VerticalLayout();
        productsList.setSpacing(true);
        productsList.setPadding(false);

        if (cartItems.isEmpty()) {
            Paragraph emptyMessage = new Paragraph("No items in cart");
            emptyMessage.getStyle()
                    .set("color", "#666")
                    .set("text-align", "center")
                    .set("font-style", "italic");
            productsList.add(emptyMessage);
        } else {
            for (ProductData item : cartItems) {
                productsList.add(createProductItem(item));
            }
        }

        return productsList;
    }

    private HorizontalLayout createProductItem(ProductData item) {
        HorizontalLayout productItem = new HorizontalLayout();
        productItem.setWidthFull();
        productItem.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        productItem.setAlignItems(Alignment.CENTER);
        productItem.getStyle()
                .set("padding", "10px")
                .set("border-bottom", "1px solid #f0f0f0");

        VerticalLayout productInfo = new VerticalLayout();
        productInfo.setSpacing(false);
        productInfo.setPadding(false);
        
        Paragraph productName = new Paragraph(item.getName());
        productName.getStyle()
                .set("margin", "0")
                .set("font-weight", "bold")
                .set("font-size", "16px");
        
        Paragraph productDetails = new Paragraph(item.getCategory() + " × " + item.getQuantity());
        productDetails.getStyle()
                .set("margin", "0")
                .set("color", "#666")
                .set("font-size", "14px");

        productInfo.add(productName, productDetails);

        Paragraph productPrice = new Paragraph("$" + (item.getNumericPrice() * item.getQuantity()));
        productPrice.getStyle()
                .set("font-weight", "bold")
                .set("color", "#3f0d50")
                .set("font-size", "16px");

        productItem.add(productInfo, productPrice);
        return productItem;
    }

    private Hr createDivider() {
        Hr divider = new Hr();
        divider.getStyle().set("margin", "20px 0").set("border", "1px solid #eee");
        return divider;
    }

    private HorizontalLayout createPriceRow(String label, String value, boolean isTotal) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        
        Paragraph labelText = new Paragraph(label);
        Paragraph valueText = new Paragraph(value);
        
        if (isTotal) {
            labelText.getStyle().set("font-weight", "bold").set("font-size", "18px");
            valueText.getStyle()
                    .set("font-weight", "bold")
                    .set("color", "#ff66cc")
                    .set("font-size", "20px");
        }      
        layout.add(labelText, valueText);
        return layout;
    }
    private Button createConfirmOrderButton() {
        Button confirmOrder = new Button("Confirm Order", new Icon(VaadinIcon.CHECK));
        confirmOrder.getStyle()
                .set("background-color", "#3f0d50")
                .set("color", "white")
                .set("width", "100%")
                .set("padding", "15px")
                .set("margin-top", "20px")
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("cursor", "pointer")
                .set("transition", "background-color 0.3s");
        confirmOrder.addClickListener(e -> {
            if (cartItems.isEmpty()) {
                Notification.show("Your cart is empty! Add some products first.", 
                                 3000, Position.MIDDLE);
                return;
            }          
            checkoutService.clearSessionData();
            Notification.show(
                "Order placed successfully! Your order is being processed.",
                5000,
                Position.TOP_CENTER
            );
            
            UI.getCurrent().access(() -> {
                try {
                    Thread.sleep(2000);
                    UI.getCurrent().navigate("home");
                } catch (InterruptedException ex) {
                    UI.getCurrent().navigate("home");
                }
            });
        });

        return confirmOrder;
    }

    private Button createContinueShoppingButton() {
        Button continueShopping = new Button("Continue Shopping", new Icon(VaadinIcon.ARROW_LEFT));
        continueShopping.getStyle()
                .set("background-color", "transparent")
                .set("color", "#3f0d50")
                .set("width", "100%")
                .set("padding", "12px")
                .set("margin-top", "10px")
                .set("border", "2px solid #3f0d50")
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("cursor", "pointer");

        continueShopping.addClickListener(e -> {
            UI.getCurrent().navigate("home");
        });

        return continueShopping;
    }
}