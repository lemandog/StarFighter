package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Enemy {
    long waitTo; //Nanoseconds.
    long startupTime;
    Enemy(int type, long timer, int mod){
        waitTo = startupTime + timer;
        startupTime = System.nanoTime();
    }
    public static Queue<Enemy> constructEnemyFromFile(int level){
        Queue<Enemy> enemyList =  new LinkedList();
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/levelInfo/"+ level +".txt"));
            System.out.println("Save file found " + level);
            int num = 0;
            while(scanner.hasNextLine()){
                num++;
                int type = scanner.nextInt();
                long timer = scanner.nextLong();
                int mod = scanner.nextInt();
                System.out.println("LINE "+ num + " CHAR: TYPE " + type +" TIMER " + timer + " MOD " + mod);
                enemyList.add(new Enemy(type,timer,mod));
            }
        } catch (FileNotFoundException e) {
            System.out.println("NO SUCH FILE FOUND! " + "levelList"+ level +".txt" + " Generating random...");
            return randomList();
        }
    return enemyList;
    }


    private static Queue<Enemy> randomList() {
        Queue<Enemy> enemyList = null;
        return enemyList;
    }
}
