package com.example.examplefeature.ui.auth.login;
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

@Route("login")
public class LoginView extends Div {

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
        box.getStyle().set("width", "350px");

        H1 title = new H1("Login");
        title.getStyle().set("color", "#3f0d50ff");

        TextField email = new TextField("Email");
        email.setWidthFull();

        PasswordField password = new PasswordField("Password");
        password.setWidthFull();

        ComboBox<String> role = new ComboBox<>("Role");
        role.setItems("User", "Admin");
        role.setWidthFull();
        role.setPlaceholder("Select your role");

        Button loginButton = new Button("Login");
        loginButton.setWidthFull();
        loginButton.getStyle().set("background-color", "#3f0d50ff");
        loginButton.getStyle().set("color", "white");
        
        loginButton.addClickListener(event -> {
            // التحقق من الحقول المطلوبة
            if (email.getValue().isEmpty() || password.getValue().isEmpty() || role.getValue() == null) {
                Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
                return;
            }
            
            // التحقق من بيانات Login (هنا يمكنك إضافة منطق التحقق من قاعدة البيانات)
            boolean isValidLogin = validateLogin(email.getValue(), password.getValue(), role.getValue());
            
            if (isValidLogin) {
                // التوجيه حسب الـ Role
                if ("Admin".equals(role.getValue())) {
                    UI.getCurrent().navigate("admin-home");
                    Notification.show("Welcome Admin!", 2000, Notification.Position.MIDDLE);
                } else {
                    UI.getCurrent().navigate("home");
                    Notification.show("Welcome!", 2000, Notification.Position.MIDDLE);
                }
            } else {
                Notification.show("Invalid email or password", 3000, Notification.Position.MIDDLE);
            }
        });

        Anchor signup = new Anchor("/signup", "Don't have an account? Sign up");
        signup.getStyle().set("color", "#3f0d50ff");
        signup.getStyle().set("font-weight", "bold");

        VerticalLayout form = new VerticalLayout(
                title, email, password, role, loginButton, signup
        );
        form.setAlignItems(FlexComponent.Alignment.CENTER);
        form.setPadding(false);
        form.setSpacing(true);

        box.add(form);

        VerticalLayout center = new VerticalLayout(box);
        center.setSizeFull();
        center.setAlignItems(FlexComponent.Alignment.START);
        center.setJustifyContentMode(JustifyContentMode.CENTER);
        box.getStyle().set("margin-left", "60px");
        add(bg, center);
    }

    // دالة للتحقق من بيانات Login (يمكنك استبدالها بمنطق قاعدة البيانات)
    private boolean validateLogin(String email, String password, String role) {
        // هنا يمكنك إضافة منطق التحقق من قاعدة البيانات
        // للتبسيط، سأستخدم بيانات افتراضية
        
        if ("Admin".equals(role)) {
            return "admin@shopex.com".equals(email) && "admin123".equals(password);
        } else {
            return "user@shopex.com".equals(email) && "user123".equals(password);
        }
        
        // في التطبيق الحقيقي، استبدل هذا بالتحقق من قاعدة البيانات
        // return userService.validateLogin(email, password, role);
    }
}