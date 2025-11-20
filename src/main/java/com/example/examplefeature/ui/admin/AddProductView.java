package com.example.examplefeature.ui.admin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

import java.io.InputStream;
import java.util.UUID;

@Route("add-product")
public class AddProductView extends VerticalLayout {

    private String selectedImageName;
    private Image previewImage;
    private byte[] uploadedImageData;

    public AddProductView() {
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
        H1 mainTitle = new H1("Add New Product");
        mainTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0")
                .set("font-size", "2.5rem")
                .set("font-weight", "bold");

        mainContent.add(mainTitle);
        mainContent.setAlignItems(Alignment.CENTER);

        // نموذج إضافة المنتج
        VerticalLayout formContainer = new VerticalLayout();
        formContainer.setWidth("60%");
        formContainer.setSpacing(true);
        formContainer.setPadding(true);
        formContainer.getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("border", "2px solid #e0e0e0");

        // صفوف النموذج
        HorizontalLayout row1 = new HorizontalLayout();
        row1.setWidthFull();
        row1.setSpacing(true);

        TextField productName = new TextField("Product Name");
        productName.setWidth("50%");
        productName.setPlaceholder("Enter product name");

        ComboBox<String> category = new ComboBox<>("Category");
        category.setItems("Clothes", "Shoes", "Electronics", "Accessories", "Home");
        category.setWidth("50%");
        category.setPlaceholder("Select category");

        row1.add(productName, category);

        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setSpacing(true);

        NumberField price = new NumberField("Price");
        price.setWidth("50%");
        price.setPrefixComponent(new Div(new Text("$")));
        price.setPlaceholder("0.00");

        NumberField stock = new NumberField("Stock Quantity");
        stock.setWidth("50%");
        stock.setPlaceholder("Enter quantity");

        row2.add(price, stock);

        // وصف المنتج
        TextArea description = new TextArea("Description");
        description.setWidthFull();
        description.setPlaceholder("Enter product description...");
        description.setHeight("120px");

        // قسم اختيار وعرض الصورة
        VerticalLayout imageSection = buildImageSection();
        
        formContainer.add(row1, row2, description, imageSection);

        // زر الإضافة
        Button addButton = new Button("Add Product", new Icon(VaadinIcon.PLUS_CIRCLE));
        addButton.getStyle()
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("width", "200px")
                .set("padding", "15px")
                .set("margin-top", "20px");

        addButton.addClickListener(e -> {
            if (validateForm(productName, category, price, stock, description)) {
                saveProductToDatabase(productName.getValue(), category.getValue(), 
                                    price.getValue(), stock.getValue(), 
                                    description.getValue(), selectedImageName);
                
                Notification.show("Product added successfully!", 3000, Notification.Position.MIDDLE);
                clearForm(productName, category, price, stock, description);
            }
        });

        formContainer.add(addButton);
        mainContent.add(formContainer);

        return mainContent;
    }

