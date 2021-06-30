package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {
    public static Scene sceneMM;
    public static int winHeight = 600;
    public static int winWidth = 600;
    private static Stage guiStage;

    public static Stage getStage() {
        return guiStage;
    }

    @Override
    public void start(Stage stage) {
        guiStage = stage;
        sceneMM = new Scene(GameMenu.construct(), winWidth, winHeight);
        MenuSelector.startup();
        sceneMM.setFill(Color.BLACK);
        guiStage.setTitle("STARFIGHTER");

        Image icon = new Image(Utility.getImageRes("/menu/icon.png"));
        guiStage.getIcons().add(icon);

        guiStage.setResizable(false);
        guiStage.setScene(sceneMM);
        guiStage.show();

        sceneMM.setOnKeyPressed((keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                MenuSelector.buttonFunc.next();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                MenuSelector.buttonFunc.prev();
            }
            if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
                MenuSelector.buttonFunc.func();
            }
            if (keyEvent.getCode() == KeyCode.F1) {
                MenuSelector.buttonFunc.special();
            }
            MenuSelector.checkText();
        }));
    }

    public static void main(String[] args) {
        launch();
    }

}