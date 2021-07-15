package Frames;

import javafx.scene.Group;
import javafx.scene.Scene;
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

        Text gameover = new Text();
        gameover.setFill(sel);
        gameover.setFont(GameMenu.font);

        gameover.setX((double) winWidth / 4);
        gameover.setY((double) winHeight / 4);

        if (playerIsAlive && !endNotMet) {
            gameover.setText("Level clear!");
            outcome = true;
        } else {
            gameover.setText("Mission failed!");
            outcome = false;
        }
        gameoverLayout.getChildren().add(gameover);

        Text scoreT = new Text(String.format("%06d", score));
        scoreT.setX((double) winWidth / 3);
        scoreT.setY((double) winHeight / 3 + 50);
        scoreT.setFill(sel);
        scoreT.setFont(GameMenu.font);
        gameoverLayout.getChildren().add(scoreT);

        start.setX(0);
        start.setY((double) 2 * winHeight / 3);
        start.setText("To level selector");
        start.setFont(GameMenu.font);
        gameoverLayout.getChildren().add(start);

        exit.setX((double) 3 * winWidth / 4);
        exit.setY((double) 2 * winHeight / 3);
        exit.setText("Exit");
        exit.setFont(GameMenu.font);
        gameoverLayout.getChildren().add(exit);
        checkText();

        App.getStage().setScene(GameEnd);


    }

    public static void checkText() {
        switch (selButton) {
            case START: {
                start.setFill(sel);
                exit.setFill(hover);
                break;
            }
            case EXIT: {
                start.setFill(hover);
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
                    if (outcome) {
                        Game.selectMode();
                    }
                    break;
                }
            }
        }
    }
}
