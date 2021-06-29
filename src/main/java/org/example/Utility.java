package org.example;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class Utility {
    static private final Image palette = new Image("/utility/pallete1.png");
    static private final PixelReader inPix = palette.getPixelReader();

    public static Color getColorFromPallete(int i) {
        if(i<palette.getWidth()){
            return inPix.getColor(i,0);
        }
        else {return Color.RED;}
    }

    public static void constructInfoFrame() {
        BorderPane layout = new BorderPane();
        Image backg = new Image("menu/info.png",960,550, false, false);
        ImageView backgV3 = new ImageView(backg);
        backgV3.setOpacity(0.4);

        Text data1 = new Text("You are STARFIGHTER. One of many. \n" +
                "Your planet was invaded by forces of unknown origin \n" +
                "They are more advanced. Their numbers are immeasurable. \n" +
                "Governments of whole EARTH created a training program \n" +
                "for the best of the best. Gladly, you were a great figter pilot, \n" +
                "even before the invasion. So you applied.\n" +
                "Engineers created state of art engines, able for interstellar flight \n" +
                "Your mission is to get to enemy base, making way for bombers\n"
        );
        data1.setFill(Utility.getColorFromPallete(19));
        data1.setFont(GameMenu.font2);
        data1.setStroke(Utility.getColorFromPallete(16));
        Text data2 = new Text("Press S & W or ARROWS for navigation in menus \n" +
                "Press ENTER to select menu \n" +
                "IN GAME: \n" +
                "Press A & D to steer your spaceship \n" +
                "Salvage upgrades from enemies \n" +
                "Press SPACE or ENTER for special firemode \n");
        data2.setFill(Utility.getColorFromPallete(19));
        data2.setFont(GameMenu.font2);
        data2.setStroke(Utility.getColorFromPallete(16));
        Stage info = new Stage();
        info.setResizable(false);
        info.setTitle("CONTROLS and INFO");
        Image icon = new Image("/menu/icon.png");
        info.getIcons().add(icon);

        Scene infoData = new Scene(layout,960,550);
        layout.getChildren().add(backgV3);
        layout.setBottom(data2);
        layout.setTop(data1);
        infoData.setFill(Color.BLACK);
        info.setScene(infoData);
        info.show();
    }

    public static int getAvailableLevels(){
        try {
            Scanner scanner = new Scanner(new File("save.txt"));
            MenuSelectorLevel.status.setText("Save file found");
            return scanner.nextInt();
        } catch (FileNotFoundException e) {
            MenuSelectorLevel.status.setText("New save created");
            try {
                PrintWriter out = new PrintWriter("save.txt");
                out.print(6);
                out.close();
                return 6;
            } catch (IOException fileNotFoundException) {
                MenuSelectorLevel.status.setText("cannot create file!");
            }
        }
        return 1;
    }
    public static void progressLevel(){
        try {
            Scanner scanner = new Scanner(new File("save.txt"));
            System.out.println("Save file found and read");
            PrintWriter out = new PrintWriter("save.txt");
            out.print(scanner.nextInt()-1);
        } catch (FileNotFoundException e) {
            System.out.println("No save file found. Creating new...");
            getAvailableLevels();
        }

    }


    public static void generateRandEnemyList() {
    }

    public static void sleepTime(long longMils) {
        try {
            Thread.sleep(longMils);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
