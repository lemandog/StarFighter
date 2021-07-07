package org.example;

public class Enemy {
    long waitTo; //Nanoseconds.
    long startupTime;
    Enemy(int type, long timer, int mod){
        waitTo = startupTime + timer;
        startupTime = System.nanoTime();
    }
    public static Enemy constructEnemyFromFile(){
        Enemy prod = new Enemy(1,1,1);
        return prod;
    }
}
