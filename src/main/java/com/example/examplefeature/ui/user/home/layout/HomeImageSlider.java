package com.example.examplefeature.ui.user.home.layout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;

public class HomeImageSlider extends Div {
    private static final String[] SLIDER_IMAGES = {
        "/images/slide1.jpg", "/images/slide2.jpg", "/images/slide3.jpg"
    };

     public HomeImageSlider() {
        createSlider();
    }

    private void createSlider() {
        Div sliderContainer = new Div();
        sliderContainer.setWidth("1200px");
        sliderContainer.getStyle().set("min-height","400px");
        sliderContainer.getStyle().set("overflow", "hidden");
        sliderContainer.getStyle().set("position", "relative");
        sliderContainer.getStyle().set("margin", "30px auto");

        String[] sliderImages = SLIDER_IMAGES;
        Image sliderImage = new Image(sliderImages[0], "Slide 1");
        sliderImage.setWidthFull();
        sliderImage.setHeightFull();
        sliderImage.getStyle().set("object-fit", "fill");

        sliderContainer.add(sliderImage);

        Button leftButton = new Button("<");
        Button rightButton = new Button(">");

        leftButton.getStyle()
                .set("position", "absolute")
                .set("top", "50%")
                .set("left", "10px")
                .set("transform", "translateY(-50%)")
                .set("z-index", "10")
                .set("background-color", "#3f0d50a4")
                .set("color", "white")
                .set("border", "none")
                .set("border-radius", "50%")
                .set("width", "40px")
                .set("height", "40px")
                .set("cursor", "pointer");

        rightButton.getStyle()
                .set("position", "absolute")
                .set("top", "50%")
                .set("right", "10px")
                .set("transform", "translateY(-50%)")
                .set("z-index", "10")
                .set("background-color", "#3f0d50a4")
                .set("color", "white")
                .set("border", "none")
                .set("border-radius", "50%")
                .set("width", "40px")
                .set("height", "40px")
                .set("cursor", "pointer");

        sliderContainer.add(leftButton, rightButton);

        final int[] currentIndex = {0};

        leftButton.addClickListener(e -> {
            currentIndex[0] = (currentIndex[0] - 1 + sliderImages.length) % sliderImages.length;
            sliderImage.setSrc(sliderImages[currentIndex[0]]);
        });

        rightButton.addClickListener(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % sliderImages.length;
            sliderImage.setSrc(sliderImages[currentIndex[0]]);
        });

        UI.getCurrent().access(() -> {
            new Thread(() -> {
                try {
                    while (true) {
                        Thread.sleep(5000); 
                        UI.getCurrent().access(() -> {
                            currentIndex[0] = (currentIndex[0] + 1) % sliderImages.length;
                            sliderImage.setSrc(sliderImages[currentIndex[0]]);
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });
        add(sliderContainer);
    }
}
