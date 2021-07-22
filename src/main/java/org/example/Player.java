package org.example;

import Frames.App;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    public ImageView pic;
    double[] Coordinates;
    public int hp;
    public int angle;
    public static int score = 0;
        public Player(int level){
            Coordinates = new double[] {App.winHeight - 100,(double) App.winWidth/2};
            hp = 200  + 40*level;
            angle = 0;
            pic = takeAngle();
            pic.setY(Coordinates[0]);
            pic.setX(Coordinates[1]);
        }

        public ImageView takeAngle(){
            Image resPic;
            String tarRes = String.format("%04d", Math.abs(angle));
            if(angle<0){
                resPic = new Image(Utility.getImageRes("/rotation/CL/" +tarRes+".png"),80,80,false,false);
            } else {
                resPic = new Image(Utility.getImageRes("/rotation/CR/" +tarRes+".png"),80,80,false,false);
            }
            ImageView res = new ImageView();
            res.setImage(resPic);
            return res;
        }
}
