package com.example.examplefeature.ui.user.home.layout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HomeCategoriesBar extends HorizontalLayout {

    public HomeCategoriesBar() {
       addCategory();
    }

    private void addCategory() {

        H3 categoriesTitle = new H3("Categories");
        categoriesTitle.getStyle()
            .set("margin", "30px 0 30px 40px")
            .set("color", "#3f0d50ff")
            .set("font-size", "24px");
        add(categoriesTitle);

        Div categoriesContainer = new Div();
        categoriesContainer.setWidthFull();
        categoriesContainer.getStyle()
            .set("overflow-x", "auto")
            .set("overflow-y", "hidden")
            .set("white-space", "nowrap")
            .set("padding", "15px")
            .set("background", "#ffffffff")
            .set("border-radius", "10px")
            .set("margin", "0 10px 10px 40px")
            .set("min-height", "80px");

        String[] categories = {"Shoes", "Clothes", "Accessories", "Bags", "Perfumes", "Electronics", "Home", "Beauty"};
        
        for (String category : categories) {
            Button categoryButton = new Button(category);
            categoryButton.getStyle()
                .set("display", "inline-block")
                .set("width", "200px")
                .set("height", "60px")
                .set("background-color", "white")
                .set("border", "2px solid #3f0d5076")
                .set("border-radius", "8px")
                .set("font-weight", "bold")
                .set("color", "#3f0d50ff")
                .set("cursor", "pointer")
                .set("margin-right", "15px")
                .set("font-size", "14px");
            
            categoriesContainer.add(categoryButton);
        }

        add(categoriesContainer);
    }
}
