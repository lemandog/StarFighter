package Frames;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Utility;

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
    }

    public static void main(String[] args) {
        launch();
    }

}