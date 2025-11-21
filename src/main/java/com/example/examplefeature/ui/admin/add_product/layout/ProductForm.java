package com.example.examplefeature.ui.admin.add_product.layout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class ProductForm extends VerticalLayout {

    private ProductImageSection imageSection;
    private TextField productName;
    private ComboBox<String> category;
    private NumberField price;
    private NumberField stock;
    private TextArea description;

    public ProductForm() {
        createForm();
    }

    private void createForm() {
        setWidth("60%");
        setSpacing(true);
        setPadding(true);
        getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("border", "2px solid #e0e0e0");

        initializeFields();
        
        HorizontalLayout row1 = createFormRow(productName, category);
        HorizontalLayout row2 = createFormRow(price, stock);

        imageSection = new ProductImageSection();

        Button addButton = createAddButton();

        add(row1, row2, description, imageSection, addButton);
    }

    private void initializeFields() {
        productName = new TextField("Product Name");
        productName.setWidth("50%");
        productName.setPlaceholder("Enter product name");

        category = new ComboBox<>("Category");
        category.setItems("Clothes", "Shoes", "Electronics", "Accessories", "Home");
        category.setWidth("50%");
        category.setPlaceholder("Select category");

        price = new NumberField("Price");
        price.setWidth("50%");
        price.setPrefixComponent(new com.vaadin.flow.component.html.Div(new com.vaadin.flow.component.Text("$")));
        price.setPlaceholder("0.00");

        stock = new NumberField("Stock Quantity");
        stock.setWidth("50%");
        stock.setPlaceholder("Enter quantity");

        description = new TextArea("Description");
        description.setWidthFull();
        description.setPlaceholder("Enter product description...");
        description.setHeight("120px");
    }

    private HorizontalLayout createFormRow(com.vaadin.flow.component.Component... components) {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.setSpacing(true);
        row.add(components);
        return row;
    }

    private Button createAddButton() {
        Button addButton = new Button("Add Product", new Icon(VaadinIcon.PLUS_CIRCLE));
        addButton.getStyle()
                .set("background-color", "#4CAF50")
                .set("color", "white")
                .set("width", "200px")
                .set("padding", "15px")
                .set("margin-top", "20px");

        addButton.addClickListener(e -> {
            if (validateForm()) {
                saveProductToDatabase();
                Notification.show("Product added successfully!", 3000, Notification.Position.MIDDLE);
                clearForm();
            }
        });

        return addButton;
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

    private void saveProductToDatabase() {
        System.out.println("=== Saving Product ===");
        System.out.println("Name: " + productName.getValue());
        System.out.println("Category: " + category.getValue());
        System.out.println("Price: $" + price.getValue());
        System.out.println("Stock: " + stock.getValue());
        System.out.println("Description: " + description.getValue());
        System.out.println("Image: " + (imageSection.getSelectedImageName() != null ? 
                          imageSection.getSelectedImageName() : "No image"));
        
        if (imageSection.getUploadedImageData() != null) {
            System.out.println("Uploaded image size: " + imageSection.getUploadedImageData().length + " bytes");
        }
        
        System.out.println("=====================");
    }

    private void clearForm() {
        productName.clear();
        category.clear();
        price.clear();
        stock.clear();
        description.clear();
        imageSection.clear();
    }
}