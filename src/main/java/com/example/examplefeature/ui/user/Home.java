package com.example.examplefeature.ui.user;
import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.user.HomeCategoriesBar;
import com.example.examplefeature.ui.layout.user.HomeImageSlider;
import com.example.examplefeature.ui.layout.user.ProductsGrid;
import com.example.examplefeature.ui.layout.user.UserHeader;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.router.Route;

@Route("home")
public class Home extends VerticalLayout {
    public Home() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        add(new UserHeader("home"));
        add(new HomeImageSlider());
        add(new HomeCategoriesBar());
        add(new ProductsGrid());
        add(new AppFooter());
    }
}