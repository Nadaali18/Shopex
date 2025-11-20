package com.example.examplefeature.ui.user;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

@Route("aboutUs")
public class AboutUsView extends VerticalLayout {

    public AboutUsView() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(buildHeader());
        add(buildMainContent());
        add(buildFooter());
    }

    private VerticalLayout buildMainContent() {
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.setWidthFull();
        mainContent.setPadding(true);
        mainContent.setSpacing(false);
        mainContent.getStyle()
                .set("background", "linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)")
                .set("min-height", "80vh");

        // العنوان الرئيسي
        H1 mainTitle = new H1("About Us");
        mainTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "40px 0 50px 0")
                .set("font-size", "3rem")
                .set("font-weight", "bold")
                .set("text-shadow", "2px 2px 4px rgba(0,0,0,0.1)");

        mainContent.add(mainTitle);

        // المحتوى الرئيسي - صفين
        HorizontalLayout contentRow = new HorizontalLayout();
        contentRow.setWidthFull();
        contentRow.setSpacing(true);
        contentRow.setPadding(true);

        // المربع الأول - Who We Are
        VerticalLayout whoWeAreBox = createInfoBox(
            "Who We Are",
            "team",
            buildTeamMembers()
        );

        // المربع الثاني - What We Do
        VerticalLayout whatWeDoBox = createInfoBox(
            "What We Do",
            "mission",
            buildMissionDescription()
        );

        contentRow.add(whoWeAreBox, whatWeDoBox);
        contentRow.setFlexGrow(1, whoWeAreBox, whatWeDoBox);

        mainContent.add(contentRow);
        mainContent.setAlignItems(Alignment.CENTER);

        return mainContent;
    }

    private VerticalLayout createInfoBox(String title, String type, VerticalLayout content) {
        VerticalLayout box = new VerticalLayout();
        box.setWidth("45%");
        box.setSpacing(false);
        box.setPadding(true);
        box.getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("border", "2px solid #e0e0e0")
                .set("min-height", "400px");

        // العنوان
        H2 boxTitle = new H2(title);
        boxTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "20px 0 30px 0")
                .set("font-size", "2rem")
                .set("font-weight", "bold");

        // أيقونة
        Icon icon = new Icon(type.equals("team") ? VaadinIcon.USERS : VaadinIcon.LIGHTBULB);
        icon.setSize("50px");
        icon.getStyle()
                .set("color", "#ff66cc")
                .set("margin", "0 auto 20px auto");

        box.add(icon, boxTitle, content);
        box.setAlignItems(Alignment.CENTER);

        return box;
    }

    private VerticalLayout buildTeamMembers() {
        VerticalLayout teamLayout = new VerticalLayout();
        teamLayout.setSpacing(true);
        teamLayout.setPadding(false);
        teamLayout.setWidthFull();

        // الصف الأول
        HorizontalLayout row1 = new HorizontalLayout();
        row1.setWidthFull();
        row1.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        
        Paragraph member1 = createTeamMember("Sulaf Nawar", "44512084");
        Paragraph member2 = createTeamMember("Alhanouf Alsulami", "44511362");
        
        row1.add(member1, member2);

        // الصف الثاني
        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        
        Paragraph member3 = createTeamMember("Jana Nader Alsharyf", "44510753");
        Paragraph member4 = createTeamMember("Fedaa fahad Alharbi", "44008389");
        
        row2.add(member3, member4);

        // الصف الثالث - في النص
        HorizontalLayout row3 = new HorizontalLayout();
        row3.setWidthFull();
        row3.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        
        Paragraph member5 = createTeamMember("Shumokh Naif", "44510317");
        
        row3.add(member5);

        teamLayout.add(row1, row2, row3);
        return teamLayout;
    }

    private Paragraph createTeamMember(String name, String id) {
        Paragraph member = new Paragraph(name + " / " + id);
        member.getStyle()
                .set("margin", "10px 0")
                .set("padding", "12px 20px")
                .set("background", "linear-gradient(135deg, #f8f9fa, #e9ecef)")
                .set("border-radius", "25px")
                .set("border", "1px solid #dee2e6")
                .set("font-weight", "500")
                .set("color", "#495057")
                .set("text-align", "center")
                .set("min-width", "200px");
        return member;
    }

    private VerticalLayout buildMissionDescription() {
        VerticalLayout missionLayout = new VerticalLayout();
        missionLayout.setSpacing(true);
        missionLayout.setPadding(true);
        missionLayout.setWidthFull();

        Paragraph p1 = new Paragraph("Shopex is a premier e-commerce platform dedicated to providing customers with an exceptional online shopping experience. We offer a wide range of high-quality products from fashion to electronics, all carefully curated to meet your lifestyle needs.");
        Paragraph p2 = new Paragraph("Our mission is to revolutionize online shopping by combining cutting-edge technology with personalized customer service. We believe in making shopping convenient, secure, and enjoyable for everyone.");
        Paragraph p3 = new Paragraph("With a user-friendly interface and seamless navigation, Shopex ensures that finding your desired products is quick and effortless. Our secure payment system and reliable delivery network guarantee peace of mind with every purchase.");
        Paragraph p4 = new Paragraph("We are committed to building lasting relationships with our customers through transparency, quality assurance, and continuous innovation in the e-commerce space.");

        styleMissionParagraph(p1);
        styleMissionParagraph(p2);
        styleMissionParagraph(p3);
        styleMissionParagraph(p4);

        missionLayout.add(p1, p2, p3, p4);
        return missionLayout;
    }

    private void styleMissionParagraph(Paragraph paragraph) {
        paragraph.getStyle()
                .set("margin", "0 0 15px 0")
                .set("line-height", "1.6")
                .set("color", "#555")
                .set("font-size", "16px")
                .set("text-align", "justify");
    }

    /* ───────────────────────────────
     *   HEADER
     * ─────────────────────────────── */
    private HorizontalLayout buildHeader() {

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.getStyle().set("background-color", "#3f0d50ff");
        header.getStyle().set("color", "white");
        header.setPadding(true);
        header.setSpacing(true);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        // Logo + site name
        HorizontalLayout logoLayout = new HorizontalLayout();
        Image logo = new Image("/images/white_logo.png", "Shop Wheel");
        logo.setHeight("40px");
        H1 siteName = new H1("Shopex");
        siteName.getStyle().set("margin", "0");
        siteName.getStyle().set("color", "pink");
        logoLayout.add(logo, siteName);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Navigation menu
        HorizontalLayout navMenu = new HorizontalLayout();
        navMenu.setSpacing(true);
        Anchor homeLink = new Anchor("home", "Home");
        Anchor cartLink = new Anchor("cart", "Cart");
        Anchor aboutLink = new Anchor("aboutUs", "About Us");
        homeLink.getStyle().set("color", "white");
        cartLink.getStyle().set("color", "white");
        aboutLink.getStyle().set("color", "pink").set("font-weight", "bold");
        homeLink.getStyle().set("margin-right", "40px");
        cartLink.getStyle().set("margin-right", "40px");
        navMenu.add(homeLink, cartLink, aboutLink);
        navMenu.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        navMenu.setAlignItems(FlexComponent.Alignment.CENTER);

        // Right icons
        HorizontalLayout rightIcons = new HorizontalLayout();

        Icon cartIcon = VaadinIcon.CART.create();
        cartIcon.getStyle()
            .set("color", "pink")
            .set("cursor", "pointer");

        Icon logoutIcon = VaadinIcon.SIGN_OUT.create();
        logoutIcon.getStyle()
            .set("color", "pink")
            .set("cursor", "pointer")
            .set("transition", "color 0.3s");

        logoutIcon.getElement().addEventListener("mouseenter", e -> {
            logoutIcon.getStyle().set("color", "#ff66cc");
        });
        logoutIcon.getElement().addEventListener("mouseleave", e -> {
            logoutIcon.getStyle().set("color", "pink");
        });

        logoutIcon.addClickListener(e -> {
            UI.getCurrent().navigate("login");
        });

        cartIcon.addClickListener(e -> {
            UI.getCurrent().navigate("cart");
        });

        rightIcons.add(cartIcon, logoutIcon);
        rightIcons.setSpacing(true);

        header.add(logoLayout, navMenu, rightIcons);
        header.expand(navMenu);

        return header;
    }

    /* ───────────────────────────────
     *   FOOTER
     * ─────────────────────────────── */
    private VerticalLayout buildFooter() {

        VerticalLayout footer = new VerticalLayout();
        footer.setWidthFull();
        footer.setPadding(true);
        footer.getStyle()
                .set("background-color", "#2a0a3f")
                .set("color", "white")
                .set("text-align", "center")
                .set("margin-top", "50px");

        H3 shopName = new H3("Shopex");
        shopName.getStyle().set("color", "pink").set("margin", "0");

        Paragraph copyright = new Paragraph("© 2024 Shopex. All rights reserved.");
        copyright.getStyle().set("color", "#ccc").set("font-size", "14px");

        Paragraph contact = new Paragraph("Contact us: info@shopex.com | +20 100 123 4567");
        contact.getStyle().set("color", "#ccc").set("font-size", "14px");

        footer.add(shopName, copyright, contact);
        footer.setAlignItems(Alignment.CENTER);

        return footer;
    }
}