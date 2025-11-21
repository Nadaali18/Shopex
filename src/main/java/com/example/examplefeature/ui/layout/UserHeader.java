package com.example.examplefeature.ui.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class UserHeader extends HorizontalLayout {

    public UserHeader(String activePage) {

        setWidthFull();
        getStyle().set("background-color", "#3f0d50ff");
        setPadding(true);
        setSpacing(true);
        setAlignItems(FlexComponent.Alignment.CENTER);

        // Logo
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("/images/white_logo.png", "Shop Wheel");
        logo.setHeight("40px");
        H1 siteName = new H1("Shopex");
        siteName.getStyle().set("margin", "0").set("color", "pink");
        logoLayout.add(logo, siteName);

        // ---------- Navigation ----------
        HorizontalLayout navMenu = new HorizontalLayout();
        navMenu.setSpacing(true);

        Anchor home = createNavLink("Home", "home", activePage);
        Anchor cart = createNavLink("Cart", "cart", activePage);
        Anchor about = createNavLink("About Us", "aboutUs", activePage);

        navMenu.add(home, cart, about);
        navMenu.setAlignItems(Alignment.CENTER);
        navMenu.setJustifyContentMode(JustifyContentMode.CENTER);

        // ---------- Right Icons ----------
        HorizontalLayout rightIcons = new HorizontalLayout();

        Icon cartIcon = VaadinIcon.CART.create();
        cartIcon.getStyle().set("color", "pink").set("cursor", "pointer");
        cartIcon.addClickListener(e -> UI.getCurrent().navigate("cart"));

        Icon logoutIcon = VaadinIcon.SIGN_OUT.create();
        logoutIcon.getStyle().set("color", "pink").set("cursor", "pointer");
        logoutIcon.addClickListener(e -> UI.getCurrent().navigate("login"));

        rightIcons.add(cartIcon, logoutIcon);

        add(logoLayout, navMenu, rightIcons);
        expand(navMenu);
    }

    private Anchor createNavLink(String name, String route, String activePage) {
        Anchor link = new Anchor(route, name);
        link.getStyle().set("font-size", "16px");

        if (activePage.equalsIgnoreCase(route)) {
            link.getStyle()
                .set("color", "pink")
                .set("font-weight", "bold");
        } else {
            link.getStyle().set("color", "white");
        }

        return link;
    }
}
