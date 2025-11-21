package com.example.examplefeature.ui.admin.add_product.layout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.button.Button;

import java.io.InputStream;
import java.util.UUID;

public class ProductImageSection extends VerticalLayout {

    private String selectedImageName;
    private Image previewImage;
    private byte[] uploadedImageData;
    private ComboBox<String> imageSelector;
    private Paragraph noImageText;

    public ProductImageSection() {
        createImageSection();
    }

    private void createImageSection() {
        setSpacing(true);
        setPadding(false);
        
        H3 imageTitle = new H3("Product Image");
        imageTitle.getStyle().set("margin", "0 0 15px 0");

        // ComboBox لاختيار الصورة من الصور الموجودة
        imageSelector = createImageSelector();

        // أو - رفع صورة جديدة
        H4 uploadTitle = new H4("Or upload new image from your device");
        uploadTitle.getStyle().set("margin", "20px 0 10px 0");

        Upload upload = createImageUpload();

        VerticalLayout previewContainer = createPreviewContainer();

        add(imageTitle, imageSelector, uploadTitle, upload, previewContainer);
    }

    private ComboBox<String> createImageSelector() {
        ComboBox<String> selector = new ComboBox<>("Select from existing images");
        selector.setItems(
            "sweatshirt.jpg", 
            "sneakers.jpg", 
            "smartwatch.jpg", 
            "backpack.jpg",
            "bottle.jpg"
        );
        selector.setPlaceholder("Choose a product image");
        selector.setWidthFull();

        selector.addValueChangeListener(e -> {
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

        return selector;
    }

    private Upload createImageUpload() {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif", "image/jpg");
        upload.setMaxFileSize(10 * 1024 * 1024); 
        
        upload.setDropLabel(new com.vaadin.flow.component.html.Span("Drag and drop image here or click to browse"));
        upload.setUploadButton(new Button("Select Image from Computer"));

        upload.addSucceededListener(event -> {
            try {
                InputStream fileData = buffer.getInputStream();
                uploadedImageData = fileData.readAllBytes();
                
                String fileName = event.getFileName();
                String fileExtension = fileName.substring(fileName.lastIndexOf("."));
                selectedImageName = "product_" + UUID.randomUUID().toString() + fileExtension;
                
                previewImage.setSrc("data:image/jpeg;base64," + 
                    java.util.Base64.getEncoder().encodeToString(uploadedImageData));
                previewImage.setAlt(selectedImageName);
                previewImage.getStyle().set("display", "block");
                noImageText.getStyle().set("display", "none");
                
                imageSelector.clear();
                
                Notification.show("Image uploaded successfully!", 2000, Notification.Position.MIDDLE);
                
            } catch (Exception ex) {
                Notification.show("Error uploading image: " + ex.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        upload.addFileRejectedListener(event -> {
            Notification.show("Upload failed: " + event.getErrorMessage(), 3000, Notification.Position.MIDDLE);
        });

        return upload;
    }

    private VerticalLayout createPreviewContainer() {
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
        
        previewImage = new Image();
        previewImage.setWidth("200px");
        previewImage.setHeight("200px");
        previewImage.getStyle()
                .set("border", "2px dashed #ccc")
                .set("border-radius", "10px")
                .set("object-fit", "cover")
                .set("display", "none");

        noImageText = new Paragraph("No image selected");
        noImageText.getStyle()
                .set("color", "#999")
                .set("font-style", "italic")
                .set("text-align", "center");

        previewContainer.add(noImageText, previewImage);
        previewContainer.setAlignItems(Alignment.CENTER);

        return previewContainer;
    }

    public void clear() {
        selectedImageName = null;
        uploadedImageData = null;
        imageSelector.clear();
        previewImage.getStyle().set("display", "none");
        noImageText.getStyle().set("display", "block");
    }

    // Getters
    public String getSelectedImageName() {
        return selectedImageName;
    }

    public byte[] getUploadedImageData() {
        return uploadedImageData;
    }
}