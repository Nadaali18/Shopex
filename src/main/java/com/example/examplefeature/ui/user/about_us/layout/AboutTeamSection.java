package com.example.examplefeature.ui.user.about_us.layout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class AboutTeamSection extends VerticalLayout {

    public AboutTeamSection() {
        createTeamSection();
    }

    private void createTeamSection() {
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
        H2 boxTitle = new H2("Who We Are");
        boxTitle.getStyle()
                .set("text-align", "center")
                .set("color", "#3f0d50")
                .set("margin", "20px 0 30px 0")
                .set("font-size", "2rem")
                .set("font-weight", "bold");

        // أيقونة
        Icon icon = new Icon(VaadinIcon.USERS);
        icon.setSize("50px");
        icon.getStyle()
                .set("color", "#ff66cc")
                .set("margin", "0 auto 20px auto");

        add(icon, boxTitle, buildTeamMembers());
        setAlignItems(Alignment.CENTER);
    }

    private VerticalLayout buildTeamMembers() {
        VerticalLayout teamLayout = new VerticalLayout();
        teamLayout.setSpacing(true);
        teamLayout.setPadding(false);
        teamLayout.setWidthFull();

        // بيانات الفريق
        String[][] teamData = {
            {"Sulaf Nawar", "44512084"},
            {"Alhanouf Alsulami", "44511362"},
            {"Jana Nader Alsharyf", "44510753"},
            {"Fedaa fahad Alharbi", "44008389"},
            {"Shumokh Naif", "44510317"}
        };

        // الصف الأول
        HorizontalLayout row1 = new HorizontalLayout();
        row1.setWidthFull();
        row1.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        row1.add(createTeamMember(teamData[0][0], teamData[0][1]));
        row1.add(createTeamMember(teamData[1][0], teamData[1][1]));

        // الصف الثاني
        HorizontalLayout row2 = new HorizontalLayout();
        row2.setWidthFull();
        row2.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        row2.add(createTeamMember(teamData[2][0], teamData[2][1]));
        row2.add(createTeamMember(teamData[3][0], teamData[3][1]));

        // الصف الثالث - في النص
        HorizontalLayout row3 = new HorizontalLayout();
        row3.setWidthFull();
        row3.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        row3.add(createTeamMember(teamData[4][0], teamData[4][1]));

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
}