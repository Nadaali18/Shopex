package com.example.examplefeature.ui.auth.login;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login | Shopex")
@AnonymousAllowed
public class LoginView extends Div implements BeforeEnterObserver {

    private final LoginForm loginForm = new LoginForm();

    public LoginView() {
        setSizeFull();
        setClassName("login-page");

        Image bg = new Image("/images/bg3.jpg", "background");
        bg.setSizeFull();
        bg.getStyle().set("object-fit", "cover");
        bg.getStyle().set("filter", "brightness(40%)");
        bg.getStyle().set("position", "absolute");
        bg.getStyle().set("top", "0");
        bg.getStyle().set("left", "0");
        bg.getStyle().set("z-index", "-1");

        Div box = new Div();
        box.getStyle().set("background", "rgba(255, 255, 255, 0.78)");
        box.getStyle().set("backdrop-filter", "blur(8px)");
        box.getStyle().set("padding", "40px");
        box.getStyle().set("border-radius", "15px");
        box.getStyle().set("width", "400px");

        H1 title = new H1("Login to Shopex");
        title.getStyle().set("color", "#3f0d50ff");

        loginForm.setAction("login");
        loginForm.setForgotPasswordButtonVisible(false);
        
        Anchor signup = new Anchor("/signup", "Don't have an account? Sign up");
        signup.getStyle().set("color", "#3f0d50ff");
        signup.getStyle().set("font-weight", "bold");
        signup.getStyle().set("text-decoration", "none");

        VerticalLayout form = new VerticalLayout(title, loginForm, signup);
        form.setAlignItems(FlexComponent.Alignment.CENTER);
        form.setPadding(false);
        form.setSpacing(true);

        box.add(form);

        VerticalLayout center = new VerticalLayout(box);
        center.setSizeFull();
        center.setAlignItems(FlexComponent.Alignment.START);
        center.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        box.getStyle().set("margin-left", "60px");
        
        add(bg, center);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }
}
