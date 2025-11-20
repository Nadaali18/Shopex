package com.example.examplefeature.ui.signup;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("signup")
public class SignupView extends Div {

    public SignupView() {

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
        box.getStyle().set("width", "350px");

        // Title
        H1 title = new H1("Sign Up");
        title.getStyle().set("color", "#3f0d50ff");
        
        // Fields
        TextField username = new TextField("Username");
        username.setWidthFull();

        TextField email = new TextField("Email");
        email.setWidthFull();

        PasswordField password = new PasswordField("Password");
        password.setWidthFull();

        ComboBox<String> role = new ComboBox<>("Role");
        role.setItems("User", "Admin");
        role.setWidthFull();
        role.setPlaceholder("Select your role");

        // Button
        Button signupButton = new Button("Sign Up");
        signupButton.setWidthFull();
        signupButton.getStyle().set("background-color", "#3f0d50ff");
        signupButton.getStyle().set("color", "white");
        
        signupButton.addClickListener(event -> {
            // التحقق من الحقول المطلوبة
            if (username.getValue().isEmpty() || email.getValue().isEmpty() || 
                password.getValue().isEmpty() || role.getValue() == null) {
                Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
                return;
            }
            
            // التحقق من قوة كلمة المرور (اختياري)
            if (password.getValue().length() < 6) {
                Notification.show("Password must be at least 6 characters", 3000, Notification.Position.MIDDLE);
                return;
            }
            
            // تسجيل المستخدم (هنا يمكنك إضافة منطق الحفظ في قاعدة البيانات)
            boolean isSignupSuccessful = registerUser(username.getValue(), email.getValue(), password.getValue(), role.getValue());
            
            if (isSignupSuccessful) {
                Notification.show("Account created successfully!", 3000, Notification.Position.MIDDLE);
                
                // التوجيه حسب الـ Role بعد التسجيل
                if ("Admin".equals(role.getValue())) {
                    UI.getCurrent().navigate("admin-home");
                } else {
                    UI.getCurrent().navigate("home");
                }
            } else {
                Notification.show("Email already exists", 3000, Notification.Position.MIDDLE);
            }
        });

        Anchor login = new Anchor("/login", "Already have an account? Log in");
        login.getStyle().set("color", "#3f0d50ff");
        login.getStyle().set("font-weight", "bold");

        // Form Layout
        VerticalLayout form = new VerticalLayout(
                title, username, email, password, role, signupButton, login
        );
        form.setAlignItems(FlexComponent.Alignment.CENTER);
        form.setPadding(false);
        form.setSpacing(true);

        box.add(form);

        // Center layout
        VerticalLayout center = new VerticalLayout(box);
        center.setSizeFull();
        center.setAlignItems(FlexComponent.Alignment.START);
        center.setJustifyContentMode(JustifyContentMode.CENTER);
        box.getStyle().set("margin-left", "60px");

        add(bg, center);
    }

    // دالة لتسجيل المستخدم (يمكنك استبدالها بمنطق قاعدة البيانات)
    private boolean registerUser(String username, String email, String password, String role) {
        // هنا يمكنك إضافة منطق الحفظ في قاعدة البيانات
        // للتبسيط، سأعود بـ true دائماً
        
        // في التطبيق الحقيقي:
        // User user = new User(username, email, password, role);
        // return userService.saveUser(user);
        
        return true;
    }
}