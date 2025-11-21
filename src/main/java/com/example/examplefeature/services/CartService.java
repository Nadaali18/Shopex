package com.example.examplefeature.services;
import com.vaadin.flow.component.UI;
import java.util.*;

public class CartService {
    private static final String CART_SESSION_KEY = "cartItems";

    private static Map<String, Object> createItem(
            String name, double price, int qty, String image) {

        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("price", price);
        item.put("qty", qty);
        item.put("image", image);
        return item;
    }

    public static List<Map<String, Object>> getCart() {
        List<Map<String, Object>> cart =
                (List<Map<String, Object>>) UI.getCurrent().getSession().getAttribute(CART_SESSION_KEY);

        if (cart == null) {
            cart = new ArrayList<>();
            UI.getCurrent().getSession().setAttribute(CART_SESSION_KEY, cart);
        }

        return cart;
    }

    public static void addProduct(String name, double price, String image) {

        List<Map<String, Object>> cart = getCart();
        boolean exists = false;

        for (Map<String, Object> item : cart) {
            if (item.get("name").equals(name)) {
                int oldQty = (int) item.get("qty");
                item.put("qty", oldQty + 1);
                exists = true;
                break;
            }
        }

        if (!exists) {
            cart.add(createItem(name, price, 1, image));
        }

        UI.getCurrent().getSession().setAttribute(CART_SESSION_KEY, cart);
    }

    public static void removeProduct(String name) {
        List<Map<String, Object>> cart = getCart();
        cart.removeIf(item -> item.get("name").equals(name));
        UI.getCurrent().getSession().setAttribute(CART_SESSION_KEY, cart);
    }

    public static void updateQty(String name, int qty) {
        List<Map<String, Object>> cart = getCart();

        for (Map<String, Object> item : cart) {
            if (item.get("name").equals(name)) {
                item.put("qty", qty);
                break;
            }
        }

        UI.getCurrent().getSession().setAttribute(CART_SESSION_KEY, cart);
    }

    public static void clearCart() {
        UI.getCurrent().getSession().setAttribute(CART_SESSION_KEY, new ArrayList<>());
    }

    public static double calculateSubtotal() {
        List<Map<String, Object>> cart = getCart();

        return cart.stream()
                .mapToDouble(item ->
                        (double) item.get("price") * (int) item.get("qty"))
                .sum();
    }

    public static int getItemCount() {
        return getCart().stream()
                .mapToInt(item -> (int) item.get("qty"))
                .sum();
    }
}
