package org.lemandog;

import org.lemandog.Frames.App;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.lemandog.Frames.App.winWidth;
import static org.lemandog.Frames.Game.mainP;
import static org.lemandog.Utility.documentsPath;

public class Enemy {
    public long waitTo; //Nanoseconds.
    long startupTime;
    double speed;
    int type;
    int mod;
    int angle;
    public boolean alive = true;
    boolean onScreen = true;
    boolean alreadyHit = false;
    int score;
    int damage;
    int collideDamage = 20;
    public ImageView pic = new ImageView();
    public Timeline ememyMovement;
    double[] coordinates = new double[] {(double) App.winWidth/2,40};

    double multiplier;
    double Vmultiplier;
    double add;

    Enemy(int type, long timer, int mod, double speed, double multiplier, double Vmultipluer, double add){
        this.damage = 5*type;
        this.type = type;
        this.speed = speed;
        this.mod = mod;
        this.angle = 0;
        this.waitTo = startupTime + timer;
        this.startupTime = System.nanoTime();
        this.score = (int) (type*speed*mod);

        this.multiplier = multiplier;
        this.Vmultiplier = Vmultipluer;
        this.add = add;

        pic.setImage(getImagePos());
        pic.setVisible(false);
    }

    public static LinkedList<Enemy> constructEnemyFromFile(int level){
        LinkedList<Enemy> enemyList =  new <Enemy>LinkedList();
        try {
            Scanner scanner = new Scanner(new File(documentsPath + File.separator + "/levelInfo/" + level + ".txt"));
            scanner.nextLine(); //Skipping info table
            while(scanner.hasNextLine()){
                Utility.debugOutput(String.valueOf(scanner.hasNextLine()));
                int type = scanner.nextInt();
                long timer = scanner.nextLong();
                int mod = scanner.nextInt();
                int speed = scanner.nextInt();
                double multiplier = scanner.nextDouble();
                double Vmultiplier = scanner.nextDouble();
                double add = scanner.nextDouble();
                Utility.debugOutput("ENEMY GEN "+ type + " " + multiplier);
                enemyList.add(new Enemy(type,timer,mod,speed,multiplier,Vmultiplier,add));
            }
        } catch (NullPointerException | NoSuchElementException | FileNotFoundException e) {
            Utility.debugOutput("NO SUCH FILE FOUND OR CORRUPTED/EMPTY! " + level +".txt" + " Generating random..." + e.getMessage());
            if (e.getMessage() != null){return randomList();}
        }
        return enemyList;
    }

    public void animationStart(){
        ememyMovement = new Timeline(new KeyFrame(Duration.millis(40),event -> {
            int screenMultiplier = 0; // amount of times enemy traveled off screen, because if u just reset coordinate, moveSet will calculate wrong path
            pic.setVisible(true);
            if(this.alive && this.onScreen){
                coordinates[1] += speed;
                coordinates[0] = moveSet(mod,coordinates[1]);

                if(coordinates[0]>=winWidth){
                    screenMultiplier--;
                } //Appear on the other side
                if(coordinates[0]<=0){
                    screenMultiplier++;
                }

                int diff = (int) this.pic.getX() - (int) (this.coordinates[0]+ (winWidth*screenMultiplier)); // Because angle could be wrong if coord outside window

                if(diff>0){ //Calculate needed pic
                    while (diff>30){diff -= 1;}
                    this.angle = diff;
                }
                else {
                    while (diff<-30){diff += 1;}
                    this.angle = diff;
                }

                pic.setImage(getImagePos());
                pic.setX(coordinates[0] + (winWidth*screenMultiplier));
                pic.setY(coordinates[1]);

                //New coordinates
                if(pic.getY()> App.winHeight || pic.getY()<0){
                    this.onScreen = false;
                }
            } else {
                pic.setVisible(false);
                if(!this.alive){
                    Player.score += this.score;
                }ememyMovement.stop();}
                if(this.pic.intersects(mainP.pic.getBoundsInLocal()) && !alreadyHit){
                    alreadyHit = true;
                    mainP.giveDamage(this.collideDamage);
                }
        }));
        ememyMovement.setCycleCount(Timeline.INDEFINITE);
        ememyMovement.play();
    }

    private Image getImagePos(){
        String tarRes;
        if(angle<=0){
            tarRes = String.format("%04d", 30 + Math.abs(angle));
        } else {
            tarRes = String.format("%04d", (30 - angle));
        }
        return new Image(Objects.requireNonNull(Utility.getImageRes("/enemy/" + this.type + "/" + tarRes + ".png")),80,80,false,false);
    }

    private double moveSet(int type , double yCord){
        switch (type) {
            case 1: { return add + multiplier*Math.sin(Vmultiplier*yCord);}
            case 2: { return add + multiplier*Math.cos(Vmultiplier*yCord);}
            case 3: { return add + -multiplier*Math.sin(Vmultiplier*yCord);}
            case 4: { return add + -multiplier*Math.cos(Vmultiplier*yCord);}
            case 5: { return add + multiplier*Math.cos(Math.acos(Vmultiplier*yCord));}
            case 6: { return add + -multiplier*Math.sin(Math.pow(Vmultiplier*yCord,1.2));}
            case 7: { return add + multiplier*Math.sin(Math.atan(Vmultiplier*yCord));}
            default: { return add;}
        }
    }

    private static LinkedList<Enemy> randomList() {
        LinkedList<Enemy> enemyList = new <Enemy>LinkedList();
        for (int i = 0; i < 4000; i++) {
            int type = (int) (1 + Math.random()*4);
            long timer = (int) (Math.random()*4000);
            int mod = (int) (Math.random()*7);
            double speed = (0.35 + Math.random()*3);
            double multiplier = Math.random()*150;
            double Vmultiplier = Math.random() * 0.005 * 10;
            double add = (0.5 - Math.random())*(winWidth*2 - 80) + 80;
            enemyList.add(new Enemy(type,timer,mod,speed,multiplier,Vmultiplier,add));
        }
        return enemyList;
    }
}
