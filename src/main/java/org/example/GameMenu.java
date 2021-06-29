package org.example;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import java.util.Objects;

import static org.example.App.*;

public class GameMenu {
    static int picOfChoice = (int)(Math.random()*6);
    public static Font font = Font.loadFont(Objects.requireNonNull(GameMenu.class.getResource("/utility/pixRectv2.ttf")).toExternalForm(), 48);
    public static Font font2 = Font.loadFont(Objects.requireNonNull(GameMenu.class.getResource("/utility/pixRectv2.ttf")).toExternalForm(), 32);
    public static Group menu = new Group();

        public static Group construct() {
        menu = new Group();
        ImageView logo = new ImageView("menu/logo3.png"); //LOGO
        Image backg3 = new Image("backg/backgPL" + picOfChoice + ".png",winWidth,winHeight,false,false);
        ImageView backgV3 = new ImageView(backg3);
        menu.getChildren().add(backgV3);

        logo.setX(0);
        logo.setY(0);
        menu.getChildren().add(logo);
        //START BUTTON
        return menu;
    }
}
