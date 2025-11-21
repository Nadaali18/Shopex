package com.example.examplefeature.ui.admin.home.view;
import com.example.examplefeature.ui.admin.home.layout.AdminActionsSection;
import com.example.examplefeature.ui.admin.home.layout.AdminHeroSection;
import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.AppHeader;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("admin-home")
public class AdminHomeView extends VerticalLayout {

    public AdminHomeView() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(new AppHeader("admin", "admin-home"));
        add(new AdminHeroSection());
        add(new AdminActionsSection());
        add(new AppFooter());
    }
}