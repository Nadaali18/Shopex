package com.example.examplefeature.ui.user;

import com.example.examplefeature.model.ProductData;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("cart")
public class CartView extends VerticalLayout {

    private double shippingCost = 0;
    private double subtotal = 0;

    private Paragraph subtotalTxt;
    private Paragraph shippingTxt;
    private H3 finalTotalTxt;

    private VerticalLayout cartContainer;

    private List<ProductItem> products = new ArrayList<>();

    public CartView() {
        setWidthFull();
        setPadding(false);

        add(buildHeader());

        H2 title = new H2("My Cart");
        title.getStyle().set("margin", "20px 0 10px 40px");
        add(title);

        cartContainer = new VerticalLayout();
        cartContainer.setWidthFull();
        cartContainer.setPadding(true);
        cartContainer.getStyle().set("background-color", "white")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)")
                .set("margin", "15px");

        // Header row - عناوين الأعمدة
        HorizontalLayout headerRow = new HorizontalLayout();
        headerRow.setWidthFull();
        headerRow.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerRow.getStyle()
                .set("font-weight", "bold")
                .set("color", "#444")
                .set("padding", "15px 20px")
                .set("border-bottom", "1px solid #eee");

        Div productHeader = new Div(new Text("PRODUCT"));
        productHeader.setWidth("35%");
        productHeader.getStyle().set("text-align", "center");
        
        Div priceHeader = new Div(new Text("PRICE"));
        priceHeader.setWidth("15%");
        priceHeader.getStyle().set("text-align", "center");
        
        Div qtyHeader = new Div(new Text("QTY"));
        qtyHeader.setWidth("20%");
        qtyHeader.getStyle().set("text-align", "center");
        
        Div totalHeader = new Div(new Text("TOTAL"));
        totalHeader.setWidth("15%");
        totalHeader.getStyle().set("text-align", "center");
        
        Div actionsHeader = new Div(new Text("ACTIONS"));
        actionsHeader.setWidth("15%");
        actionsHeader.getStyle().set("text-align", "center");

        headerRow.add(productHeader, priceHeader, qtyHeader, totalHeader, actionsHeader);
        cartContainer.add(headerRow);
        add(cartContainer);

        // Checkout box
        add(buildCheckoutBox());

        // Load products from session
        loadCartProducts();

        add(buildFooter());

