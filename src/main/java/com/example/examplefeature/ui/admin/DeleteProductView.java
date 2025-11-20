package com.example.examplefeature.ui.admin;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route("delete-product")
public class DeleteProductView extends VerticalLayout {

    private List<UpdateProductView.Product> products;
    private Grid<UpdateProductView.Product> productGrid;

    public DeleteProductView() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(buildHeader());
        add(buildMainContent());
        add(buildFooter());
    }

    private VerticalLayout buildMainContent() {
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.setWidthFull();
        mainContent.setPadding(true);
        mainContent.setSpacing(false);
        mainContent.getStyle()
                .set("background", "linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%)")
                .set("min-height", "80vh");

        // العنوان الرئيسي
        H1 mainTitle = new H1("Delete Product");
        mainTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0")
                .set("font-size", "2.5rem")
                .set("font-weight", "bold");

        mainContent.add(mainTitle);
        mainContent.setAlignItems(Alignment.CENTER);

        // قسم المنتجات
        VerticalLayout productsSection = new VerticalLayout();
        productsSection.setWidth("80%");
        productsSection.setSpacing(true);
        productsSection.setPadding(true);
        productsSection.getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)");

        H2 sectionTitle = new H2("Select Product to Delete");
        sectionTitle.getStyle()
                .set("color", "#3f0d50")
                .set("margin", "0 0 20px 0");

        // بيانات نموذجية
        products = new ArrayList<>(Arrays.asList(
            new UpdateProductView.Product("SweetShirt", "Clothes", 300, 50),
            new UpdateProductView.Product("Sneakers", "Shoes", 150, 30),
            new UpdateProductView.Product("Smart Watch", "Electronics", 500, 20),
            new UpdateProductView.Product("Backpack", "Accessories", 80, 40),
            new UpdateProductView.Product("Water Bottle", "Home", 25, 100)
        ));

        // شبكة المنتجات
        productGrid = new Grid<>(UpdateProductView.Product.class, false);
        productGrid.addColumn(UpdateProductView.Product::getName).setHeader("Product Name").setAutoWidth(true);
        productGrid.addColumn(UpdateProductView.Product::getCategory).setHeader("Category").setAutoWidth(true);
        productGrid.addColumn(UpdateProductView.Product::getPrice).setHeader("Price").setAutoWidth(true);
        productGrid.addColumn(UpdateProductView.Product::getStock).setHeader("Stock").setAutoWidth(true);
        
        // عمود الحذف
        productGrid.addComponentColumn(product -> {
            Button deleteBtn = new Button("Delete", new Icon(VaadinIcon.TRASH));
            deleteBtn.getStyle()
                    .set("background-color", "#f44336")
                    .set("color", "white")
                    .set("padding", "5px 10px");
            
            deleteBtn.addClickListener(e -> showDeleteConfirmation(product));
            return deleteBtn;
        }).setHeader("Actions").setAutoWidth(true);

        productGrid.setItems(products);

        productsSection.add(sectionTitle, productGrid);
        mainContent.add(productsSection);

        return mainContent;
    }

    private void showDeleteConfirmation(UpdateProductView.Product product) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete Product");
        dialog.setText("Are you sure you want to delete \"" + product.getName() + "\"? This action cannot be undone.");
        
        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        
        dialog.setCancelable(true);
        dialog.setCancelText("Cancel");

        dialog.addConfirmListener(e -> {
            products.remove(product);
            productGrid.setItems(products);
            Notification.show("Product \"" + product.getName() + "\" deleted successfully!", 3000, Notification.Position.MIDDLE);
        });

        dialog.open();
    }

    // الهيدر والفوتر بنفس الكود
    private HorizontalLayout buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.getStyle().set("background-color", "#3f0d50ff");
        header.getStyle().set("color", "white");
        header.setPadding(true);
        header.setSpacing(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("/images/white_logo.png", "Shop Wheel");
        logo.setHeight("40px");
        H1 siteName = new H1("Shopex Admin");
        siteName.getStyle().set("margin", "0");
        siteName.getStyle().set("color", "pink");
        logoLayout.add(logo, siteName);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout navMenu = new HorizontalLayout();
        navMenu.setSpacing(true);
        Anchor homeLink = new Anchor("admin-home", "Home");
        Anchor addProductLink = new Anchor("add-product", "Add Product");
        Anchor deleteProductLink = new Anchor("delete-product", "Delete Product");
        Anchor updateProductLink = new Anchor("update-product", "Update Product");
        homeLink.getStyle().set("color", "white");
        addProductLink.getStyle().set("color", "white");
        deleteProductLink.getStyle().set("color", "pink").set("font-weight", "bold");
        updateProductLink.getStyle().set("color", "white");
        navMenu.add(homeLink,addProductLink,deleteProductLink,updateProductLink);
        navMenu.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        navMenu.setAlignItems(FlexComponent.Alignment.CENTER);

        HorizontalLayout rightIcons = new HorizontalLayout();
        Icon logoutIcon = VaadinIcon.SIGN_OUT.create();
        logoutIcon.getStyle().set("color", "pink").set("cursor", "pointer");
        logoutIcon.addClickListener(e -> UI.getCurrent().navigate("login"));
        rightIcons.add(logoutIcon);

        header.add(logoLayout, navMenu, rightIcons);
        header.expand(navMenu);

        return header;
    }

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