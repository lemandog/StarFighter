package org.lemandog.Frames;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import org.lemandog.Utility;

import java.util.Objects;

import static org.lemandog.Frames.App.*;


//In fact, this class contains only links to resoureces and basic Group construction.
//Use it, when need to reset GameMenu.menu Group, and then, add whatever you want
public class GameMenu {
    static int picOfChoice = (int) (Math.random() * 6);
    public static Font font = Font.loadFont(Objects.requireNonNull(GameMenu.class.getResource("/utility/pixRectv2.ttf")).toExternalForm(), 48);
    public static Font font2 = Font.loadFont(Objects.requireNonNull(GameMenu.class.getResource("/utility/pixRectv2.ttf")).toExternalForm(), 32);
    public static Group menu = new Group();

    public static Group construct() {
        menu = new Group();
        ImageView logo = new ImageView(Objects.requireNonNull(Utility.getImageRes("/menu/logo3.png")));
        Image backg3 = new Image(Objects.requireNonNull(Utility.getImageRes("/backg/backgPL" + picOfChoice + ".png")), winWidth, winHeight, false, false);
        ImageView backgV3 = new ImageView(backg3);
        menu.getChildren().add(backgV3);
        menu.getChildren().add(logo);//Logo is on top
        return menu;
    }
}