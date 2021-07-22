package org.example;

import Frames.App;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.*;

public class Enemy {
    public long waitTo; //Nanoseconds.
    long startupTime;
    double speed;
    int type;
    int mod;
    int angle;
    public boolean alive = true;
    boolean onScreen = true;
    int score;
    int damage;
    int collideDamage = 50;
    public ImageView pic = new ImageView();
    public Timeline ememyMovement;
    double[] coordinates = new double[] {(double) App.winWidth/2,40};

    double multiplier;
    double Vmultiplier;
    double add;

    Enemy(int type, long timer, int mod, int speed, double multiplier, double Vmultipluer, double add){
        this.damage = 10*type;
        this.type = type;
        this.speed = speed;
        this.mod = mod;
        this.angle = 0;
        this.waitTo = startupTime + timer;
        this.startupTime = System.nanoTime();
        this.score = type*speed*mod;

        this.multiplier = multiplier;
        this.Vmultiplier = Vmultipluer;
        this.add = add;

        Utility.debugOutput("ENEMY CONSTRUCTED: " + " TYPE " +type + " DAMAGE " + damage +
                " SPEED " + speed + " WAITING TO " + waitTo+ " M IS " + this.multiplier + " VM "
                + this.Vmultiplier + " ADD " + this.add);

        pic.setImage(getImagePos());
        pic.setVisible(false);
    }

    public static LinkedList<Enemy> constructEnemyFromFile(int level){
        LinkedList<Enemy> enemyList =  new <Enemy>LinkedList();
        try {
            Scanner scanner = new Scanner(Objects.requireNonNull(Utility.class.getResource("/levelInfo/" + level + ".txt")).getFile());
            Utility.debugOutput("Save file found " + level);
            int num = 0;
            scanner.nextLine(); //Skipping info table
            while(scanner.hasNextLine()){
                num++;
                int type = scanner.nextInt();
                long timer = scanner.nextLong();
                int mod = scanner.nextInt();
                int speed = scanner.nextInt();
                double multiplier = scanner.nextDouble();
                double Vmultiplier = scanner.nextDouble();
                double add = scanner.nextDouble();
                Utility.debugOutput("LINE "+ num + " CHAR: TYPE " + type +" TIMER " + timer + " MOVE SET " + mod + " SPEED IS " + speed);
                enemyList.add(new Enemy(type,timer,mod,speed,multiplier,Vmultiplier,add));
            }
        } catch (NullPointerException e) {
            Utility.debugOutput("NO SUCH FILE FOUND OR EMPTY! " + level +".txt" + " Generating random...");
            return randomList();
        }
    return enemyList;
    }

    public void animationStart(){
        ememyMovement = new Timeline(new KeyFrame(Duration.millis(50),event -> {
            pic.setVisible(true);
            if(this.alive && this.onScreen){
                coordinates[1] += speed;
                coordinates[0] = moveSet(mod,coordinates[1]);
                if(((int) pic.getX() - (int) coordinates[0])>0){
                    angle = (int) speed;
                }
                else {
                    angle = (int) -speed;
                }
                pic.setImage(getImagePos());
                pic.setX(coordinates[0]);
                pic.setY(coordinates[1]);
                //New coordinates
                if(pic.getX()> App.winWidth || pic.getY()> App.winHeight || pic.getY()<0 || pic.getX()<0){
                    this.onScreen = false;
                }
            } else {
                pic.setVisible(false);
                if(!this.alive){
                    Player.score += this.score;
                }
                ememyMovement.stop();
            }
        }));
        ememyMovement.setCycleCount(Timeline.INDEFINITE);
        ememyMovement.play();
    }

    private Image getImagePos(){
        String tarRes;
        if(angle<0){
            tarRes = String.format("%04d", Math.abs(angle + 30));
        } else {
            tarRes = String.format("%04d", Math.abs(angle));
        }
        Utility.debugOutput("GET IMAGE POSITION /enemy/"+ this.type +"/" + tarRes +".png");
        return new Image(Utility.getImageRes("/enemy/"+ this.type +"/" + tarRes +".png"),80,80,false,false);
    }

    private double moveSet(int type , double yCord){
        switch (type) {
            case 1: { return add + multiplier*Math.sin(Vmultiplier*yCord);}
            case 2: { return add + multiplier*Math.cos(Vmultiplier*yCord);}
            case 3: { return add + -multiplier*Math.sin(Vmultiplier*yCord);}
            case 4: { return add + -multiplier*Math.cos(Vmultiplier*yCord);}
            case 5: { return add + multiplier*Math.exp(Vmultiplier*yCord);}
            case 6: { return add + -multiplier*Math.exp(Vmultiplier*yCord);}
            case 7: { return add + multiplier*Math.atan(Vmultiplier*yCord);}
            default: { return add;}
        }
    }

    private static LinkedList<Enemy> randomList() {
        LinkedList<Enemy> enemyList = new <Enemy>LinkedList();
        for (int i = 0; i < 4000; i++) {
            int type = (int) (1 + Math.random()*4);
            long timer = (int) (Math.random()*1000);
            int mod = (int) (Math.random()*7);
            int speed = (int) (1 + Math.random()*5);
            double multiplier = Math.random()*100;
            double Vmultiplier = Math.random() * 0.05 * 10;
            double add = (0.5 - Math.random())*200 + (double) App.winWidth/2 - 40;
            enemyList.add(new Enemy(type,timer,mod,speed,multiplier,Vmultiplier,add));
        }
        return enemyList;
    }
}
