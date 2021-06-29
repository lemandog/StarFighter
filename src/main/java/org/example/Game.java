package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


import static org.example.App.*;

public class Game {
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

        Box levprogress = new Box();
        levprogress.setHeight(30);
        levprogress.setWidth(1);
        levprogress.setTranslateX(5 + (double)winWidth/5);
        levprogress.setLayoutY(40);
        PhongMaterial boxCol = new PhongMaterial();
        boxCol.setDiffuseColor(Utility.getColorFromPallete(22));
        levprogress.setMaterial(boxCol);

        playfieldLayout.getChildren().add(backgr);
        playfieldLayout.getChildren().add(gui);
        playfieldLayout.getChildren().add(levprogress);

        Text hT = new Text("HELLO THERE");
        hT.setFont(GameMenu.font);
        hT.setFill(Utility.getColorFromPallete(7));
        hT.setLayoutX((float)winWidth/5);
        hT.setLayoutY((float)winHeight/5);

        Text hpT = new Text("200");
        hpT.setFont(GameMenu.font);
        hpT.setFill(Utility.getColorFromPallete(7));
        hpT.setLayoutX(15);
        hpT.setLayoutY(55);
        Player mainP = new Player(i);

        playfieldLayout.getChildren().add(mainP.pic);
        playfieldLayout.getChildren().add(hpT);
        playfieldLayout.getChildren().add(hT);
        sceneGAME.setOnKeyPressed((keyEvent -> {
            mainP.takeAngle();
            if ((keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) && angle<30){
                hT.setText(String.valueOf(angle));
                angle++;}
            if ((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) && angle>-30){
                hT.setText(String.valueOf(angle));
                angle--;}
        }));



Thread gameCycle = new Thread(() -> {
    int progress = 0;

    boolean playerIsAlive = true;
    boolean endNotMet = true;
    long startTime = System.currentTimeMillis();
    long lastCycle = startTime;
Timeline guiAnim = new Timeline(new KeyFrame(Duration.millis(100), event -> {
        //SET PLAYER STATE
        //UPDATE UI

        hpT.setText(String.valueOf(mainP.hp));
    }));
    guiAnim.setCycleCount(Timeline.INDEFINITE);
    guiAnim.play();

    while(playerIsAlive && endNotMet){
        startTime = System.currentTimeMillis();
        if(progress > 10000){endNotMet = false;}
        if(mainP.hp < 0){playerIsAlive = false;}
        while (startTime - lastCycle > 150){
            levprogress.setWidth((double) (4*winWidth/5)*progress/10000);
            levprogress.setTranslateX(5 + (double)winWidth/5 + levprogress.getWidth()/2);
            if(angle>0){
                mainP.angle--;}
            if(angle<0){
                mainP.angle++;}
            mainP.takeAngle();
            hT.setText(String.valueOf(mainP.angle));
            progress++;
            lastCycle = System.currentTimeMillis();
            System.out.println((double) (4*winWidth/5)*progress/10000);
        }
    }

});
        gameCycle.start();
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
