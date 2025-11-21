package com.example.examplefeature.ui.user.check_out.view;

import com.example.examplefeature.model.ProductData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

import java.util.*;

@Route("checkout")
public class CheckoutView extends VerticalLayout {

    private double subtotal = 0;
    private double shippingCost = 0;
    private double total = 0;
    private List<ProductData> cartItems = new ArrayList<>();
    private Div screenshotPreview;

    public CheckoutView() {
        // جلب البيانات من Session
        loadCartDataFromSession();
        setupUI();
    }

    private void loadCartDataFromSession() {
        // محاولة جلب البيانات من Session
        try {
            @SuppressWarnings("unchecked")
            List<ProductData> sessionCartItems = (List<ProductData>) UI.getCurrent().getSession().getAttribute("cartItems");
            Double sessionSubtotal = (Double) UI.getCurrent().getSession().getAttribute("subtotal");
            Double sessionShippingCost = (Double) UI.getCurrent().getSession().getAttribute("shippingCost");
            Double sessionTotal = (Double) UI.getCurrent().getSession().getAttribute("total");
            
            if (sessionCartItems != null && !sessionCartItems.isEmpty()) {
                this.cartItems = sessionCartItems;
                this.subtotal = sessionSubtotal != null ? sessionSubtotal : calculateSubtotal();
                this.shippingCost = sessionShippingCost != null ? sessionShippingCost : 0;
                this.total = sessionTotal != null ? sessionTotal : this.subtotal + this.shippingCost;
            } else {
                // إذا مفيش بيانات في Session، نستخدم بيانات افتراضية
                initializeWithDefaultData();
            }
        } catch (Exception e) {
            // إذا حصل أي خطأ، نستخدم بيانات افتراضية
            initializeWithDefaultData();
        }
    }

    private double calculateSubtotal() {
        return cartItems.stream()
                .mapToDouble(ProductData::getTotal)
                .sum();
    }

    private void initializeWithDefaultData() {
        // قيم افتراضية إذا ماكانش في بيانات
        this.cartItems = Arrays.asList(
            new ProductData("SweetShirt", "Clothes", "$300", ""),
            new ProductData("Sneakers", "Shoes", "$150", "")
        );
        this.subtotal = calculateSubtotal();
        this.shippingCost = 10;
        this.total = subtotal + shippingCost;
    }

    private void setupUI() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(buildHeader());

        // العنوان الرئيسي
        H1 pageTitle = new H1("Checkout");
        pageTitle.getStyle()
                .set("text-align", "center")
                .set("margin", "40px 0 20px 0")
                .set("color", "#3f0d50");
        add(pageTitle);

        // المحتوى الرئيسي
        HorizontalLayout mainContent = new HorizontalLayout();
        mainContent.setWidthFull();
        mainContent.setPadding(true);
        mainContent.setSpacing(true);

        // الجزء الأيسر - معلومات الدفع
        VerticalLayout paymentSection = buildPaymentSection();
        paymentSection.setWidth("60%");

        // الجزء الأيمن - ملخص الطلب
        VerticalLayout orderSummary = buildOrderSummary();
        orderSummary.setWidth("40%");

        mainContent.add(paymentSection, orderSummary);
        add(mainContent);

