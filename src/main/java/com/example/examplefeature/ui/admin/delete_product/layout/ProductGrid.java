package com.example.examplefeature.ui.admin.delete_product.layout;
import com.example.examplefeature.model.Product;
import com.example.examplefeature.services.ProductService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class ProductGrid extends VerticalLayout {

    private Grid<Product> productGrid;
    private ProductService productService;
    private List<Product> products;

    public ProductGrid(ProductService productService) {
        this.productService = productService;
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

        productGrid = new Grid<>(Product.class, false);
        configureGridColumns();

        productGrid.setItems(products);

        add(sectionTitle, productGrid);
    }

    private void configureGridColumns() {
        productGrid.addColumn(Product::getName)
                .setHeader("Product Name")
                .setAutoWidth(true)
                .setSortable(true);

        productGrid.addColumn(Product::getCategory)
                .setHeader("Category")
                .setAutoWidth(true)
                .setSortable(true);

        productGrid.addColumn(product -> String.format("$%.2f", product.getPrice()))
                .setHeader("Price")
                .setAutoWidth(true)
                .setSortable(true);

        productGrid.addColumn(Product::getStock)
                .setHeader("Stock")
                .setAutoWidth(true)
                .setSortable(true);

                productGrid.addComponentColumn(product -> {
            DeleteButton deleteButton = new DeleteButton(product, this::deleteProduct);
            return deleteButton;
        }).setHeader("Actions").setAutoWidth(true);
    }

    private void deleteProduct(Product product) {
        products.remove(product);
        productGrid.setItems(products);
        productService.deleteProduct(product);
    }

    public void refreshGrid() {
        products = productService.getAllProducts();
        productGrid.setItems(products);
    }
}
