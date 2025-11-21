package com.example.examplefeature.ui.user.about_us.view;

import com.example.examplefeature.ui.layout.AppFooter;
import com.example.examplefeature.ui.layout.AppHeader;
import com.example.examplefeature.ui.user.about_us.layout.AboutHeader;
import com.example.examplefeature.ui.user.about_us.layout.AboutTeamSection;
import com.example.examplefeature.ui.user.about_us.layout.MissionSection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("aboutUs")
public class AboutUsView extends VerticalLayout {

    public AboutUsView() {
        setWidthFull();
        setPadding(false);
        setSpacing(false);

        add(new AppHeader("user","aboutUs"));
        add(buildMainContent());
        add(new AppFooter());
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
        mainContent.add(new AboutHeader());

        // المحتوى الرئيسي - صفين
        HorizontalLayout contentRow = new HorizontalLayout();
        contentRow.setWidthFull();
        contentRow.setSpacing(true);
        contentRow.setPadding(true);

        // المربع الأول - Who We Are
        VerticalLayout whoWeAreBox = new AboutTeamSection();

        // المربع الثاني - What We Do
        VerticalLayout whatWeDoBox = new MissionSection();

        contentRow.add(whoWeAreBox, whatWeDoBox);
        contentRow.setFlexGrow(1, whoWeAreBox, whatWeDoBox);

        mainContent.add(contentRow);
        mainContent.setAlignItems(Alignment.CENTER);

        return mainContent;
    }
}