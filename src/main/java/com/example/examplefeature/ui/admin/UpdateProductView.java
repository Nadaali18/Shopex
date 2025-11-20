package com.example.examplefeature.ui.admin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route("update-product")
public class UpdateProductView extends VerticalLayout {

    private TextField productName;
    private ComboBox<String> category;
    private NumberField price;
    private NumberField stock;
    private TextArea description;
    private Button updateButton;
    private VerticalLayout formContainer;
    private Grid<Product> productGrid;
    private List<Product> products;
    private Product selectedProduct;

    public UpdateProductView() {
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
        H1 mainTitle = new H1("Update Product");
        mainTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0")
                .set("font-size", "2.5rem")
                .set("font-weight", "bold");

        mainContent.add(mainTitle);
        mainContent.setAlignItems(Alignment.CENTER);

        // قسم اختيار المنتج
        VerticalLayout selectionSection = new VerticalLayout();
        selectionSection.setWidth("80%");
        selectionSection.setSpacing(true);
        selectionSection.setPadding(true);
        selectionSection.getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("margin-bottom", "30px");

        H2 selectionTitle = new H2("Select Product to Update");
        selectionTitle.getStyle()
                .set("color", "#3f0d50")
                .set("margin", "0 0 20px 0");

        // بيانات نموذجية
        products = new ArrayList<>(Arrays.asList(
            new Product("SweetShirt", "Clothes", 300, 50),
            new Product("Sneakers", "Shoes", 150, 30),
            new Product("Smart Watch", "Electronics", 500, 20)
        ));

        // شبكة المنتجات
        productGrid = new Grid<>(Product.class, false);
        productGrid.addColumn(Product::getName).setHeader("Product Name").setAutoWidth(true);
        productGrid.addColumn(Product::getCategory).setHeader("Category").setAutoWidth(true);
        productGrid.addColumn(Product::getPrice).setHeader("Price").setAutoWidth(true);
        productGrid.addColumn(Product::getStock).setHeader("Stock").setAutoWidth(true);

        productGrid.setItems(products);

        // التحديث الصحيح لـ SelectionEvent
        productGrid.addSelectionListener(selection -> {
            if (!selection.getAllSelectedItems().isEmpty()) {
                selectedProduct = selection.getAllSelectedItems().iterator().next();
                loadProductData(selectedProduct);
            } else {
                // إذا ماكانش في منتج مختار، اخفي النموذج
                formContainer.setVisible(false);
            }
        });

        selectionSection.add(selectionTitle, productGrid);
        mainContent.add(selectionSection);

        // نموذج التحديث
        formContainer = buildUpdateForm();
        mainContent.add(formContainer);

        return mainContent;
    }

    private VerticalLayout buildUpdateForm() {
        VerticalLayout formContainer = new VerticalLayout();
        formContainer.setWidth("60%");
        formContainer.setSpacing(true);
        formContainer.setPadding(true);
        formContainer.getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("border", "2px solid #e0e0e0");

        H2 formTitle = new H2("Update Product Details");
        formTitle.getStyle()
                .set("color", "#3f0d50")
                .set("margin", "0 0 20px 0");

        // صفوف النموذج
        HorizontalLayout row1 = new HorizontalLayout();
        row1.setWidthFull();
        row1.setSpacing(true);

        productName = new TextField("Product Name");
        productName.setWidth("50%");

        category = new ComboBox<>("Category");
        category.setItems("Clothes", "Shoes", "Electronics", "Accessories", "Home");
        category.setWidth("50%");

        row1.add(productName, category);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setSpacing(true);

        price = new NumberField("Price");
        price.setWidth("50%");
        price.setPrefixComponent(new Div(new Text("$")));

        stock = new NumberField("Stock Quantity");
        stock.setWidth("50%");

        row2.add(price, stock);

        // وصف المنتج
        description = new TextArea("Description");
        description.setWidthFull();
        description.setHeight("120px");

        // زر التحديث
        updateButton = new Button("Update Product", new Icon(VaadinIcon.EDIT));
        updateButton.getStyle()
                .set("background-color", "#2196F3")
                .set("color", "white")
                .set("width", "200px")
                .set("padding", "15px")
                .set("margin-top", "20px");

        updateButton.addClickListener(e -> {
            if (validateForm()) {
                updateProductInList();
                Notification.show("Product updated successfully!", 3000, Notification.Position.MIDDLE);
            }
        });

        // إخفاء النموذج في البداية
        formContainer.setVisible(false);
        formContainer.add(formTitle, row1, row2, description, updateButton);

        return formContainer;
    }

    private void loadProductData(Product product) {
        productName.setValue(product.getName());
        category.setValue(product.getCategory());
        price.setValue((double) product.getPrice());
        stock.setValue((double) product.getStock());
        description.setValue("This is a description for " + product.getName() + ". High quality product with excellent features.");
        
        // إظهار النموذج
        formContainer.setVisible(true);
    }

    private void updateProductInList() {
        if (selectedProduct != null) {
            // تحديث بيانات المنتج في القائمة
            selectedProduct.setName(productName.getValue());
            selectedProduct.setCategory(category.getValue());
            selectedProduct.setPrice(price.getValue().intValue());
            selectedProduct.setStock(stock.getValue().intValue());
            
            // تحديث الـ Grid
            productGrid.getDataProvider().refreshAll();
            
            // إعادة تحميل الـ Grid
            productGrid.setItems(products);
            
            Notification.show("Product updated in the list!", 2000, Notification.Position.MIDDLE);
        }
    }

    private boolean validateForm() {
        if (productName.getValue().isEmpty()) {
            Notification.show("Please enter product name", 3000, Notification.Position.MIDDLE);
            return false;
        }
        if (category.getValue() == null) {
            Notification.show("Please select category", 3000, Notification.Position.MIDDLE);
            return false;
        }
        if (price.getValue() == null || price.getValue() <= 0) {
            Notification.show("Please enter valid price", 3000, Notification.Position.MIDDLE);
            return false;
        }
        if (stock.getValue() == null || stock.getValue() < 0) {
            Notification.show("Please enter valid stock quantity", 3000, Notification.Position.MIDDLE);
            return false;
        }
        return true;
    }

    // كلاس Product مع setters
    public static class Product {
        private String name;
        private String category;
        private int price;
        private int stock;

        public Product(String name, String category, int price, int stock) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.stock = stock;
        }

        public String getName() { return name; }
        public String getCategory() { return category; }
        public int getPrice() { return price; }
        public int getStock() { return stock; }

        public void setName(String name) { this.name = name; }
        public void setCategory(String category) { this.category = category; }
        public void setPrice(int price) { this.price = price; }
        public void setStock(int stock) { this.stock = stock; }
    }

    /* ───────────────────────────────
     *   HEADER
     * ─────────────────────────────── */
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
        deleteProductLink.getStyle().set("color", "white");
        updateProductLink.getStyle().set("color", "pink").set("font-weight", "bold");
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