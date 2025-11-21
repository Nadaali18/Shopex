package com.example.examplefeature.ui.admin.home.layout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AdminActionsSection extends VerticalLayout {

    public AdminActionsSection() {
        createActionsSection();
    }

    private void createActionsSection() {
        setWidthFull();
        setPadding(true);
        setSpacing(true);
        getStyle()
                .set("background", "linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%)")
                .set("min-height", "60vh");

        H2 sectionTitle = new H2("Product Management");
        sectionTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0")
                .set("font-size", "2.5rem")
                .set("font-weight", "bold");

        add(sectionTitle);
        setAlignItems(Alignment.CENTER);

        AdminActionBox addProductBox = new AdminActionBox(
            "Add Product", 
            "/images/add.jpg",
            "Add new products to your store inventory",
            "add-product"
        );

        AdminActionBox deleteProductBox = new AdminActionBox(
            "Delete Product", 
            "/images/delete.jpg",
            "Remove products from your store inventory",
            "delete-product"
        );

        AdminActionBox updateProductBox = new AdminActionBox(
            "Update Product", 
            "/images/update.jpg",
            "Modify existing product information and details",
            "update-product"
        );

        add(addProductBox, deleteProductBox, updateProductBox);
    }
}