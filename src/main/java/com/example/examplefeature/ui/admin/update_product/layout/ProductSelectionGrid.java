package com.example.examplefeature.ui.admin.update_product.layout;
import com.example.examplefeature.model.Product;
import com.example.examplefeature.services.ProductService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;
import java.util.function.Consumer;

public class ProductSelectionGrid extends VerticalLayout {

    private Grid<Product> productGrid;
    private ProductService productService;
    private List<Product> products;
    private Consumer<Product> selectionListener;

    public ProductSelectionGrid() {
        productService = new ProductService();
        createSelectionGrid();
    }

    private void createSelectionGrid() {
        setWidth("80%");
        setSpacing(true);
        setPadding(true);
        getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("margin-bottom", "30px");

        H2 selectionTitle = new H2("Select Product to Update");
        selectionTitle.getStyle()
                .set("color", "#3f0d50")
                .set("margin", "0 0 20px 0");

        // الحصول على البيانات من الخدمة
        products = productService.getAllProducts();

        // إنشاء الـ Grid
        productGrid = new Grid<>(Product.class, false);
        configureGridColumns();

        productGrid.setItems(products);

        // إعداد حدث الاختيار
        productGrid.addSelectionListener(selection -> {
            if (!selection.getAllSelectedItems().isEmpty()) {
                Product selectedProduct = selection.getAllSelectedItems().iterator().next();
                if (selectionListener != null) {
                    selectionListener.accept(selectedProduct);
                }
            }
        });

        add(selectionTitle, productGrid);
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
    }

    public void setSelectionListener(Consumer<Product> selectionListener) {
        this.selectionListener = selectionListener;
    }

    public void refreshGrid() {
        products = productService.getAllProducts();
        productGrid.setItems(products);
        productGrid.getDataProvider().refreshAll();
    }
}
