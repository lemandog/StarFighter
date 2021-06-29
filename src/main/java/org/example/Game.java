package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.playercontrol.Controller;

import static org.example.App.*;

public class Game {
    static Image backg;
    static ImageView backgView;
    public static int angle = 0;
    public static void startGame(int i) {
        Group playfieldLayout = new Group();
        Scene sceneGAME = new Scene(playfieldLayout,winWidth,winHeight);
        Stage mainStage = App.getStage();
        mainStage.setScene(sceneGAME);
        sceneGAME.setFill(Color.BLACK);

        ImageView gui = new ImageView("menu/GUI2.png");
        gui.setX(0);
        gui.setY(0);
        ImageView backgr = new ImageView("levelbackgr/lvl"+ i +".png");
        backgr.setX(0);
        backgr.setY(0);

        playfieldLayout.getChildren().add(backgr);
        playfieldLayout.getChildren().add(gui);
        Text hT = new Text("HELLO THERE");
        hT.setFont(GameMenu.font);
        hT.setFill(Color.WHEAT);
        hT.setLayoutX((float)winWidth/5);
        hT.setLayoutY((float)winHeight/5);

        Text hpT = new Text("200");
        hpT.setFont(GameMenu.font);
        hpT.setFill(Utility.getColorFromPallete(9));
        hpT.setLayoutX(15);
        hpT.setLayoutY(55);


        playfieldLayout.getChildren().add(hpT);
        playfieldLayout.getChildren().add(hT);
        sceneGAME.setOnKeyPressed((keyEvent -> {
            System.out.println("KEY EVENT!");
            if ((keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) && angle<90){
                hT.setText(String.valueOf(angle));
                angle++;}
            if ((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) && angle>-90){
                hT.setText(String.valueOf(angle));
                angle--;}
        }));



Thread gameCycle = new Thread(() -> {
    int progress = 0;
    int hp = 200;

    boolean playerIsAlive = true;
    boolean endNotMet = true;
    long startTime = System.currentTimeMillis();
    long lastCycle = startTime;

Timeline guiAnim = new Timeline(new KeyFrame(Duration.millis(100), event -> {
        //SET PLAYER STATE
        //UPDATE UI
        Controller.moveAPlayer(angle);
        hpT.setText(String.valueOf(hp));
    }));
    guiAnim.setCycleCount(Timeline.INDEFINITE);
    guiAnim.play();

    while(playerIsAlive && endNotMet){
        startTime = System.currentTimeMillis();
        if(progress > 10000){endNotMet = false;}
        if(hp < 0){playerIsAlive = false;}
        while (startTime - lastCycle > 1000){
            progress++;
            lastCycle = System.currentTimeMillis();
            System.out.println("Cycle!");
        }
    }

});
        gameCycle.start();
    }

    private static Parent construct(int i) {
        Group gamefield = new Group();
        backg = startStarField();
        backgView = new ImageView();
        gamefield.getChildren().add(backgView);
        return gamefield;

    }

    private static Image startStarField() {
        WritableImage output = new WritableImage(winWidth,winHeight);
        PixelWriter outPix = output.getPixelWriter();
        for (int x = 0; x<winWidth; x++){
            for (int y = 0; y<winHeight; y++) {
                if(Math.random()*10 > 9){outPix.setColor(x,y,Color.WHITE);}
                else{outPix.setColor(x,y,Color.BLACK);}
            }}
        return output;
    }

    private static Image moveBackg() {
        WritableImage output = new WritableImage(winWidth,winHeight);
        PixelReader inPix = output.getPixelReader();
        PixelWriter outPix = output.getPixelWriter();
        for (int x = 0; x<winWidth; x++) {
            if(Math.random()*10 > 9){outPix.setColor(x,winHeight-1,Color.WHITE);}
            else{outPix.setColor(x,winHeight-1,Color.BLACK);}
        }
        for (int x = 0; x<winWidth; x++){
            for (int y = 0; y<winHeight-1; y++) {
                outPix.setColor(x,y,inPix.getColor(x,y+1));
            }}
        return output;
    }

    public static void startSpecialGame() {
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
}
