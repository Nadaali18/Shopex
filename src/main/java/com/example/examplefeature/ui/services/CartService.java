package com.example.examplefeature.ui.services;

import com.vaadin.flow.server.VaadinSession;
import java.util.*;

public class CartService {

    public static void addProduct(String name, double price, int qty) {

        List<Map<String, Object>> cart =
                (List<Map<String, Object>>) VaadinSession.getCurrent().getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("price", price);
        item.put("qty", qty);

        cart.add(item);

        VaadinSession.getCurrent().setAttribute("cart", cart);
    }

    public static List<Map<String, Object>> getCart() {
        return (List<Map<String, Object>>) VaadinSession.getCurrent().getAttribute("cart");
    }

    public static void clearCart() {
        VaadinSession.getCurrent().setAttribute("cart", new ArrayList<>());
    }
}
