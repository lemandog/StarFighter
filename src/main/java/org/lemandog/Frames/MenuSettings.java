package org.lemandog.Frames;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import org.lemandog.Utility;
import org.lemandog.jdbc.Control;

import static org.lemandog.Frames.MenuSelector.*;
import static org.lemandog.Frames.App.winHeight;
import static org.lemandog.Frames.App.winWidth;

public class MenuSettings {
    public static int volume = Integer.parseInt(Control.loadSettings(1));
    public static Text volumeT = new Text("Music vol: " + volume);
    public static int volumeS = Integer.parseInt(Control.loadSettings(2));
    public static Text volumeTS = new Text("Sound vol: " + volumeS);
    public static boolean comicShow = Boolean.parseBoolean(Control.loadSettings(3));
    public static Text comicT = new Text("Show plot comics: " + comicShow);
    public static Text author = new Text("Authors");
    public static Text resetSave = new Text("Reset Save file");
    static public boolean showDebug = Boolean.parseBoolean(Control.loadSettings(4));
    public static Text debugState = new Text("Show debug text: " + showDebug);
    public static Text toMainMenu = new Text("Return to menu");

    static buttonFunc selButton = buttonFunc.VOLUME;

    public static void startup(){
        int offset = 200;
        int increment = 40;

        volumeT.setFont(GameMenu.font);
        volumeT.setLayoutX((float)winWidth/8);
        volumeT.setLayoutY((float)winHeight/5 + (float) offset);
        offset += increment;
        GameMenu.menu.getChildren().add(volumeT);

        volumeTS.setFont(GameMenu.font);
        volumeTS.setLayoutX((float)winWidth/8);
        volumeTS.setLayoutY((float)winHeight/5 + (float) offset);
        offset += increment;
        GameMenu.menu.getChildren().add(volumeTS);

        comicT.setFont(GameMenu.font);
        comicT.setLayoutX((float)winWidth/8);
        comicT.setLayoutY((float)winHeight/5 + (float) offset);
        offset += increment;
        GameMenu.menu.getChildren().add(comicT);

        author.setFont(GameMenu.font);
        author.setLayoutX((float)winWidth/8);
        author.setLayoutY((float)winHeight/5 + offset);
        offset += increment;
        GameMenu.menu.getChildren().add(author);

        resetSave.setFont(GameMenu.font);
        resetSave.setLayoutX((float)winWidth/8);
        resetSave.setLayoutY((float)winHeight/5 + offset);
        offset += increment;
        GameMenu.menu.getChildren().add(resetSave);

        debugState.setFont(GameMenu.font);
        debugState.setLayoutX((float)winWidth/8);
        debugState.setLayoutY((float)winHeight/5 + offset);
        offset += increment;
        GameMenu.menu.getChildren().add(debugState);

        toMainMenu.setFont(GameMenu.font);
        toMainMenu.setLayoutX((float)winWidth/8);
        toMainMenu.setLayoutY((float)winHeight/5 + offset);
        GameMenu.menu.getChildren().add(toMainMenu);

        checkText();
    }
    public static void selectMode() {
        Scene sceneLS = new Scene(GameMenu.construct(),winWidth,winHeight);
        App.getStage().setScene(sceneLS);
        startup();
        sceneLS.setOnKeyPressed((keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S){
                buttonFunc.next();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W){
                buttonFunc.prev();
            }
            if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER){
                buttonFunc.func();
            }
            if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT){
                if(volume<100 && selButton==buttonFunc.VOLUME){
                    volume++;
                    volumeT.setText("Music vol: " + volume);
                }
                if(volumeS<100 && selButton==buttonFunc.VOLUME_S){
                    volumeS++;
                    volumeTS.setText("Sound vol: " + volumeS);
                }
            }
            if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT){
                if(volume>0 && selButton==buttonFunc.VOLUME){
                    volume--;
                    volumeT.setText("Music vol: " + volume);
                }
                if(volumeS>0 && selButton==buttonFunc.VOLUME_S){
                    volumeS--;
                    volumeTS.setText("Sound vol: " + volumeS);
                }
            }
            MenuSelector.checkText();
        }));
    }

    public static void checkText() {
        volumeT.setFill(hover);
        volumeTS.setFill(hover);
        comicT.setFill(hover);
        author.setFill(hover);
        resetSave.setFill(hover);
        debugState.setFill(hover);
        toMainMenu.setFill(hover);
        switch (selButton){
            case VOLUME:{
                volumeT.setFill(sel);
                break;
            }
            case VOLUME_S:{
                volumeTS.setFill(sel);
                break;
            }
            case PRE_LEVEL_COMIC:{
                comicT.setFill(sel);
                break;
            }
            case AUTHOR:{
                author.setFill(sel);
                break;
            }
            case RESET_SAVE:{
                resetSave.setFill(sel);
                break;
            }
            case DEBUG:{
                debugState.setFill(sel);
                break;
            }
            case TO_MAIN_MENU:{
                toMainMenu.setFill(sel);
                break;
            }
        }
    }

    public enum buttonFunc{
        VOLUME,
        VOLUME_S,
        PRE_LEVEL_COMIC,
        AUTHOR,
        RESET_SAVE,
        DEBUG,
        TO_MAIN_MENU;

        public static void prev() {
            int current = selButton.ordinal();
            if(current > 0){
                selButton = buttonFunc.values()[current-1];
            }
            else{
                selButton = buttonFunc.values()[values().length-1];
            }
            checkText();
        }

        public static void next() {
            int current = selButton.ordinal();
            if(current < values().length-1){
                selButton = buttonFunc.values()[current+1];
            }
            else{
                selButton = buttonFunc.values()[0];
            }
            checkText();
        }

        public static void func() {
            switch (selButton){
                case PRE_LEVEL_COMIC:{
                    comicShow=!comicShow;
                    comicT.setText("Show plot comics: " + comicShow);
                    break;}
                case DEBUG:{
                    showDebug = !showDebug;
                    debugState.setText("Show debug text: " + showDebug);
                    break;
                }
                case AUTHOR:{Utility.constructAuthorFrame(); break;}
                case RESET_SAVE:{
                    resetSave.setFill(additional);
                    Control.resetSave();break;
                }
                case TO_MAIN_MENU:{
                    Control.saveSettings(volume,volumeS,comicShow,showDebug);
                    App.sceneMM = new Scene(GameMenu.construct());
                    MenuSelector.startup();
                    App.getStage().setScene(App.sceneMM);
                    break;}
            }
        }

    }
}