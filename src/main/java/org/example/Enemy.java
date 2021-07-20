package org.example;

import Frames.App;

import java.util.*;

public class Enemy {
    long waitTo; //Nanoseconds.
    long startupTime;
    double speed;
    int type;
    int mod;
    double[] coordinates = new double[] {0,(double) App.winWidth/2};
    Enemy(int type, long timer, int mod, int speed){
        this.type = type;
        this.speed = speed;
        this.mod = mod;
        waitTo = startupTime + timer;
        startupTime = System.nanoTime();
    }
    public static Queue<Enemy> constructEnemyFromFile(int level){
        Queue<Enemy> enemyList =  new LinkedList();
        try {
            Scanner scanner = new Scanner(Utility.class.getResource("/levelInfo/" + level +".txt").getFile());
            Utility.debugOutput("Save file found " + level);
            int num = 0;
            scanner.nextLine(); //Skipping info table
            while(scanner.hasNextLine()){
                num++;
                int type = scanner.nextInt();
                long timer = scanner.nextLong();
                int mod = scanner.nextInt();
                int speed = scanner.nextInt();
                Utility.debugOutput("LINE "+ num + " CHAR: TYPE " + type +" TIMER " + timer + " MOVE SET " + mod + " SPEED IS " + speed);
                enemyList.add(new Enemy(type,timer,mod,speed));
            }
        } catch (NullPointerException e) {
            Utility.debugOutput("NO SUCH FILE FOUND OR EMPTY! " + level +".txt" + " Generating random...");
            return randomList();
        }
    return enemyList;
    }

    private double moveset(int type , double yCord){
        switch (type) {
            case 1: { return Math.sin(yCord);}
            case 2: { return Math.cos(yCord);}
            case 3: { return -Math.sin(yCord);}
            case 4: { return -Math.cos(yCord);}
            case 5: { return Math.exp(yCord);}
            case 6: { return -Math.exp(yCord);}
            case 7: { return Math.atan(yCord);}
            default: { return yCord;}
        }
    }

    private static Queue<Enemy> randomList() {
        Queue<Enemy> enemyList = new LinkedList();
        for (int i = 0; i < 40; i++) {
            int type = (int) (Math.random()*5);
            long timer = (int) (Math.random()*100);
            int mod = (int) (Math.random()*4);
            int speed = (int) (Math.random()*20);
            enemyList.add(new Enemy(type,timer,mod,speed));
        }
        return enemyList;
    }
}
