package com.example.examplefeature.ui.user.check_out.layout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

public class ScreenshotUpload {

    public VerticalLayout create() {
        VerticalLayout uploadSection = new VerticalLayout();
        uploadSection.setSpacing(true);
        uploadSection.setPadding(false);

        Div screenshotPreview = createScreenshotPreview();
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/jpg");
        upload.setDropLabel(new com.vaadin.flow.component.html.Span("Drag and drop payment screenshot here"));
        upload.setUploadButton(new Button("Select Screenshot"));
        
        upload.addSucceededListener(event -> {
            Notification.show("Screenshot uploaded successfully!", 3000, Position.MIDDLE);
            screenshotPreview.removeAll();
            Paragraph successText = new Paragraph("Screenshot uploaded: " + event.getFileName());
            successText.getStyle().set("color", "#3f0d50").set("text-align", "center");
            screenshotPreview.add(successText);
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

        return uploadSection;
    }

    private Div createScreenshotPreview() {
        Div screenshotPreview = new Div();
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

        return screenshotPreview;
    }
}