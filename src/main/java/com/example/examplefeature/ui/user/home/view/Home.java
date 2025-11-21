package com.example.examplefeature.ui.user.home.view;
import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.UserHeader;
import com.example.examplefeature.ui.user.home.layout.BestProductsSection;
import com.example.examplefeature.ui.user.home.layout.HomeCategoriesBar;
import com.example.examplefeature.ui.user.home.layout.HomeImageSlider;
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
        add(new BestProductsSection());
        add(new AppFooter());
    }
}