package Frames;

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
import org.example.*;


import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import static Frames.App.*;

public class Game {
    static int score = 0;
    static LinkedList<Enemy> thisGameList;
    public static void startGame(int i) {
        Utility.saveSettings(Utility.loadSettings(0),Utility.loadSettings(1),Utility.loadSettings(2), Utility.loadSettings(3)); //From file to programm
        int levelLen = Utility.loadLevel(i); // Level lenght from file
        SoundHandler.takeMusicAdress(i);     //Sound handler from file

        thisGameList = Enemy.constructEnemyFromFile(i);

        Group playfieldLayout = new Group();
        Scene sceneGAME = new Scene(playfieldLayout,winWidth,winHeight);
        Stage mainStage = App.getStage();

        mainStage.setScene(sceneGAME);
        sceneGAME.setFill(Color.BLACK);

        ImageView gui = new ImageView(Utility.getImageRes("/menu/GUI2.png"));
        ImageView backgr = new ImageView(Utility.getImageRes("/levelbackgr/lvl"+ i +".png"));


        Box levprogress = new Box();
        levprogress.setHeight(30);
        levprogress.setWidth(1);
        levprogress.setTranslateX(5 + (double)winWidth/5);
        levprogress.setLayoutY(40);
        PhongMaterial boxCol = new PhongMaterial();
        boxCol.setDiffuseColor(Utility.getColorFromPallete(22));
        levprogress.setMaterial(boxCol);

        playfieldLayout.getChildren().add(backgr);
        for (Enemy enemy : thisGameList) {
            playfieldLayout.getChildren().add(enemy.pic);
        }
        playfieldLayout.getChildren().add(gui);
        playfieldLayout.getChildren().add(levprogress);

        Text hpT = new Text("200");
        hpT.setFont(GameMenu.font);
        hpT.setFill(Utility.getColorFromPallete(7));
        hpT.setLayoutX(15);
        hpT.setLayoutY(55);

        Player mainP = new Player(i);

        ImageView playerBody = mainP.pic;

        playfieldLayout.getChildren().add(playerBody);
        playfieldLayout.getChildren().add(hpT);

        SoundHandler.musicInit();
Thread gameCycle = new Thread(() -> {
    int progress = 0;
    boolean playerIsAlive = true;
    boolean endNotMet = true;
    long startTime = System.currentTimeMillis();
    long lastCycle = startTime;
    activateEnemyCycle();
    while(playerIsAlive && endNotMet){
        if(progress > levelLen){endNotMet = false;}
        if(mainP.hp < 0){playerIsAlive = false;}
        startTime = System.currentTimeMillis();
        while (startTime - lastCycle > 100){
            levprogress.setWidth((double) (4*winWidth/5)*progress/levelLen);
            levprogress.setTranslateX(5 + (double)winWidth/5 + levprogress.getWidth()/2);
            if(mainP.angle>0){
                mainP.angle--;}
            if(mainP.angle<0){
                mainP.angle++;}
            progress++;
            lastCycle = System.currentTimeMillis();
        }
    }
    Utility.debugOutput("GAME CYCLE OVER");
    if(!endNotMet && playerIsAlive && i != 0){
        Utility.progressLevel(i);
    }
    thisGameList.clear();
    MenuLevelClear.gameEnd(playerIsAlive, endNotMet, score, i);
});
        gameCycle.start();
        Timeline guiAnim = new Timeline(new KeyFrame(Duration.millis(50), event -> {
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
                mainP.angle--;}
            if ((keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) && mainP.angle<30){
                mainP.angle++;}
            if (keyEvent.getCode() == KeyCode.PAGE_UP){
                gameCycle.interrupt();
                MenuLevelClear.gameEnd(true, false, score, i);
            }
            if (keyEvent.getCode() == KeyCode.PAGE_DOWN){
                gameCycle.interrupt();
                MenuLevelClear.gameEnd(false, true, score, i);
            }
        }));

    }

    static void activateEnemyCycle(){
        new Thread(() -> {
        for(int i = 0; i< thisGameList.size(); i++){
            try {
                Thread.sleep(thisGameList.element().waitTo);
                thisGameList.element().animationStart();
                thisGameList.remove();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }}).start();
    }


    //Special mode. Yes, its copy-paste of what is above.
    // Still easier than sorting everything in different methods
    public static void startSpecialGame() {
        Group playfieldLayout = new Group();
        Scene sceneGAME = new Scene(playfieldLayout,winWidth,winHeight);
        Stage mainStage = App.getStage();

        mainStage.setScene(sceneGAME);
        sceneGAME.setFill(Color.BLACK);

        ImageView gui = new ImageView(Utility.getImageRes("/menu/GUI2.png"));
        gui.setX(0);
        gui.setY(0);
        ImageView backgr = new ImageView(Utility.getImageRes("/levelbackgr/lvl0.png"));
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


        Text hpT = new Text("Speed is 38240 c ");
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
            double speed = 1.147e+10;
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
                    hpT3.setText((dist - progress) + " km");
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


}
