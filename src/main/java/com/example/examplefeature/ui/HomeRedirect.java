package com.example.examplefeature.ui;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("")
public class HomeRedirect extends Div {

    public HomeRedirect() {
        UI.getCurrent().navigate("login");
    }
}
