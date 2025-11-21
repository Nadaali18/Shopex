package com.example.examplefeature.ui.admin.update_product.view;
import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.AppHeader;
import com.example.examplefeature.ui.admin.update_product.layout.ProductSelectionGrid;
import com.example.examplefeature.ui.admin.update_product.layout.ProductUpdateForm;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("update-product")
public class UpdateProductView extends VerticalLayout {

    private ProductSelectionGrid productSelectionGrid;
    private ProductUpdateForm productUpdateForm;

    public UpdateProductView() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(new AppHeader("admin", "update-product"));
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

        H1 mainTitle = new H1("Update Product");
        mainTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0")
                .set("font-size", "2.5rem")
                .set("font-weight", "bold");

        mainContent.add(mainTitle);
        mainContent.setAlignItems(Alignment.CENTER);

        productSelectionGrid = new ProductSelectionGrid();
        
        productUpdateForm = new ProductUpdateForm();
        
        productSelectionGrid.setSelectionListener(productUpdateForm::loadProductData);

        mainContent.add(productSelectionGrid, productUpdateForm);

        return mainContent;
    }
}