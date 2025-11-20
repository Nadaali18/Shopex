package com.example.examplefeature.ui.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

@Route("admin-home")
public class AdminHomeView extends VerticalLayout {

    public AdminHomeView() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(buildHeader());
        add(buildHeroSection());
        add(buildAdminActions());
        add(buildFooter());
    }

    private VerticalLayout buildHeroSection() {
        VerticalLayout heroSection = new VerticalLayout();
        heroSection.setWidthFull();
        heroSection.setHeight("400px");
        heroSection.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        heroSection.setAlignItems(FlexComponent.Alignment.CENTER);
        heroSection.getStyle()
                .set("background", "linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('/images/admin.jpg')")
                .set("background-size", "cover")
                .set("background-position", "center")
                .set("color", "white")
                .set("position", "relative");

        // النص في وسط الصورة
        H1 heroTitle = new H1("Admin Dashboard");
        heroTitle.getStyle()
                .set("font-size", "3.5rem")
                .set("font-weight", "bold")
                .set("text-align", "center")
                .set("margin", "0")
                .set("text-shadow", "2px 2px 8px rgba(255, 255, 255, 0.8)");

        Paragraph heroSubtitle = new Paragraph("Manage Your E-commerce Store Efficiently");
        heroSubtitle.getStyle()
                .set("font-size", "1.5rem")
                .set("text-align", "center")
                .set("margin", "20px 0 0 0")
                .set("text-shadow", "2px 2px 6px rgba(0,0,0,0.6)");

        heroSection.add(heroTitle, heroSubtitle);
        return heroSection;
    }

    private VerticalLayout buildAdminActions() {
        VerticalLayout actionsLayout = new VerticalLayout();
        actionsLayout.setWidthFull();
        actionsLayout.setPadding(true);
        actionsLayout.setSpacing(true);
        actionsLayout.getStyle()
                .set("background", "linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%)")
                .set("min-height", "60vh");

        // العنوان
        H2 sectionTitle = new H2("Product Management");
        sectionTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0")
                .set("font-size", "2.5rem")
                .set("font-weight", "bold");

        actionsLayout.add(sectionTitle);
        actionsLayout.setAlignItems(Alignment.CENTER);

        // المربعات الثلاثة مع background images
        VerticalLayout addProductBox = createActionBox(
            "Add Product", 
            "/images/add.jpg",
            "Add new products to your store inventory"
        );

        VerticalLayout deleteProductBox = createActionBox(
            "Delete Product", 
            "/images/delete.jpg",
            "Remove products from your store inventory"
        );

        VerticalLayout updateProductBox = createActionBox(
            "Update Product", 
            "/images/update.jpg",
            "Modify existing product information and details"
        );

        actionsLayout.add(addProductBox, deleteProductBox, updateProductBox);
        return actionsLayout;
    }

    private VerticalLayout createActionBox(String title, String backgroundImage, String description) {
        VerticalLayout box = new VerticalLayout();
        box.setWidth("800px");
        box.setHeight("450px");
        box.setSpacing(false);
        box.setPadding(true);
        box.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        box.setAlignItems(Alignment.CENTER);
        box.getStyle()
                .set("background", "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('" + backgroundImage + "')")
                .set("background-size", "cover")
                .set("background-position", "center")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.3)")
                .set("border", "2px solid rgba(255,255,255,0.2)")
                .set("transition", "all 0.3s ease")
                .set("cursor", "pointer")
                .set("margin", "15px")
                .set("position", "relative")
                .set("overflow", "hidden");

        // تأثير Hover - يتحرك للأعلى
        box.getElement().addEventListener("mouseenter", e -> {
            box.getStyle()
                    .set("transform", "translateY(-10px)")
                    .set("box-shadow", "0 15px 35px rgba(0,0,0,0.4)")
                    .set("background", "linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('" + backgroundImage + "')");
        });

        box.getElement().addEventListener("mouseleave", e -> {
            box.getStyle()
                    .set("transform", "translateY(0)")
                    .set("box-shadow", "0 8px 25px rgba(0,0,0,0.3)")
                    .set("background", "linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('" + backgroundImage + "')");
        });

        // الأيقونة - باللون الأبيض
        
        // العنوان - باللون الأبيض
        H3 actionTitle = new H3(title);
        actionTitle.getStyle()
                .set("color", "white")
                .set("margin", "0 0 10px 0")
                .set("font-size", "2rem")
                .set("font-weight", "bold")
                .set("text-align", "center")
                .set("text-shadow", "2px 2px 6px rgba(0,0,0,0.8)");

        // الوصف - باللون الأبيض
        Paragraph actionDescription = new Paragraph(description);
        actionDescription.getStyle()
                .set("color", "rgba(255,255,255,0.9)")
                .set("text-align", "center")
                .set("margin", "0")
                .set("line-height", "1.4")
                .set("font-size", "16px")
                .set("text-shadow", "1px 1px 3px rgba(0,0,0,0.6)")
                .set("max-width", "350px");

        // إضافة click listener (يمكنك تعديله حسب احتياجك)
        box.addClickListener(e -> {
            // هنا يمكنك إضافة التنقل للصفحات المناسبة
            switch (title) {
                case "Add Product":
                    UI.getCurrent().navigate("add-product");
                    break;
                case "Delete Product":
                    UI.getCurrent().navigate("delete-product");
                    break;
                case "Update Product":
                    UI.getCurrent().navigate("update-product");
                    break;
            }
        });

        box.add( actionTitle, actionDescription);
        return box;
    }

    /* ───────────────────────────────
     *   HEADER - معدل للإدمن
     * ─────────────────────────────── */
    private HorizontalLayout buildHeader() {

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.getStyle().set("background-color", "#3f0d50ff");
        header.getStyle().set("color", "white");
        header.setPadding(true);
        header.setSpacing(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        // Logo + site name
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("/images/white_logo.png", "Shop Wheel");
        logo.setHeight("40px");
        H1 siteName = new H1("Shopex Admin");
        siteName.getStyle().set("margin", "0");
        siteName.getStyle().set("color", "pink");
        logoLayout.add(logo, siteName);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Navigation menu 
       HorizontalLayout navMenu = new HorizontalLayout();
        navMenu.setSpacing(true);
        Anchor homeLink = new Anchor("admin-home", "Home");
        Anchor addLink = new Anchor("add-product", "Add Product");
        Anchor deleteLink = new Anchor("delete-product", "Delete Product");
        Anchor updateLink = new Anchor("update-product", "Update Product");
        homeLink.getStyle().set("color", "pink").set("font-weight", "bold");
        addLink.getStyle().set("color", "white");
        deleteLink.getStyle().set("color", "white");
        updateLink.getStyle().set("color", "white");
        navMenu.add(homeLink);
        navMenu.add(addLink);
        navMenu.add(deleteLink);
        navMenu.add(updateLink);
        navMenu.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        navMenu.setAlignItems(FlexComponent.Alignment.CENTER);


        // Right icons - بس Logout
        HorizontalLayout rightIcons = new HorizontalLayout();

        Icon logoutIcon = VaadinIcon.SIGN_OUT.create();
        logoutIcon.getStyle()
            .set("color", "pink")
            .set("cursor", "pointer")
            .set("transition", "color 0.3s");

        logoutIcon.getElement().addEventListener("mouseenter", e -> {
            logoutIcon.getStyle().set("color", "#ff66cc");
        });
        logoutIcon.getElement().addEventListener("mouseleave", e -> {
            logoutIcon.getStyle().set("color", "pink");
        });

        logoutIcon.addClickListener(e -> {
            UI.getCurrent().navigate("login");
        });

        rightIcons.add(logoutIcon);
        rightIcons.setSpacing(true);

        header.add(logoLayout, navMenu, rightIcons);
        header.expand(navMenu);

        return header;
    }

    /* ───────────────────────────────
     *   FOOTER
     * ─────────────────────────────── */
    private VerticalLayout buildFooter() {

        VerticalLayout footer = new VerticalLayout();
        footer.setWidthFull();
        footer.setPadding(true);
        footer.getStyle()
                .set("background-color", "#2a0a3f")
                .set("color", "white")
                .set("text-align", "center")
                .set("margin-top", "50px");

        H3 shopName = new H3("Shopex Admin Panel");
        shopName.getStyle().set("color", "pink").set("margin", "0");

        Paragraph copyright = new Paragraph("© 2024 Shopex Admin. All rights reserved.");
        copyright.getStyle().set("color", "#ccc").set("font-size", "14px");

        Paragraph contact = new Paragraph("Admin Support: admin@shopex.com | +20 100 123 4567");
        contact.getStyle().set("color", "#ccc").set("font-size", "14px");

        footer.add(shopName, copyright, contact);
        footer.setAlignItems(Alignment.CENTER);

        return footer;
    }
}