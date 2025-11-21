package com.example.examplefeature.ui.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class AppHeader extends HorizontalLayout {

    public AppHeader(String userType, String activePage) {
        setWidthFull();
        getStyle().set("background-color", "#3f0d50ff");
        setPadding(true);
        setSpacing(true);
        setAlignItems(FlexComponent.Alignment.CENTER);

        // Logo
        HorizontalLayout logoLayout = createLogoLayout(userType);
        
        // Navigation Menu
        HorizontalLayout navMenu = createNavigationMenu(userType, activePage);
        
        // Right Icons
        HorizontalLayout rightIcons = createRightIcons(userType);

        add(logoLayout, navMenu, rightIcons);
        expand(navMenu);
    }

    private HorizontalLayout createLogoLayout(String userType) {
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("/images/white_logo.png", "Shop Wheel");
        logo.setHeight("40px");
        
        String siteName = userType.equals("admin") ? "Shopex Admin" : "Shopex";
        H1 siteNameText = new H1(siteName);
        siteNameText.getStyle().set("margin", "0").set("color", "pink");
        
        logoLayout.add(logo, siteNameText);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        return logoLayout;
    }

    private HorizontalLayout createNavigationMenu(String userType, String activePage) {
        HorizontalLayout navMenu = new HorizontalLayout();
        navMenu.setSpacing(true);

        if (userType.equals("admin")) {
            // Admin Navigation
            Anchor home = createNavLink("Home", "admin-home", activePage);
            Anchor add = createNavLink("Add Product", "add-product", activePage);
            Anchor delete = createNavLink("Delete Product", "delete-product", activePage);
            Anchor update = createNavLink("Update Product", "update-product", activePage);
            navMenu.add(home, add, delete, update);
        } else {
            // User Navigation
            Anchor home = createNavLink("Home", "home", activePage);
            Anchor cart = createNavLink("Cart", "cart", activePage);
            Anchor about = createNavLink("About Us", "aboutUs", activePage);
            navMenu.add(home, cart, about);
        }

        navMenu.setAlignItems(Alignment.CENTER);
        navMenu.setJustifyContentMode(JustifyContentMode.CENTER);
        return navMenu;
    }

    private HorizontalLayout createRightIcons(String userType) {
        HorizontalLayout rightIcons = new HorizontalLayout();
        rightIcons.setSpacing(true);

        if (userType.equals("user")) {
            // User Icons - Cart + Logout
            Icon cartIcon = createIcon(VaadinIcon.CART, "cart");
            rightIcons.add(cartIcon);
        }

        // Logout Icon for both user and admin
        Icon logoutIcon = createIcon(VaadinIcon.SIGN_OUT, "login");
        rightIcons.add(logoutIcon);

        return rightIcons;
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

    private Icon createIcon(VaadinIcon iconType, String navigateTo) {
        Icon icon = iconType.create();
        icon.getStyle()
            .set("color", "pink")
            .set("cursor", "pointer")
            .set("transition", "color 0.3s");

        icon.getElement().addEventListener("mouseenter", e -> {
            icon.getStyle().set("color", "#ff66cc");
        });
        
        icon.getElement().addEventListener("mouseleave", e -> {
            icon.getStyle().set("color", "pink");
        });

        icon.addClickListener(e -> UI.getCurrent().navigate(navigateTo));
        return icon;
    }
}