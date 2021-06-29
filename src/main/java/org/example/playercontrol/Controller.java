package org.example.playercontrol;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.Game;

public class Controller {

    public static void placeAPlayer(){
        int Y = App.winHeight - 60;
        int X = App.winWidth/2;
        boolean rotLeft = false;
        boolean rotRight = false;
        ImageView player = new ImageView(new Image("player.rotation/0000.png"));
        player.setY(Y);
        player.setX(X);
    }
    public static void moveAPlayer(int angle){

    }

}
