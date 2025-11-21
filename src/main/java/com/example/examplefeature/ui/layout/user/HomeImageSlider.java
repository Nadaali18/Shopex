package com.example.examplefeature.ui.layout.user;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class HomeImageSlider extends Div {
    private int currentIndex = 0;
    private String[] images = {
            "/images/slider1.jpg",
            "/images/slider2.jpg",
            "/images/slider3.jpg"
    };
    
    private Image sliderImage;
    
    public HomeImageSlider() {
        setWidthFull();
        setHeight("60vh");
        getStyle().set("overflow", "hidden");
        getStyle().set("position", "relative");
        getStyle().set("margin-top", "20px");
        sliderImage = new Image(images[currentIndex], "Slider");
        sliderImage.setWidth("100%");
        sliderImage.setHeight("100%");
        sliderImage.getStyle().set("object-fit", "cover");
    
        Button nextBtn = new Button(new Icon(VaadinIcon.ANGLE_RIGHT));
        nextBtn.getStyle().set("position", "absolute")
                .set("right", "10px")
                .set("top", "50%")
                .set("transform", "translateY(-50%)")
                .set("color", "white")
                .set("background-color", "rgba(0,0,0,0.3)");
        nextBtn.addClickListener(e -> showNext());

        Button prevBtn = new Button(new Icon(VaadinIcon.ANGLE_LEFT));
        prevBtn.getStyle().set("position", "absolute")
                .set("left", "10px")
                .set("top", "50%")
                .set("transform", "translateY(-50%)")
                .set("color", "white")
                .set("background-color", "rgba(0,0,0,0.3)");

        prevBtn.addClickListener(e -> showPrev());

        add(sliderImage, nextBtn, prevBtn);
    }

    private void showNext() {
        currentIndex = (currentIndex + 1) % images.length;
        sliderImage.setSrc(images[currentIndex]);
    }

    private void showPrev() {
        currentIndex = (currentIndex - 1 + images.length) % images.length;
        sliderImage.setSrc(images[currentIndex]);
    }
}