        // للتأكد من تحديث البيانات عند كل زيارة للصفحة
        UI.getCurrent().getPage().addBrowserWindowResizeListener(e -> {
            loadCartProducts();
        });
    }

    /* ───────────────────────────────
     *   LOAD PRODUCTS FROM SESSION
     * ─────────────────────────────── */
    private void loadCartProducts() {
        // مسح المنتجات الحالية
        products.clear();
        
        // إزالة كل الصفوف ما عدا الهيدر
        List<com.vaadin.flow.component.Component> toRemove = new ArrayList<>();
        for (com.vaadin.flow.component.Component comp : cartContainer.getChildren()
                .filter(c -> c != cartContainer.getComponentAt(0))
                .toList()) {
            toRemove.add(comp);
        }
        toRemove.forEach(cartContainer::remove);
        
        // الحصول على المنتجات من Session
        @SuppressWarnings("unchecked")
        List<ProductData> cartItems = (List<ProductData>) UI.getCurrent().getSession()
                .getAttribute("cartItems");
        
        if (cartItems != null && !cartItems.isEmpty()) {
            for (ProductData productData : cartItems) {
                // إضافة المنتج للعرض
                addProductFromData(
                    productData.getName(), 
                    productData.getNumericPrice(), 
                    productData.getCategory(), 
                    productData.getImagePath(),
                    productData.getQuantity()
                );
            }
        } else {
            // عرض رسالة إذا كانت العربة فارغة
            Div emptyCartMessage = new Div();
            emptyCartMessage.setText("Your cart is empty");
            emptyCartMessage.getStyle()
                    .set("text-align", "center")
                    .set("padding", "40px")
                    .set("color", "#666")
                    .set("font-size", "18px");
            cartContainer.add(emptyCartMessage);
        }
        
        // تحديث الحسابات من Session
        Double sessionSubtotal = (Double) UI.getCurrent().getSession().getAttribute("subtotal");
        Double sessionShippingCost = (Double) UI.getCurrent().getSession().getAttribute("shippingCost");
        
        if (sessionSubtotal != null) {
            subtotal = sessionSubtotal;
        }
        if (sessionShippingCost != null) {
            shippingCost = sessionShippingCost;
        }
        
        updateTotals();
    }

    /* ───────────────────────────────
     *   ADD PRODUCT FROM DATA
     * ─────────────────────────────── */
    private void addProductFromData(String name, int price, String category, String img, int initialQuantity) {
        ProductItem item = new ProductItem(name, price, category, img, initialQuantity);
        products.add(item);
        cartContainer.addComponentAtIndex(1, item.row);
        updateTotals();
    }

    /* ───────────────────────────────
     *   PRODUCT ITEM CLASS
     * ─────────────────────────────── */
    private class ProductItem {

        HorizontalLayout row;
        int price;
        int quantity;
        String name;
        String category;

        Paragraph totalTxt;
        Paragraph qtyText;

        ProductItem(String name, int price, String category, String imgPath, int initialQuantity) {
            this.price = price;
            this.name = name;
            this.category = category;
            this.quantity = initialQuantity;

            row = new HorizontalLayout();
            row.setWidthFull();
            row.setAlignItems(FlexComponent.Alignment.CENTER);
            row.getStyle()
                    .set("padding", "15px 20px")
                    .set("border-bottom", "1px solid #eee");

            // الصورة
            Image img = new Image(imgPath, name);
            img.setWidth("120px");
            img.setHeight("120px");
            img.getStyle()
                    .set("object-fit", "cover")
                    .set("border-radius", "8px");

            // عمود معلومات المنتج
            HorizontalLayout productInfo = new HorizontalLayout();
            productInfo.setSpacing(true);
            productInfo.setPadding(false);
            productInfo.setAlignItems(Alignment.CENTER);
            
            VerticalLayout productDetails = new VerticalLayout();
            productDetails.setSpacing(false);
            productDetails.setPadding(false);
            
            H4 productName = new H4(name);
            productName.getStyle()
                    .set("margin", "0 0 5px 0")
                    .set("font-size", "16px")
                    .set("font-weight", "bold");
            
            Paragraph categoryText = new Paragraph(category);
            categoryText.getStyle()
                    .set("margin", "0")
                    .set("color", "#666")
                    .set("font-size", "14px");

            productDetails.add(productName, categoryText);
            productInfo.add(img, productDetails);
            productInfo.setWidth("35%");

            // عمود السعر
            VerticalLayout priceCol = new VerticalLayout();
            priceCol.setSpacing(false);
            priceCol.setPadding(false);
            priceCol.setWidth("15%");
            priceCol.setAlignItems(Alignment.CENTER);
            priceCol.setJustifyContentMode(JustifyContentMode.CENTER);
            
            Paragraph priceText = new Paragraph("$" + price);
            priceText.getStyle()
                    .set("margin", "0")
                    .set("font-weight", "bold")
                    .set("font-size", "16px")
                    .set("text-align", "center");
            
            priceCol.add(priceText);

            // عمود الكمية
            VerticalLayout qtyCol = new VerticalLayout();
            qtyCol.setSpacing(false);
            qtyCol.setPadding(false);
            qtyCol.setWidth("20%");
            qtyCol.setAlignItems(Alignment.CENTER);
            qtyCol.setJustifyContentMode(JustifyContentMode.CENTER);

            HorizontalLayout qtyBox = new HorizontalLayout();
            qtyBox.setSpacing(true);
            qtyBox.setAlignItems(Alignment.CENTER);
            qtyBox.setJustifyContentMode(JustifyContentMode.CENTER);
            
            Button minus = new Button("-", e -> {
                if (quantity > 1) {
                    quantity--;
                    updateRowTotal();
                    updateSessionData();
                }
            });
            minus.getStyle()
                    .set("min-width", "35px")
                    .set("min-height", "35px")
                    .set("font-weight", "bold")
                    .set("background-color", "#f0f0f0")
                    .set("border", "1px solid #ddd");

            qtyText = new Paragraph(String.valueOf(quantity));
            qtyText.getStyle()
                    .set("margin", "0 10px")
                    .set("font-weight", "bold")
                    .set("font-size", "16px")
                    .set("text-align", "center");

            Button plus = new Button("+", e -> {
                quantity++;
                updateRowTotal();
                updateSessionData();
            });
            plus.getStyle()
                    .set("min-width", "35px")
                    .set("min-height", "35px")
                    .set("font-weight", "bold")
                    .set("background-color", "#f0f0f0")
                    .set("border", "1px solid #ddd");

            qtyBox.add(minus, qtyText, plus);
            qtyCol.add(qtyBox);

            // عمود المجموع
            VerticalLayout totalCol = new VerticalLayout();
            totalCol.setSpacing(false);
            totalCol.setPadding(false);
            totalCol.setWidth("15%");
            totalCol.setAlignItems(Alignment.CENTER);
            totalCol.setJustifyContentMode(JustifyContentMode.CENTER);
            
            totalTxt = new Paragraph("$" + (price * quantity));
            totalTxt.getStyle()
                    .set("margin", "0")
                    .set("font-weight", "bold")
                    .set("font-size", "16px")
                    .set("color", "#3f0d50")
                    .set("text-align", "center");
            
            totalCol.add(totalTxt);

            // زر الحذف
            VerticalLayout actionsCol = new VerticalLayout();
            actionsCol.setSpacing(false);
            actionsCol.setPadding(false);
            actionsCol.setWidth("15%");
            actionsCol.setAlignItems(Alignment.CENTER);
            actionsCol.setJustifyContentMode(JustifyContentMode.CENTER);
            
            Button delete = new Button(new Icon(VaadinIcon.TRASH), e -> {
                cartContainer.remove(row);
                products.remove(this);
                removeFromSession();
                updateTotals();
            });
            delete.getStyle()
                    .set("color", "red")
                    .set("background", "none")
                    .set("border", "none")
                    .set("cursor", "pointer");
            
            actionsCol.add(delete);

            row.add(productInfo, priceCol, qtyCol, totalCol, actionsCol);
            row.setFlexGrow(1, productInfo);
        }

        void updateRowTotal() {
            int result = price * quantity;
            totalTxt.setText("$" + result);
            qtyText.setText(String.valueOf(quantity));
            updateTotals();
        }

        void updateSessionData() {
            @SuppressWarnings("unchecked")
            List<ProductData> cartItems = (List<ProductData>) UI.getCurrent().getSession()
                    .getAttribute("cartItems");
            
            if (cartItems != null) {
                for (ProductData item : cartItems) {
                    if (item.getName().equals(name)) {
                        item.setQuantity(quantity);
                        break;
                    }
                }
                UI.getCurrent().getSession().setAttribute("cartItems", cartItems);
                
                // تحديث الـ subtotal
                double newSubtotal = cartItems.stream()
                        .mapToDouble(ProductData::getTotal)
                        .sum();
                UI.getCurrent().getSession().setAttribute("subtotal", newSubtotal);
            }
        }

        void removeFromSession() {
            @SuppressWarnings("unchecked")
            List<ProductData> cartItems = (List<ProductData>) UI.getCurrent().getSession()
                    .getAttribute("cartItems");
            
            if (cartItems != null) {
                cartItems.removeIf(item -> item.getName().equals(name));
                UI.getCurrent().getSession().setAttribute("cartItems", cartItems);
                
                // تحديث الـ subtotal
                double newSubtotal = cartItems.stream()
                        .mapToDouble(ProductData::getTotal)
                        .sum();
                UI.getCurrent().getSession().setAttribute("subtotal", newSubtotal);
            }
        }

        int getTotal() {
            return price * quantity;
        }
    }

    /* ───────────────────────────────
     *   UPDATE ALL TOTALS
     * ─────────────────────────────── */
    private void updateTotals() {
        subtotal = products.stream().mapToInt(ProductItem::getTotal).sum();
        if (subtotalTxt != null) {
            subtotalTxt.setText("Subtotal: $" + subtotal);
            shippingTxt.setText("Shipping: $" + shippingCost);
            finalTotalTxt.setText("Total: $" + (subtotal + shippingCost));
        }
    }

    /* ───────────────────────────────
     *   CHECKOUT BOX
     * ─────────────────────────────── */
    private HorizontalLayout buildCheckoutBox() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.setWidthFull();
        layout.setPadding(true);
        layout.setSpacing(true);

        // Left — shipping method
        VerticalLayout left = new VerticalLayout();
        left.setWidth("70%");
        left.getStyle()
                .set("background-color", "#f5f5f5")
                .set("border-radius", "10px")
                .set("padding", "20px");

        H3 t = new H3("Choose shipping mode:");
        t.getStyle().set("margin-top", "0").set("text-align", "center");

        VerticalLayout radioContainer = new VerticalLayout();
        radioContainer.setSpacing(true);
        radioContainer.setPadding(false);
        radioContainer.setAlignItems(Alignment.CENTER);
        
        RadioButtonGroup<String> options = new RadioButtonGroup<>();
        options.setItems("Free Pickup", "Delivery ($10)");
        options.setValue("Free Pickup");

        options.addValueChangeListener(v -> {
            if (v.getValue().equals("Delivery ($10)")) shippingCost = 10;
            else shippingCost = 0;
            updateTotals();
        });

        options.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center");
        
        radioContainer.add(options);
        left.add(t, radioContainer);
        left.setAlignItems(Alignment.CENTER);

        // Right — summary
        VerticalLayout summary = new VerticalLayout();
        summary.setWidth("30%");
        summary.getStyle()
                .set("background-color", "white")
                .set("border-radius", "10px")
                .set("padding", "20px")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");

        subtotalTxt = new Paragraph("Subtotal: $0");
        subtotalTxt.getStyle()
                .set("margin", "10px 0")
                .set("text-align", "center");
        
        shippingTxt = new Paragraph("Shipping: $0");
        shippingTxt.getStyle()
                .set("margin", "10px 0")
                .set("text-align", "center");
        
        finalTotalTxt = new H3("Total: $0");
        finalTotalTxt.getStyle()
                .set("color", "#ff66cc")
                .set("margin", "20px 0 10px 0")
                .set("text-align", "center");

        Button checkout = new Button("Checkout");
        checkout.getStyle()
                .set("background-color", "#3f0d50")
                .set("color", "white")
                .set("width", "100%")
                .set("padding", "15px")
                .set("margin-top", "10px")
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("cursor", "pointer");

        checkout.addClickListener(e -> {
            if (products.isEmpty()) {
                Notification.show("Your cart is empty! Add some products first.", 
                                 3000, Notification.Position.MIDDLE);
                return;
            }
            
            // حفظ البيانات في Session
            List<ProductData> cartData = new ArrayList<>();
            for (ProductItem product : products) {
                ProductData productData = new ProductData(
                    product.name,
                    product.category,
                    "$" + product.price,
                    "" // image path
                );
                productData.setQuantity(product.quantity);
                cartData.add(productData);
            }
            
            // حفظ في Session
            UI.getCurrent().getSession().setAttribute("cartItems", cartData);
            UI.getCurrent().getSession().setAttribute("subtotal", subtotal);
            UI.getCurrent().getSession().setAttribute("shippingCost", shippingCost);
            UI.getCurrent().getSession().setAttribute("total", subtotal + shippingCost);
            
            // الانتقال لصفحة الشيك اوت
            UI.getCurrent().navigate("checkout");
        });

        // زر متابعة التسوق
        Button continueShopping = new Button("Continue Shopping");
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

        summary.add(subtotalTxt, shippingTxt, finalTotalTxt, checkout, continueShopping);
        summary.setSpacing(false);
        summary.setAlignItems(Alignment.CENTER);

        layout.add(left, summary);
        return layout;
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

        // Logo + site name
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("/images/white_logo.png", "Shop Wheel");
        logo.setHeight("40px");
        H1 siteName = new H1("Shopex");
        siteName.getStyle().set("margin", "0");
        siteName.getStyle().set("color", "pink");
        logoLayout.add(logo, siteName);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Navigation menu
        HorizontalLayout navMenu = new HorizontalLayout();
        navMenu.setSpacing(true);
        Anchor homeLink = new Anchor("home", "Home");
        Anchor cartLink = new Anchor("cart", "Cart");
        Anchor aboutLink = new Anchor("aboutUs", "About Us");
        homeLink.getStyle().set("color", "white");
        cartLink.getStyle().set("color", "pink").set("font-weight", "bold");
        aboutLink.getStyle().set("color", "white");
        homeLink.getStyle().set("margin-right", "40px");
        cartLink.getStyle().set("margin-right", "40px");
        navMenu.add(homeLink, cartLink, aboutLink);
        navMenu.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        navMenu.setAlignItems(FlexComponent.Alignment.CENTER);

        // Right icons
        HorizontalLayout rightIcons = new HorizontalLayout();

        Icon cartIcon = VaadinIcon.CART.create();
        cartIcon.getStyle()
            .set("color", "pink")
            .set("cursor", "pointer")
            .set("transition", "color 0.3s");

        cartIcon.getElement().addEventListener("mouseenter", e -> {
            cartIcon.getStyle().set("color", "#ff66cc");
        });
        cartIcon.getElement().addEventListener("mouseleave", e -> {
            cartIcon.getStyle().set("color", "pink");
        });

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

        cartIcon.addClickListener(e -> {
            UI.getCurrent().navigate("cart");
        });

        rightIcons.add(cartIcon, logoutIcon);
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

        H3 shopName = new H3("Shopex");
        shopName.getStyle().set("color", "pink").set("margin", "0");

        Paragraph copyright = new Paragraph("© 2024 Shopex. All rights reserved.");
        copyright.getStyle().set("color", "#ccc").set("font-size", "14px");

        Paragraph contact = new Paragraph("Contact us: info@shopex.com | +20 100 123 4567");
        contact.getStyle().set("color", "#ccc").set("font-size", "14px");

        footer.add(shopName, copyright, contact);
        footer.setAlignItems(Alignment.CENTER);

        return footer;
    }
}