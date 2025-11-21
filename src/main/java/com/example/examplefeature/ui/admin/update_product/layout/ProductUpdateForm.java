package com.example.examplefeature.ui.admin.update_product.layout;
import com.example.examplefeature.model.Product;
import com.example.examplefeature.services.ProductService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

public class ProductUpdateForm extends VerticalLayout {

    private TextField productName;
    private ComboBox<String> category;
    private NumberField price;
    private NumberField stock;
    private TextArea description;
    private Button updateButton;
    private ProductService productService;
    private Product selectedProduct;

    public ProductUpdateForm() {
        productService = new ProductService();
        createUpdateForm();
    }

    private void createUpdateForm() {
        setWidth("60%");
        setSpacing(true);
        setPadding(true);
        getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("border", "2px solid #e0e0e0");

        H2 formTitle = new H2("Update Product Details");
        formTitle.getStyle()
                .set("color", "#3f0d50")
                .set("margin", "0 0 20px 0");

        initializeFields();
        
        HorizontalLayout row1 = createFormRow(productName, category);
        HorizontalLayout row2 = createFormRow(price, stock);

        updateButton = createUpdateButton();

        add(formTitle, row1, row2, description, updateButton);
        
        setVisible(false);
    }

    private void initializeFields() {
        productName = new TextField("Product Name");
        productName.setWidth("50%");
        productName.setRequired(true);

        category = new ComboBox<>("Category");
        category.setItems("Clothes", "Shoes", "Electronics", "Accessories", "Home");
        category.setWidth("50%");
        category.setRequired(true);

        price = new NumberField("Price");
        price.setWidth("50%");
        price.setPrefixComponent(new Div(new Text("$")));
        price.setMin(0.01);
        price.setRequired(true);

        stock = new NumberField("Stock Quantity");
        stock.setWidth("50%");
        stock.setMin(0);
        stock.setRequired(true);

        description = new TextArea("Description");
        description.setWidthFull();
        description.setHeight("120px");
        description.setPlaceholder("Enter product description...");
    }

    private HorizontalLayout createFormRow(com.vaadin.flow.component.Component... components) {
        HorizontalLayout row = new HorizontalLayout();
        row.setWidthFull();
        row.setSpacing(true);
        row.add(components);
        return row;
    }

    private Button createUpdateButton() {
        Button button = new Button("Update Product", new Icon(VaadinIcon.EDIT));
        button.getStyle()
                .set("background-color", "#2196F3")
                .set("color", "white")
                .set("width", "200px")
                .set("padding", "15px")
                .set("margin-top", "20px")
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("cursor", "pointer")
                .set("transition", "background-color 0.3s");

        button.getElement().addEventListener("mouseenter", e -> {
            button.getStyle().set("background-color", "#1976D2");
        });
        
        button.getElement().addEventListener("mouseleave", e -> {
            button.getStyle().set("background-color", "#2196F3");
        });

        button.addClickListener(e -> {
            if (validateForm()) {
                updateProduct();
            }
        });

        return button;
    }

    public void loadProductData(Product product) {
        this.selectedProduct = product;
        
        productName.setValue(product.getName());
        category.setValue(product.getCategory());
        price.setValue((double) product.getPrice());
        stock.setValue((double) product.getStock());
        description.setValue(generateDescription(product.getName()));
        
        setVisible(true);
    }

    private String generateDescription(String productName) {
        return "This is a description for " + productName + ". High quality product with excellent features. " +
               "Designed to meet customer expectations and provide outstanding performance.";
    }

    private void updateProduct() {
        if (selectedProduct != null) {
            Product updatedProduct = new Product(
                productName.getValue(),
                category.getValue(),
                price.getValue().intValue(),
                stock.getValue().intValue()
            );

            productService.updateProduct(updatedProduct);
            
            Notification.show("Product updated successfully!", 3000, Notification.Position.MIDDLE);
            clearForm();
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

    private void clearForm() {
        productName.clear();
        category.clear();
        price.clear();
        stock.clear();
        description.clear();
        setVisible(false);
        selectedProduct = null;
    }
}