package com.example.examplefeature.ui.admin.add_product.view;

import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.AppHeader;
import com.example.examplefeature.ui.admin.add_product.layout.ProductForm;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("add-product")
public class AddProductView extends VerticalLayout {

    public AddProductView() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(new AppHeader("admin", "add-product"));
        add(buildMainContent());
        add(new AppFooter());
    }

    private VerticalLayout buildMainContent() {
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.setWidthFull();
        mainContent.setPadding(true);
        mainContent.setSpacing(false);
        mainContent.getStyle()
                .set("background", "linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%)")
                .set("min-height", "80vh");

        H1 mainTitle = new H1("Add New Product");
        mainTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0")
                .set("font-size", "2.5rem")
                .set("font-weight", "bold");

        mainContent.add(mainTitle);
        mainContent.setAlignItems(Alignment.CENTER);

        ProductForm productForm = new ProductForm();
        mainContent.add(productForm);

        return mainContent;
    }
}