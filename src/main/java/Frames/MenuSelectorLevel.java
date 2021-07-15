package Frames;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import org.example.Utility;

import static Frames.MenuSelector.*;
import static Frames.App.winHeight;
import static Frames.App.winWidth;

public class MenuSelectorLevel {
    public static Text gamemode = new Text("Campain / Arcade");
    public static Text status = new Text("Reading file");
    public static Text freemode = new Text("Free fight");
    public static Text lvl1 = new Text("LVL 1");
    public static Text lvl2 = new Text("LVL 2");
    public static Text lvl3 = new Text("LVL 3");
    public static Text lvl4 = new Text("LVL 4");
    public static Text lvl5 = new Text("LVL 5");
    public static Text lvl6 = new Text("GRAND FINALE");
    public static int offsetLevel = Utility.getAvailableLevels();

    static buttonFunc selButton = buttonFunc.FREEMODE;

    public static void startup(){
        int offset = 200;
        int increment = 40;

        gamemode.setFont(GameMenu.font);
        gamemode.setLayoutX((float)winWidth/5);
        gamemode.setLayoutY((float)winHeight/5 + (float) offset/2 + increment);
        gamemode.setFill(additional);
        GameMenu.menu.getChildren().add(gamemode);

        status.setFont(GameMenu.font);
        status.setLayoutX((float)winWidth/5);
        status.setLayoutY((float)winHeight/5 + (float) offset/2);
        status.setFill(additional);
        GameMenu.menu.getChildren().add(status);

        freemode.setFont(GameMenu.font);
        freemode.setLayoutX((float)winWidth/5  + offset);
        freemode.setLayoutY((float)winHeight/5 + offset + increment);
        freemode.setFill(hover);
        GameMenu.menu.getChildren().add(freemode);

        lvl1.setFont(GameMenu.font);
        lvl1.setLayoutX((float)winWidth/5);
        offset += increment;
        lvl1.setLayoutY((float)winHeight/5 + offset);
        lvl1.setFill(hover);
        GameMenu.menu.getChildren().add(lvl1);

        lvl2.setFont(GameMenu.font);
        lvl2.setLayoutX((float)winWidth/5);
        offset += increment;
        lvl2.setLayoutY((float)winHeight/5 + offset);
        lvl2.setFill(hover);
        GameMenu.menu.getChildren().add(lvl2);

        lvl3.setFont(GameMenu.font);
        lvl3.setLayoutX((float)winWidth/5);
        offset += increment;
        lvl3.setLayoutY((float)winHeight/5 + offset);
        lvl3.setFill(hover);
        GameMenu.menu.getChildren().add(lvl3);

        lvl4.setFont(GameMenu.font);
        lvl4.setLayoutX((float)winWidth/5);
        offset += increment;
        lvl4.setLayoutY((float)winHeight/5 + offset);
        lvl4.setFill(hover);
        GameMenu.menu.getChildren().add(lvl4);

        lvl5.setFont(GameMenu.font);
        lvl5.setLayoutX((float)winWidth/5);
        offset += increment;
        lvl5.setLayoutY((float)winHeight/5 + offset);
        lvl5.setFill(hover);
        GameMenu.menu.getChildren().add(lvl5);

        lvl6.setFont(GameMenu.font);
        lvl6.setLayoutX((float)winWidth/5);
        offset += increment;
        lvl6.setLayoutY((float)winHeight/5 + offset);
        lvl6.setFill(hover);
        GameMenu.menu.getChildren().add(lvl6);

        checkText();
    }
    public static void selectMode() {
        Scene sceneLS = new Scene(GameMenu.construct(),winWidth,winHeight);
        App.getStage().setScene(sceneLS);
        MenuSelectorLevel.startup();
        sceneLS.setOnKeyPressed((keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S){
                MenuSelectorLevel.buttonFunc.next();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W){
                MenuSelectorLevel.buttonFunc.prev();
            }
            if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER){
                MenuSelectorLevel.buttonFunc.func();
            }
            MenuSelector.checkText();
        }));
    }

    public static void checkText() {
        lvl1.setFill(hover);
        lvl2.setFill(hover);
        lvl3.setFill(hover);
        lvl4.setFill(hover);
        lvl5.setFill(hover);
        lvl6.setFill(hover);
        freemode.setFill(hover);
        switch (selButton){
            case FREEMODE:{
                freemode.setFill(sel);
                break;
            }
            case LVL1:{
                lvl1.setFill(sel);
                break;
            }
            case LVL2:{
                lvl2.setFill(sel);
                break;
            }
            case LVL3:{
                lvl3.setFill(sel);
                break;
            }
            case LVL4:{
                lvl4.setFill(sel);
                break;
            }
            case LVL5:{
                lvl5.setFill(sel);
                break;
            }
            case LVL6:{
                lvl6.setFill(sel);
                break;
            }
        }
    }

    public enum buttonFunc{
        FREEMODE,
        LVL1,
        LVL2,
        LVL3,
        LVL4,
        LVL5,
        LVL6;

        public static void prev() {
            int current = selButton.ordinal();
            if(current > 0){
                selButton = buttonFunc.values()[current-1];
            }
            else{
                selButton = buttonFunc.values()[values().length-offsetLevel];
            }
            checkText();
        }

        public static void next() {
            int current = selButton.ordinal();
            if(current < values().length-offsetLevel){
                selButton = buttonFunc.values()[current+1];
            }
            else{
                selButton = buttonFunc.FREEMODE;
            }
            checkText();
        }

        public static void func() {
            switch (selButton){
                case FREEMODE:{Game.startGame(0); break;}
                case LVL1:{Game.startGame(1); break;}
                case LVL2:{Game.startGame(2); break;}
                case LVL3:{Game.startGame(3); break;}
                case LVL4:{Game.startGame(4); break;}
                case LVL5:{Game.startGame(5); break;}
                case LVL6:{Game.startGame(6); break;}
            }
        }

    }
}