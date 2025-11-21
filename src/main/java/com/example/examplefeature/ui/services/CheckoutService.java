package com.example.examplefeature.ui.services;
import com.example.examplefeature.model.ProductData;
import com.vaadin.flow.component.UI;
import java.util.Arrays;
import java.util.List;

public class CheckoutService {

    public List<ProductData> getCartItemsFromSession() {
        try {
            @SuppressWarnings("unchecked")
            List<ProductData> sessionCartItems = (List<ProductData>) UI.getCurrent().getSession().getAttribute("cartItems");
            return sessionCartItems != null ? sessionCartItems : getDefaultCartItems();
        } catch (Exception e) {
            return getDefaultCartItems();
        }
    }

    public double getSubtotalFromSession() {
        try {
            Double sessionSubtotal = (Double) UI.getCurrent().getSession().getAttribute("subtotal");
            return sessionSubtotal != null ? sessionSubtotal : calculateSubtotal(getDefaultCartItems());
        } catch (Exception e) {
            return calculateSubtotal(getDefaultCartItems());
        }
    }

    public double getShippingCostFromSession() {
        try {
            Double sessionShippingCost = (Double) UI.getCurrent().getSession().getAttribute("shippingCost");
            return sessionShippingCost != null ? sessionShippingCost : 10;
        } catch (Exception e) {
            return 10;
        }
    }

    public double getTotalFromSession() {
        try {
            Double sessionTotal = (Double) UI.getCurrent().getSession().getAttribute("total");
            return sessionTotal != null ? sessionTotal : getSubtotalFromSession() + getShippingCostFromSession();
        } catch (Exception e) {
            return getSubtotalFromSession() + getShippingCostFromSession();
        }
    }

    private List<ProductData> getDefaultCartItems() {
        return Arrays.asList(
            new ProductData("SweetShirt", "Clothes", "$300", ""),
            new ProductData("Sneakers", "Shoes", "$150", "")
        );
    }

    private double calculateSubtotal(List<ProductData> items) {
        return items.stream()
                .mapToDouble(ProductData::getTotal)
                .sum();
    }

    public void clearSessionData() {
        UI.getCurrent().getSession().setAttribute("cartItems", null);
        UI.getCurrent().getSession().setAttribute("subtotal", null);
        UI.getCurrent().getSession().setAttribute("shippingCost", null);
        UI.getCurrent().getSession().setAttribute("total", null);
    }
}