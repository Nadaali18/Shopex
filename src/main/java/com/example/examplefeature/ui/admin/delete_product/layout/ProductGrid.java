package com.example.examplefeature.ui.admin.delete_product.layout;
import com.example.examplefeature.model.ProductsDeletePage;
import com.example.examplefeature.ui.services.DeleteProductService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class ProductGrid extends VerticalLayout {

    private Grid<ProductsDeletePage> productGrid;
    private DeleteProductService productService;
    private List<ProductsDeletePage> products;

    public ProductGrid() {
        productService = new DeleteProductService();
        createProductGrid();
    }

    private void createProductGrid() {
        setWidth("80%");
        setSpacing(true);
        setPadding(true);
        getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)");

        H2 sectionTitle = new H2("Select Product to Delete");
        sectionTitle.getStyle()
                .set("color", "#3f0d50")
                .set("margin", "0 0 20px 0");

        products = productService.getAllProducts();

        productGrid = new Grid<>(ProductsDeletePage.class, false);
        configureGridColumns();

        productGrid.setItems(products);

        add(sectionTitle, productGrid);
    }

    private void configureGridColumns() {
        productGrid.addColumn(ProductsDeletePage::getName)
                .setHeader("Product Name")
                .setAutoWidth(true)
                .setSortable(true);

        productGrid.addColumn(ProductsDeletePage::getCategory)
                .setHeader("Category")
                .setAutoWidth(true)
                .setSortable(true);

        productGrid.addColumn(product -> String.format("$%.2f", product.getPrice()))
                .setHeader("Price")
                .setAutoWidth(true)
                .setSortable(true);

        productGrid.addColumn(ProductsDeletePage::getStock)
                .setHeader("Stock")
                .setAutoWidth(true)
                .setSortable(true);

                productGrid.addComponentColumn(product -> {
            DeleteButton deleteButton = new DeleteButton(product, this::deleteProduct);
            return deleteButton;
        }).setHeader("Actions").setAutoWidth(true);
    }

    private void deleteProduct(ProductsDeletePage product) {
        products.remove(product);
        productGrid.setItems(products);
        productService.deleteProduct(product);
    }

    public void refreshGrid() {
        products = productService.getAllProducts();
        productGrid.setItems(products);
    }
}
