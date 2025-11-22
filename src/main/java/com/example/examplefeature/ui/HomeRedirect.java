package com.example.examplefeature.ui;

import com.example.examplefeature.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@AnonymousAllowed
public class HomeRedirect extends Div {

    public HomeRedirect(SecurityService securityService) {
        if (securityService.isUserLoggedIn()) {
            if (securityService.isAdmin()) {
                UI.getCurrent().navigate("admin-home");
            } else {
                UI.getCurrent().navigate("home");
            }
        } else {
            UI.getCurrent().navigate("login");
        }
    }
}
