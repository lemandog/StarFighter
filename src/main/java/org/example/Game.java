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


import java.util.concurrent.atomic.AtomicBoolean;

import static org.example.App.*;

public class Game {
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

        Text pause = new Text("GAME IS PAUSED!");
        pause.setFont(GameMenu.font);
        pause.setFill(Utility.getColorFromPallete(2));
        pause.setLayoutX((float)winWidth/4);
        pause.setLayoutY((float)winHeight/2);
        pause.setVisible(false);

        Text hpT = new Text("200");
        hpT.setFont(GameMenu.font);
        hpT.setFill(Utility.getColorFromPallete(7));
        hpT.setLayoutX(15);
        hpT.setLayoutY(55);
        Player mainP = new Player(i);

        ImageView playerBody = mainP.pic;

        playfieldLayout.getChildren().add(playerBody);
        playfieldLayout.getChildren().add(hpT);
        playfieldLayout.getChildren().add(hT);
        playfieldLayout.getChildren().add(pause);

Thread gameCycle = new Thread(() -> {
    int progress = 0;

    boolean playerIsAlive = true;
    boolean endNotMet = true;
    long startTime = System.currentTimeMillis();
    long lastCycle = startTime;

    while(playerIsAlive && endNotMet){
        startTime = System.currentTimeMillis();
        if(progress > 10000){endNotMet = false;}
        if(mainP.hp < 0){playerIsAlive = false;}
        while (startTime - lastCycle > 150){
            levprogress.setWidth((double) (4*winWidth/5)*progress/10000);
            levprogress.setTranslateX(5 + (double)winWidth/5 + levprogress.getWidth()/2);
            if(mainP.angle>0){
                mainP.angle--;}
            if(mainP.angle<0){
                mainP.angle++;}
            hT.setText(String.valueOf(mainP.angle));
            progress++;
            lastCycle = System.currentTimeMillis();
        }
    }
});
        gameCycle.start();
        Timeline guiAnim = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            System.out.println(playerBody.getX());

            if(playerBody.getX()<winWidth && playerBody.getX()>0){
                playerBody.setX(mainP.angle + playerBody.getX());
            } else {
                if(playerBody.getX()>=winWidth){
                    playerBody.setX(1);}
                if(playerBody.getX()<=0){
                    playerBody.setX(winWidth-20);}
            }

            playerBody.setImage(mainP.takeAngle().getImage());
            hpT.setText(String.valueOf(mainP.hp));
        }));

        guiAnim.setCycleCount(Timeline.INDEFINITE);
        guiAnim.play();

        sceneGAME.setOnKeyPressed((keyEvent -> {
            mainP.takeAngle();
            if ((keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) && mainP.angle>-30){
                hT.setText(String.valueOf(mainP.angle));
                mainP.angle--;}
            if ((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) && mainP.angle<30){
                hT.setText(String.valueOf(mainP.angle));
                mainP.angle++;}
        }));

    }



    public static void startSpecialGame() {
        Group playfieldLayout = new Group();
        Scene sceneGAME = new Scene(playfieldLayout,winWidth,winHeight);
        Stage mainStage = App.getStage();

        mainStage.setScene(sceneGAME);
        sceneGAME.setFill(Color.BLACK);

        ImageView gui = new ImageView("menu/GUI2.png");
        gui.setX(0);
        gui.setY(0);
        ImageView backgr = new ImageView("levelbackgr/lvl0.png");
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


        Text hpT = new Text("Speed is 300 000km/s. ");
        hpT.setFont(GameMenu.font);
        hpT.setFill(Utility.getColorFromPallete(7));
        hpT.setLayoutX(15);
        hpT.setLayoutY(120);

        Text hpT2 = new Text("To alpha-centauri: ");
        hpT2.setFont(GameMenu.font);
        hpT2.setFill(Utility.getColorFromPallete(7));
        hpT2.setLayoutX(15);
        hpT2.setLayoutY(160);

        Text hpT3 = new Text();
        hpT3.setFont(GameMenu.font);
        hpT3.setFill(Utility.getColorFromPallete(7));
        hpT3.setLayoutX(15);
        hpT3.setLayoutY(200);

        Text hpT4 = new Text("Do not stray away!");
        hpT4.setFont(GameMenu.font);
        hpT4.setFill(Utility.getColorFromPallete(7));
        hpT4.setLayoutX(15);
        hpT4.setLayoutY(240);

        Text hpT5 = new Text("YOU WON! Congrats");
        hpT5.setFont(GameMenu.font);
        hpT5.setFill(Utility.getColorFromPallete(7));
        hpT5.setLayoutX(15);
        hpT5.setLayoutY(280);
        hpT5.setVisible(false);

        Player mainP = new Player(0);
        ImageView playerBody = mainP.pic;

        playfieldLayout.getChildren().add(playerBody);
        playfieldLayout.getChildren().add(hpT);
        playfieldLayout.getChildren().add(hpT2);
        playfieldLayout.getChildren().add(hpT3);
        playfieldLayout.getChildren().add(hpT4);
        playfieldLayout.getChildren().add(hpT5);
        AtomicBoolean checkWall = new AtomicBoolean(false);
        Thread gameCycle = new Thread(() -> {
            double progress = 0;
            double speed = 300000;
            boolean endNotMet = true;
            long startTime = System.currentTimeMillis();
            long lastCycle = startTime;
            double dist = 4.13e+13;
            while(endNotMet){
                startTime = System.currentTimeMillis();
                if(progress > dist){
                    hpT5.setVisible(true);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress = 0;
                    if (hpT2.getText().equals("To alpha-centauri: ")){hpT2.setText("To Earth");}
                    else {hpT2.setText("To alpha-centauri: ");}
                    hpT5.setVisible(false);
                }

                while (startTime - lastCycle > 1000){
                    mainP.angle = (int) (mainP.angle + ((Math.random() - 0.5)*6));
                    if(checkWall.get()){progress=0;playerBody.setX((double) winWidth/2); checkWall.set(false);}
                    levprogress.setWidth((double) (4*winWidth/5)*progress/dist);
                    levprogress.setTranslateX(5 + (double)winWidth/5 + levprogress.getWidth()/2);
                    hpT3.setText(String.valueOf((dist - progress) + " km"));
                    if(mainP.angle>0){
                        mainP.angle--;}
                    if(mainP.angle<0){
                        mainP.angle++;}
                    progress = progress + speed;
                    lastCycle = System.currentTimeMillis();
                }
            }

        });
        gameCycle.start();
        Timeline guiAnim = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if(playerBody.getX()<winWidth && playerBody.getX()>0){
                playerBody.setX(mainP.angle + playerBody.getX());
            } else {
                if(playerBody.getX()>=winWidth || playerBody.getX()<=0){
                    checkWall.set(true);
                }

            }

            playerBody.setImage(mainP.takeAngle().getImage());
        }));

        guiAnim.setCycleCount(Timeline.INDEFINITE);
        guiAnim.play();

        sceneGAME.setOnKeyPressed((keyEvent -> {
            mainP.takeAngle();
            if ((keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) && mainP.angle>-30){
                mainP.angle--;}
            if ((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) && mainP.angle<30){
                mainP.angle++;}
        }));
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
