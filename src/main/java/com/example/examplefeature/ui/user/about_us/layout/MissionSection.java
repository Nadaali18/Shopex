package com.example.examplefeature.ui.user.about_us.layout;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class MissionSection extends VerticalLayout {

    public MissionSection() {
        createMissionSection();
    }

    private void createMissionSection() {
        setWidth("45%");
        setSpacing(false);
        setPadding(true);
        getStyle()
                .set("background-color", "white")
                .set("border-radius", "15px")
                .set("box-shadow", "0 8px 25px rgba(0,0,0,0.1)")
                .set("border", "2px solid #e0e0e0")
                .set("min-height", "400px");

        // العنوان
        H2 boxTitle = new H2("What We Do");
        boxTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "20px 0 30px 0")
                .set("font-size", "2rem")
                .set("font-weight", "bold");

        // أيقونة
        Icon icon = new Icon(VaadinIcon.LIGHTBULB);
        icon.setSize("50px");
        icon.getStyle()
                .set("color", "#ff66cc")
                .set("margin", "0 auto 20px auto");

        add(icon, boxTitle, buildMissionDescription());
        setAlignItems(Alignment.CENTER);
    }

    private VerticalLayout buildMissionDescription() {
        VerticalLayout missionLayout = new VerticalLayout();
        missionLayout.setSpacing(true);
        missionLayout.setPadding(true);
        missionLayout.setWidthFull();

        String[] missionTexts = {
            "Shopex is a premier e-commerce platform dedicated to providing customers with an exceptional online shopping experience. We offer a wide range of high-quality products from fashion to electronics, all carefully curated to meet your lifestyle needs.",
            "Our mission is to revolutionize online shopping by combining cutting-edge technology with personalized customer service. We believe in making shopping convenient, secure, and enjoyable for everyone.",
            "With a user-friendly interface and seamless navigation, Shopex ensures that finding your desired products is quick and effortless. Our secure payment system and reliable delivery network guarantee peace of mind with every purchase.",
            "We are committed to building lasting relationships with our customers through transparency, quality assurance, and continuous innovation in the e-commerce space."
        };

        for (String text : missionTexts) {
            Paragraph paragraph = new Paragraph(text);
            styleMissionParagraph(paragraph);
            missionLayout.add(paragraph);
        }

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
}