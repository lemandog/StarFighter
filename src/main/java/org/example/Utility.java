package org.example;

import Frames.GameMenu;
import Frames.MenuSelectorLevel;
import Frames.MenuSettings;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Utility {
    static private final Image palette = new Image(getImageRes("/utility/pallete1.png"));
    static private final PixelReader inPix = palette.getPixelReader();
    static String documentsPath = new JFileChooser().getFileSystemView().getDefaultDirectory().getPath() + File.separator + "My Games"+ File.separator +"StarFighter";
    static long loggercounter = 0;


    public static Color getColorFromPallete(int i) {
        if(i<palette.getWidth()){
            return inPix.getColor(i,0);
        }
        else {return Color.RED;}
    }

    public static void constructInfoFrame() {
        BorderPane layout = new BorderPane();
        Image backg = new Image(getImageRes("/menu/info.png"),960,550, false, false);
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
        Image icon = new Image(getImageRes("/menu/icon.png"));
        info.getIcons().add(icon);

        Scene infoData = new Scene(layout,960,550);
        layout.getChildren().add(backgV3);
        layout.setBottom(data2);
        layout.setTop(data1);
        infoData.setFill(Color.BLACK);
        info.setScene(infoData);
        info.show();
    }
    public static void constructAuthorFrame() {
        BorderPane layout = new BorderPane();
        Image backg = new Image(getImageRes("/menu/info.png"),960,550, false, false);
        ImageView backgV3 = new ImageView(backg);
        backgV3.setOpacity(0.4);

        Text data1 = new Text(" This game was made by Lemandog, Aldegida and Groza.\n" +
                "   Latest version available on Lemandog`s github\n" +
                "   This game is distributed on share-free basis, if someone sold \n" +
                "you this game you was lied to.\n" +
                "\n"+
                "   You are free to make any changes, but you cannot \n" +
                "use original code and resources to make non share-free software \n" +
                "   No known interest were broken\n" +
                "\n" +
                "GRAPHICS (3D models and pixelart): Aldegida\n" +
                "CODE, MINOR GRAPHICS AND FONT: Lemandog \n" +
                "TEA AND MORAL SUPPORT: Groza \n"
        );
        data1.setFill(Utility.getColorFromPallete(19));
        data1.setFont(GameMenu.font2);
        data1.setStroke(Utility.getColorFromPallete(16));
        Text data2 = new Text("SOFTWARE USED: \n" +
                "BLENDER v2.91, ASEPRITE v1.2 \n" +
                "MUSIC GENERATED BY TRICK COMPOSER v1.4\n" +
                "CODED IN IntelliJ IDEA Community Edition 2020 with JAVAFX");
        data2.setFill(Utility.getColorFromPallete(19));
        data2.setFont(GameMenu.font2);
        data2.setStroke(Utility.getColorFromPallete(16));
        Stage info = new Stage();
        info.setResizable(false);
        info.setTitle("AUTHORS");
        Image icon = new Image(getImageRes("/menu/icon.png"));
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
            Scanner scanner = new Scanner(new File(documentsPath + File.separator+ "save.txt"));
            MenuSelectorLevel.status.setText("Save file found");
            if (scanner.hasNext()){return scanner.nextInt();}
            else {throw new FileNotFoundException();}
        } catch (FileNotFoundException e) {
            new File(documentsPath + File.separator).mkdir();
            new File(documentsPath + File.separator+ "save.txt");
            MenuSelectorLevel.status.setText("New save created");
            resetSaveFile();
            return 6;
        }
    }
    public static void resetSaveFile(){
        try {
            PrintWriter out = new PrintWriter(documentsPath + File.separator+ "save.txt");
            out.print(6);
            out.close();
        }catch (FileNotFoundException e) {
            MenuSelectorLevel.status.setText("cannot create file!");
        }
    }

    public static void progressLevel(int i){
        try {
            Scanner scanner = new Scanner(new File(documentsPath + File.separator +"save.txt"));
            if (scanner.hasNext()){
                int number = scanner.nextInt();
                if(number > (6-i) && number > 0){
                    debugOutput("Save file found and read");
                    PrintWriter out = new PrintWriter(documentsPath + File.separator +"save.txt");
                    out.print(6-i);
                    out.close();
                }
                scanner.close();
            }
            else {
                throw new FileNotFoundException();
            }


        } catch (FileNotFoundException e) {
            debugOutput("Problem with save file. Creating new...");
            getAvailableLevels();
        }

    }

    public static String getImageRes(String path) {
       return Objects.requireNonNull(Utility.class.getResource(path)).toExternalForm();
    }


    public static int loadLevel(int i) {
        int result = 0;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(documentsPath + File.separator +"misc.txt"));
            if(scanner.hasNextLine()){
                for(int x = 0;x<=i; x++){ //To needed level
                    result =  scanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            generateMisc();
            debugOutput("MISC FILE GENERATED! "+ i);
            loadSettings(i);
        }
            debugOutput("Save file found LEVEL_LENGTH " + result + " NO "+ i);
            return result;
    }

    private static void generateMisc() {
        try {
            PrintWriter out = new PrintWriter(documentsPath + File.separator +"misc.txt");
            out.println(500000);
            out.println(300000);
            out.println(480000);
            out.println(500000);
            out.println(600000);
            out.println(800000);
            out.println(200000);
            out.close();
            debugOutput("New misc file generated");
        }catch (FileNotFoundException e1){
            debugOutput("Failed");
        }
    }

    public static int loadSettings(int i) {
        int result = 0;
        try {
            Scanner scanner = new Scanner(new File(documentsPath + File.separator +"settings.txt"));
            if(scanner.hasNextLine()){
                for(int x = 0;x<=i; x++){ //To needed level
                    result =  scanner.nextInt();
                }
            }
            return result;
        } catch (FileNotFoundException | NoSuchElementException e) {
            saveSettings(60,60,1, 1);
            return loadSettings(i);
        }
    }
    public static void saveSettings(int volM, int volS, int c, int debug) {
        try {
        PrintWriter out = new PrintWriter(documentsPath + File.separator +"settings.txt");
        out.println(volM);
        out.println(volS);
        out.println(c);
        out.println(debug);
        out.close();
        } catch (FileNotFoundException e) {
            debugOutput("NO SETTINGS FILE FOUND! ");
        }
    }
    public static void debugOutput(String text) {
            if(MenuSettings.showDebug == 1){
                System.out.println(text);
            }
    }
}