        add(buildFooter());
    }

    /* ───────────────────────────────
     *   PAYMENT SECTION
     * ─────────────────────────────── */
    private VerticalLayout buildPaymentSection() {
        VerticalLayout section = new VerticalLayout();
        section.setPadding(true);
        section.getStyle()
                .set("background-color", "white")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");

        H2 sectionTitle = new H2("Payment Method");
        sectionTitle.getStyle()
                .set("margin-top", "0")
                .set("color", "#3f0d50");

        // خيارات الدفع
        RadioButtonGroup<String> paymentOptions = new RadioButtonGroup<>();
        paymentOptions.setItems("Cash on Delivery", "E-Wallet", "Credit Card");
        paymentOptions.setValue("Cash on Delivery");

        // حاوية خيارات الدفع
        VerticalLayout paymentContainer = new VerticalLayout();
        paymentContainer.setSpacing(true);
        paymentContainer.setPadding(false);

        paymentOptions.addValueChangeListener(event -> {
            paymentContainer.removeAll();
            paymentContainer.add(paymentOptions);
            
            switch (event.getValue()) {
                case "E-Wallet":
                    paymentContainer.add(buildEWalletSection());
                    break;
                case "Credit Card":
                    paymentContainer.add(buildCreditCardSection());
                    break;
                case "Cash on Delivery":
                    paymentContainer.add(buildCashOnDeliverySection());
                    break;
            }
        });

        // الإضافة الأولية
        paymentContainer.add(paymentOptions, buildCashOnDeliverySection());

        section.add(sectionTitle, paymentContainer);
        return section;
    }

    /* ───────────────────────────────
     *   E-WALLET SECTION
     * ─────────────────────────────── */
    private VerticalLayout buildEWalletSection() {
        VerticalLayout ewalletSection = new VerticalLayout();
        ewalletSection.setSpacing(true);
        ewalletSection.setPadding(true);
        ewalletSection.getStyle()
                .set("background-color", "#f5f5f5")
                .set("border-radius", "8px")
                .set("border", "1px solid #ddd");

        H3 title = new H3("E-Wallet Payment");
        title.getStyle().set("margin-top", "0");

        // اختيار نوع المحفظة الإلكترونية
        ComboBox<String> walletType = new ComboBox<>("Select E-Wallet");
        walletType.setItems("Vodafone Cash", "Orange Money", "Etisalat Cash", "WePay");
        walletType.setPlaceholder("Choose your e-wallet");
        walletType.setWidthFull();

        // رقم المحفظة
        Paragraph walletNumber = new Paragraph("Account Number: 0100 123 4567");
        walletNumber.getStyle()
                .set("font-weight", "bold")
                .set("color", "#3f0d50")
                .set("margin", "10px 0")
                .set("text-align", "center");

        // رسالة التأكيد
        Paragraph confirmationMsg = new Paragraph("The number you will transfer to");
        confirmationMsg.getStyle()
                .set("color", "#666")
                .set("font-size", "14px")
                .set("margin", "5px 0")
                .set("text-align", "center");

        // قسم رفع screenshot
        VerticalLayout uploadSection = new VerticalLayout();
        uploadSection.setSpacing(true);
        uploadSection.setPadding(false);

        // معاينة الصورة
        screenshotPreview = new Div();
        screenshotPreview.setWidth("100%");
        screenshotPreview.setHeight("200px");
        screenshotPreview.getStyle()
                .set("border", "2px dashed #ccc")
                .set("border-radius", "8px")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background-color", "#fafafa");

        Paragraph previewText = new Paragraph("Screenshot preview will appear here");
        previewText.getStyle()
                .set("color", "#999")
                .set("text-align", "center");
        screenshotPreview.add(previewText);

        // رفع الصورة
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/jpg");
        upload.setDropLabel(new Span("Drag and drop payment screenshot here"));
        upload.setUploadButton(new Button("Select Screenshot"));
        
        upload.addSucceededListener(event -> {
            Notification.show("Screenshot uploaded successfully!", 3000, Position.MIDDLE);
            previewText.setText("Screenshot uploaded: " + event.getFileName());
            previewText.getStyle().set("color", "#3f0d50");
            screenshotPreview.getStyle().set("border-color", "#3f0d50");
        });

        upload.addFileRejectedListener(event -> {
            Notification.show("Error: " + event.getErrorMessage(), 3000, Position.MIDDLE);
        });

        uploadSection.add(
            new Paragraph("Please upload a screenshot of your payment confirmation:"),
            screenshotPreview,
            upload
        );

        ewalletSection.add(title, walletType, confirmationMsg, walletNumber, uploadSection);
        return ewalletSection;
    }

    /* ───────────────────────────────
     *   CREDIT CARD SECTION
     * ─────────────────────────────── */
    private VerticalLayout buildCreditCardSection() {
        VerticalLayout cardSection = new VerticalLayout();
        cardSection.setSpacing(true);
        cardSection.setPadding(true);
        cardSection.getStyle()
                .set("background-color", "#f5f5f5")
                .set("border-radius", "8px")
                .set("border", "1px solid #ddd");

        H3 title = new H3("Credit Card Payment");
        title.getStyle().set("margin-top", "0");

        // حقول بيانات البطاقة
        TextField cardNumber = new TextField("Card Number");
        cardNumber.setPlaceholder("1234 5678 9012 3456");
        cardNumber.setWidthFull();
        cardNumber.setRequired(true);

        HorizontalLayout cardDetails = new HorizontalLayout();
        cardDetails.setWidthFull();
        cardDetails.setSpacing(true);

        TextField expiryDate = new TextField("Expiry Date");
        expiryDate.setPlaceholder("MM/YY");
        expiryDate.setWidth("50%");
        expiryDate.setRequired(true);

        TextField cvv = new TextField("CVV");
        cvv.setPlaceholder("123");
        cvv.setWidth("50%");
        cvv.setRequired(true);

        cardDetails.add(expiryDate, cvv);

        TextField cardHolder = new TextField("Card Holder Name");
        cardHolder.setPlaceholder("John Doe");
        cardHolder.setWidthFull();
        cardHolder.setRequired(true);

        cardSection.add(title, cardNumber, cardDetails, cardHolder);
        return cardSection;
    }

    /* ───────────────────────────────
     *   CASH ON DELIVERY SECTION
     * ─────────────────────────────── */
    private VerticalLayout buildCashOnDeliverySection() {
        VerticalLayout cashSection = new VerticalLayout();
        cashSection.setSpacing(true);
        cashSection.setPadding(true);
        cashSection.getStyle()
                .set("background-color", "#f5f5f5")
                .set("border-radius", "8px")
                .set("border", "1px solid #ddd");

        H3 title = new H3("Cash on Delivery");
        title.getStyle().set("margin-top", "0");

        Paragraph description = new Paragraph("Pay cash when your order is delivered. Our delivery agent will collect the payment at your doorstep.");
        description.getStyle()
                .set("color", "#666")
                .set("font-size", "14px")
                .set("margin", "0");

        cashSection.add(title, description);
        return cashSection;
    }

    /* ───────────────────────────────
     *   ORDER SUMMARY
     * ─────────────────────────────── */
    private VerticalLayout buildOrderSummary() {
        VerticalLayout summary = new VerticalLayout();
        summary.setPadding(true);
        summary.getStyle()
                .set("background-color", "white")
                .set("border-radius", "10px")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)");

        H2 title = new H2("Order Summary");
        title.getStyle()
                .set("margin-top", "0")
                .set("color", "#3f0d50");

        // تفاصيل المنتجات
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
                addProductItem(productsList, item.getName(), item.getNumericPrice(), item.getQuantity(), item.getCategory());
            }
        }

        // خط فاصل
        Hr divider = new Hr();
        divider.getStyle()
                .set("margin", "20px 0")
                .set("border", "1px solid #eee");

        // الحسابات
        HorizontalLayout subtotalLayout = new HorizontalLayout();
        subtotalLayout.setWidthFull();
        subtotalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        Paragraph subtotalText = new Paragraph("Subtotal:");
        Paragraph subtotalValue = new Paragraph("$" + subtotal);
        subtotalLayout.add(subtotalText, subtotalValue);

        HorizontalLayout shippingLayout = new HorizontalLayout();
        shippingLayout.setWidthFull();
        shippingLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        Paragraph shippingText = new Paragraph("Shipping:");
        Paragraph shippingValue = new Paragraph("$" + shippingCost);
        shippingLayout.add(shippingText, shippingValue);

        // خط فاصل آخر
        Hr divider2 = new Hr();
        divider2.getStyle()
                .set("margin", "20px 0")
                .set("border", "1px solid #eee");

        HorizontalLayout totalLayout = new HorizontalLayout();
        totalLayout.setWidthFull();
        totalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        Paragraph totalText = new Paragraph("Total:");
        totalText.getStyle().set("font-weight", "bold").set("font-size", "18px");
        Paragraph totalValue = new Paragraph("$" + total);
        totalValue.getStyle()
                .set("font-weight", "bold")
                .set("color", "#ff66cc")
                .set("font-size", "20px");
        totalLayout.add(totalText, totalValue);

        // زر تأكيد الطلب
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

        confirmOrder.getElement().addEventListener("mouseenter", e -> {
            confirmOrder.getStyle().set("background-color", "#2a0a3f");
        });
        confirmOrder.getElement().addEventListener("mouseleave", e -> {
            confirmOrder.getStyle().set("background-color", "#3f0d50");
        });

        confirmOrder.addClickListener(e -> {
            if (cartItems.isEmpty()) {
                Notification.show("Your cart is empty! Add some products first.", 
                                 3000, Position.MIDDLE);
                return;
            }
            
            // تنظيف بيانات الـ Session
            UI.getCurrent().getSession().setAttribute("cartItems", null);
            UI.getCurrent().getSession().setAttribute("subtotal", null);
            UI.getCurrent().getSession().setAttribute("shippingCost", null);
            UI.getCurrent().getSession().setAttribute("total", null);
            
            // إظهار رسالة النجاح
            Notification.show(
                "✅ Order placed successfully! Your order is being processed.",
                5000,
                Position.TOP_CENTER
            );
            
            // الانتقال لصفحة الهوم بعد تأخير بسيط
            UI.getCurrent().access(() -> {
                try {
                    Thread.sleep(2000);
                    UI.getCurrent().navigate("home");
                } catch (InterruptedException ex) {
                    UI.getCurrent().navigate("home");
                }
            });
        });

        // زر العودة للتسوق
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
                .set("cursor", "pointer")
                .set("transition", "all 0.3s");

        continueShopping.getElement().addEventListener("mouseenter", e -> {
            continueShopping.getStyle()
                    .set("background-color", "#3f0d50")
                    .set("color", "white");
        });
        continueShopping.getElement().addEventListener("mouseleave", e -> {
            continueShopping.getStyle()
                    .set("background-color", "transparent")
                    .set("color", "#3f0d50");
        });

        continueShopping.addClickListener(e -> {
            UI.getCurrent().navigate("home");
        });

        summary.add(title, productsList, divider, subtotalLayout, shippingLayout, divider2, totalLayout, confirmOrder, continueShopping);
        summary.setSpacing(false);
        return summary;
    }

    private void addProductItem(VerticalLayout layout, String name, double price, int quantity, String category) {
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
        
        Paragraph productName = new Paragraph(name);
        productName.getStyle()
                .set("margin", "0")
                .set("font-weight", "bold")
                .set("font-size", "16px");
        
        Paragraph productDetails = new Paragraph(category + " × " + quantity);
        productDetails.getStyle()
                .set("margin", "0")
                .set("color", "#666")
                .set("font-size", "14px");

        productInfo.add(productName, productDetails);

        Paragraph productPrice = new Paragraph("$" + (price * quantity));
        productPrice.getStyle()
                .set("font-weight", "bold")
                .set("color", "#3f0d50")
                .set("font-size", "16px");

        productItem.add(productInfo, productPrice);
        layout.add(productItem);
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
        cartLink.getStyle().set("color", "white");
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