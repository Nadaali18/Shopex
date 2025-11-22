package com.example.examplefeature.ui.auth.signup;

import com.example.examplefeature.service.UserServiceImpl;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("signup")
@PageTitle("Sign Up | Shopex")
@AnonymousAllowed
public class SignupView extends Div {

    private final UserServiceImpl userService;

    public SignupView(UserServiceImpl userService) {
        this.userService = userService;
        
        setSizeFull();
        setClassName("signup-page");

        // Background image
        Image bg = new Image("/images/bg3.jpg", "background");
        bg.setSizeFull();
        bg.getStyle().set("object-fit", "cover");
        bg.getStyle().set("filter", "brightness(40%)");
        bg.getStyle().set("position", "absolute");
        bg.getStyle().set("top", "0");
        bg.getStyle().set("left", "0");
        bg.getStyle().set("z-index", "-1");

        // Form Box
        Div box = new Div();
        box.getStyle().set("background", "rgba(255, 255, 255, 0.78)");
        box.getStyle().set("backdrop-filter", "blur(8px)");
        box.getStyle().set("padding", "40px");
        box.getStyle().set("border-radius", "15px");
        box.getStyle().set("width", "400px");

        // Title
        H1 title = new H1("Create Account");
        title.getStyle().set("color", "#3f0d50ff");
        
        // Fields
        TextField username = new TextField("Username");
        username.setWidthFull();
        username.setRequired(true);

        EmailField email = new EmailField("Email");
        email.setWidthFull();
        email.setRequired(true);

        PasswordField password = new PasswordField("Password");
        password.setWidthFull();
        password.setRequired(true);
        password.setHelperText("Minimum 6 characters");

        // Button
        Button signupButton = new Button("Sign Up");
        signupButton.setWidthFull();
        signupButton.getStyle().set("background-color", "#3f0d50ff");
        signupButton.getStyle().set("color", "white");
        
        signupButton.addClickListener(event -> {
            // Validate fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
                return;
            }
            
            // Validate email format
            if (!email.getValue().contains("@")) {
                Notification.show("Please enter a valid email", 3000, Notification.Position.MIDDLE);
                return;
            }
            
            // Validate password strength
            if (password.getValue().length() < 6) {
                Notification.show("Password must be at least 6 characters", 3000, Notification.Position.MIDDLE);
                return;
            }
            
            try {
                // Register user
                userService.createUser(username.getValue(), password.getValue(), email.getValue());
                Notification.show("Account created successfully! Please login.", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().navigate("login");
            } catch (RuntimeException e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        Anchor login = new Anchor("/login", "Already have an account? Log in");
        login.getStyle().set("color", "#3f0d50ff");
        login.getStyle().set("font-weight", "bold");
        login.getStyle().set("text-decoration", "none");

        // Form Layout
        VerticalLayout form = new VerticalLayout(
                title, username, email, password, signupButton, login
        );
        form.setAlignItems(FlexComponent.Alignment.CENTER);
        form.setPadding(false);
        form.setSpacing(true);

        box.add(form);

        // Center layout
        VerticalLayout center = new VerticalLayout(box);
        center.setSizeFull();
        center.setAlignItems(FlexComponent.Alignment.START);
        center.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        box.getStyle().set("margin-left", "60px");

        add(bg, center);
    }
}
