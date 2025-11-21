package com.example.examplefeature.ui.admin.delete_product.layout;

import com.example.examplefeature.model.ProductsDeletePage;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;

import java.util.function.Consumer;

public class DeleteButton extends Button {

    public DeleteButton(ProductsDeletePage product, Consumer<ProductsDeletePage> deleteCallback) {
        super("Delete", new Icon(VaadinIcon.TRASH));
        
        getStyle()
                .set("background-color", "#f44336")
                .set("color", "white")
                .set("padding", "5px 10px")
                .set("border-radius", "4px")
                .set("cursor", "pointer")
                .set("transition", "background-color 0.3s");

        getElement().addEventListener("mouseenter", e -> {
            getStyle().set("background-color", "#d32f2f");
        });
        
        getElement().addEventListener("mouseleave", e -> {
            getStyle().set("background-color", "#f44336");
        });

        addClickListener(e -> showDeleteConfirmation(product, deleteCallback));
    }

    private void showDeleteConfirmation(ProductsDeletePage product, Consumer<ProductsDeletePage> deleteCallback) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete Product");
        dialog.setText("Are you sure you want to delete \"" + product.getName() + "\"? This action cannot be undone.");
        
        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");
        
        dialog.setCancelable(true);
        dialog.setCancelText("Cancel");

        dialog.addConfirmListener(e -> {
            deleteCallback.accept(product);
            Notification.show("Product \"" + product.getName() + "\" deleted successfully!", 
                             3000, Notification.Position.MIDDLE);
        });

        dialog.open();
    }
}
