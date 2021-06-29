package org.example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.App;

public class Player {
    public ImageView pic;
    double[] Coordinates;
    public int hp;
    public int angle;
        public Player(int level){
            Coordinates = new double[] {App.winHeight - 60,(double) App.winWidth/2};
            hp = 200  + 40*level;
            angle = 0;
            takeAngle();
        }
        public void takeAngle(){
            Image resPic;
            String tarRes = String.format("%04d", angle);
            if(angle<0){
                resPic = new Image("rotation/CR/" +tarRes+".png",40,40,false,false);
            } else {
                resPic = new Image("rotation/CL/" +tarRes+".png",40,40,false,false);
            }

            pic = new ImageView(resPic);
        }
}