    private VerticalLayout buildImageSection() {
        VerticalLayout imageSection = new VerticalLayout();
        imageSection.setSpacing(true);
        imageSection.setPadding(false);
        
        H3 imageTitle = new H3("Product Image");
        imageTitle.getStyle().set("margin", "0 0 15px 0");

        // ComboBox لاختيار الصورة من الصور الموجودة
        ComboBox<String> imageSelector = new ComboBox<>("Select from existing images");
        imageSelector.setItems(
            "sweatshirt.jpg", 
            "sneakers.jpg", 
            "smartwatch.jpg", 
            "backpack.jpg",
            "bottle.jpg"
        );
        imageSelector.setPlaceholder("Choose a product image");
        imageSelector.setWidthFull();

        // أو - رفع صورة جديدة
        H4 uploadTitle = new H4("Or upload new image from your device");
        uploadTitle.getStyle().set("margin", "20px 0 10px 0");

        // مكون رفع الملفات
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif", "image/jpg");
        upload.setMaxFileSize(5 * 1024 * 1024); // 5MB حد أقصى
        
        // تخصيص مظهر زر الرفع
        upload.setDropLabel(new Span("Drag and drop image here or click to browse"));
        upload.setUploadButton(new Button("Select Image from Computer"));
        
        // صورة المعاينة
        previewImage = new Image();
        previewImage.setWidth("200px");
        previewImage.setHeight("200px");
        previewImage.getStyle()
                .set("border", "2px dashed #ccc")
                .set("border-radius", "10px")
                .set("object-fit", "cover")
                .set("display", "none");

        // نص عندما لا توجد صورة مختارة
        Paragraph noImageText = new Paragraph("No image selected");
        noImageText.getStyle()
                .set("color", "#999")
                .set("font-style", "italic")
                .set("text-align", "center");

        VerticalLayout previewContainer = new VerticalLayout();
        previewContainer.setSpacing(false);
        previewContainer.setPadding(true);
        previewContainer.getStyle()
                .set("border", "2px solid #f0f0f0")
                .set("border-radius", "10px")
                .set("background-color", "#fafafa")
                .set("min-height", "250px")
                .set("align-items", "center")
                .set("justify-content", "center");
        
        previewContainer.add(noImageText, previewImage);
        previewContainer.setAlignItems(Alignment.CENTER);

        // حدث عند اختيار صورة من القائمة المنسدلة
        imageSelector.addValueChangeListener(e -> {
            String selectedImage = e.getValue();
            if (selectedImage != null && !selectedImage.isEmpty()) {
                selectedImageName = selectedImage;
                uploadedImageData = null; // Reset uploaded image
                
                // عرض الصورة المختارة
                previewImage.setSrc("/images/products/" + selectedImage);
                previewImage.setAlt(selectedImage);
                previewImage.getStyle().set("display", "block");
                noImageText.getStyle().set("display", "none");
                
                Notification.show("Image selected: " + selectedImage, 1500, Notification.Position.MIDDLE);
            } else {
                // إخفاء الصورة
                previewImage.getStyle().set("display", "none");
                noImageText.getStyle().set("display", "block");
                selectedImageName = null;
            }
        });

        // حدث عند رفع صورة جديدة
        upload.addSucceededListener(event -> {
            try {
                // الحصول على بيانات الصورة
                InputStream fileData = buffer.getInputStream();
                uploadedImageData = fileData.readAllBytes();
                
                // إنشاء اسم فريد للصورة
                String fileName = event.getFileName();
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                selectedImageName = "product_" + UUID.randomUUID().toString() + fileExtension;
                
                // عرض الصورة المرفوعة
                previewImage.setSrc("data:image/jpeg;base64," + 
                    java.util.Base64.getEncoder().encodeToString(uploadedImageData));
                previewImage.setAlt(selectedImageName);
                previewImage.getStyle().set("display", "block");
                noImageText.getStyle().set("display", "none");
                
                // إعادة تعيين الـ ComboBox
                imageSelector.clear();
                
                Notification.show("Image uploaded successfully!", 2000, Notification.Position.MIDDLE);
                
            } catch (Exception ex) {
                Notification.show("Error uploading image: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        // إضافة حدث عند فشل الرفع
        upload.addFileRejectedListener(event -> {
            Notification.show("Upload failed: " + event.getErrorMessage(), 3000, Notification.Position.MIDDLE);
        });

        imageSection.add(imageTitle, imageSelector, uploadTitle, upload, previewContainer);
        return imageSection;
    }

    private boolean validateForm(TextField name, ComboBox<String> category, NumberField price, 
                               NumberField stock, TextArea description) {
        if (name.getValue().isEmpty()) {
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

    private void saveProductToDatabase(String name, String category, Double price, 
                                     Double stock, String description, String imageName) {
        // هنا يمكنك إضافة منطق حفظ المنتج في قاعدة البيانات
        System.out.println("=== Saving Product ===");
        System.out.println("Name: " + name);
        System.out.println("Category: " + category);
        System.out.println("Price: $" + price);
        System.out.println("Stock: " + stock);
        System.out.println("Description: " + description);
        System.out.println("Image: " + (imageName != null ? imageName : "No image"));
        
        // إذا كانت هناك صورة مرفوعة، احفظها في ملف النظام
        if (uploadedImageData != null) {
            System.out.println("Uploaded image size: " + uploadedImageData.length + " bytes");
            // هنا يمكنك إضافة كود لحفظ الصورة في مجلد الصور الخاص بالتطبيق
            // saveImageToFileSystem(uploadedImageData, imageName);
        }
        
        System.out.println("=====================");
    }

    private void clearForm(TextField name, ComboBox<String> category, NumberField price, 
                          NumberField stock, TextArea description) {
        name.clear();
        category.clear();
        price.clear();
        stock.clear();
        description.clear();
        selectedImageName = null;
        uploadedImageData = null;
        previewImage.getStyle().set("display", "none");
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
        addProductLink.getStyle().set("color", "pink").set("font-weight", "bold");
        deleteProductLink.getStyle().set("color", "white");
        updateProductLink.getStyle().set("color", "white");
        navMenu.add(homeLink, addProductLink,deleteProductLink,updateProductLink);
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