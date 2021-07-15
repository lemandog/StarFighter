package Frames;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.example.Utility;

import static Frames.App.winHeight;
import static Frames.App.winWidth;
public class MenuLevelClear {
    static Color sel = Utility.getColorFromPallete(19);
    static Color hover = Utility.getColorFromPallete(22);

    public static Text start = new Text("Level selector");
    public static Text exit = new Text("Exit");

    static boolean outcome;
    static int sLevel;
    static buttonFunc selButton = buttonFunc.START;


    public static void gameEnd(boolean playerIsAlive, boolean endNotMet, int score, int level) {
        sLevel = level;
        Group gameoverLayout = new Group();

        Scene GameEnd = new Scene(gameoverLayout, winWidth, winHeight);
        GameEnd.setOnKeyPressed((keyEvent -> {
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.LEFT) {
                buttonFunc.next();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.RIGHT) {
                buttonFunc.prev();
            }
            if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
                buttonFunc.func();
            }
            MenuLevelClear.checkText();
        }));

        GameEnd.setFill(Color.BLACK);
        ImageView backg;
        Text gameover = new Text();
        gameover.setFill(sel);
        gameover.setFont(GameMenu.font);

        gameover.setX((double) winWidth / 4);
        gameover.setY((double) winHeight / 8);

        if (playerIsAlive && !endNotMet) {
            gameover.setText("Level clear!");
            backg = new ImageView(Utility.getImageRes("/menu/End1.png"));
            outcome = true;
        } else {
            gameover.setText("Mission failed!");
            backg = new ImageView(Utility.getImageRes("/menu/End2.png"));
            outcome = false;
        }
        gameoverLayout.getChildren().add(backg);
        gameoverLayout.getChildren().add(gameover);

        Text scoreT = new Text(String.format("%06d", score));
        scoreT.setX((double) winWidth / 3);
        scoreT.setY((double) winHeight / 8 + 50);
        scoreT.setFill(sel);
        scoreT.setFont(GameMenu.font);
        gameoverLayout.getChildren().add(scoreT);

        start.setX(20);
        start.setY((double) 2 * winHeight / 3 + 60);
        start.setText("To level selector");
        start.setFont(GameMenu.font);
        gameoverLayout.getChildren().add(start);

        exit.setX((double) 3 * winWidth / 4 + 20);
        exit.setY((double) 2 * winHeight / 3 + 60);
        exit.setText("Exit");
        exit.setFont(GameMenu.font);
        gameoverLayout.getChildren().add(exit);
        checkText();

        App.getStage().setScene(GameEnd);


    }

    public static void checkText() {
        exit.setFill(hover);
        start.setFill(hover);
        switch (selButton) {
            case START: {
                start.setFill(sel);
                break;
            }
            case EXIT: {
                exit.setFill(sel);
                break;
            }
        }
    }

    public enum buttonFunc {
        START,
        EXIT;
        public static void prev() {
            int current = selButton.ordinal();
            if(current > 0){
                selButton = MenuLevelClear.buttonFunc.values()[current-1];
            }
            else{
                selButton = MenuLevelClear.buttonFunc.values()[values().length-1];
            }
        }

        public static void next() {
            int current = selButton.ordinal();
            if(current < values().length-1){
                selButton = MenuLevelClear.buttonFunc.values()[current+1];
            }
            else{
                selButton = MenuLevelClear.buttonFunc.START;
            }
        }

        public static void func() {
            switch (MenuLevelClear.selButton) {
                case EXIT: {
                    System.exit(0);
                    break;
                }
                case START: {
                    if (outcome) {Utility.progressLevel(sLevel);}
                    MenuSelectorLevel.offsetLevel = Utility.getAvailableLevels();
                    MenuSelectorLevel.selectMode();
                    break;
                }
            }
        }
    }
}
