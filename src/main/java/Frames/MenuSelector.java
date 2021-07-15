package Frames;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.Utility;

import static Frames.App.winHeight;
import static Frames.App.winWidth;

public class MenuSelector {
    static Color hover = Utility.getColorFromPallete(22);
    static Color hidden = Utility.getColorFromPallete(25);
    static Color sel = Utility.getColorFromPallete(19);
    static Color additional = Utility.getColorFromPallete(26);

    public static Text start = new Text("Start");
    public static Text info = new Text("Info");
    public static Text exit = new Text("Exit");
    public static Text special = new Text("SpecialMode");

    static buttonFunc selButton = buttonFunc.START;

    public static void startup(){
        int offset = 200;
        int increment = 40;
        MenuSelector.start.setFont(GameMenu.font);
        MenuSelector.start.setLayoutX((float)winWidth/5);
        MenuSelector.start.setLayoutY((float)winHeight/5 + offset);
        GameMenu.menu.getChildren().add(MenuSelector.start);


        MenuSelector.info.setFont(GameMenu.font);
        MenuSelector.info.setLayoutX((float)winWidth/5);
        offset += increment;
        MenuSelector.info.setLayoutY((float)winHeight/5 + offset);
        GameMenu.menu.getChildren().add(MenuSelector.info);


        MenuSelector.exit.setFont(GameMenu.font);
        MenuSelector.exit.setLayoutX((float)winWidth/5);
        offset += increment;
        MenuSelector.exit.setLayoutY((float)winHeight/5 + offset);
        GameMenu.menu.getChildren().add(MenuSelector.exit);


        MenuSelector.special.setFont(GameMenu.font);
        MenuSelector.special.setLayoutX((float)winWidth/5);
        offset += increment;
        MenuSelector.special.setLayoutY((float)winHeight/5 + offset);
        GameMenu.menu.getChildren().add(MenuSelector.special);

        MenuSelector.checkText();
    }

    public static void checkText() {
        switch (selButton){
            case START:{
                start.setFill(sel);
                info.setFill(hover);
                exit.setFill(hover);
                special.setFill(hidden);
                break;
            }
            case INFO:{
                start.setFill(hover);
                info.setFill(sel);
                exit.setFill(hover);
                special.setFill(hidden);
                break;
            }
            case EXIT:{
                start.setFill(hover);
                info.setFill(hover);
                exit.setFill(sel);
                special.setFill(hidden);
                break;
            }
            case SPECIAL:{
                start.setFill(hover);
                info.setFill(hover);
                exit.setFill(hover);
                special.setFill(sel);
                break;
            }
        }
    }

    public enum buttonFunc{
        START,
        INFO,
        EXIT,
        SPECIAL;

        public static void prev() {
            int current = selButton.ordinal();
            if(current > 0){
                selButton = buttonFunc.values()[current-1];
            }
            else{
                selButton = buttonFunc.EXIT;
            }
        }

        public static void next() {
            int current = selButton.ordinal();
            if(current < values().length-2){
                selButton = buttonFunc.values()[current+1];
            }
            else{
                selButton = buttonFunc.START;
            }
        }

        public static void func() {
            switch (selButton){
                case EXIT:{
                    System.exit(0); break;}
                case INFO:{
                    Utility.constructInfoFrame(); break;}
                case START:{
                    Game.selectMode(); break;}
                case SPECIAL:{
                    Game.startSpecialGame(); break;}
            }
        }
        public static void special() {
            selButton = buttonFunc.values()[values().length-1];
        }

    }
}
