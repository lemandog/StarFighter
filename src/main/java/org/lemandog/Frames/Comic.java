package org.lemandog.Frames;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import org.lemandog.Utility;

import java.util.Objects;

import static org.lemandog.Frames.App.winHeight;
import static org.lemandog.Frames.App.winWidth;
public class Comic {
    static boolean outcome;
    static int currPage=1;
    public static void construct(int level) {
        Group comicLayout = new Group();
        Scene comicSc = new Scene(comicLayout, winWidth, winHeight);
        comicSc.setFill(Color.BLACK);
        ImageView pictureShower = new ImageView(showPicture(currPage, level));
        comicSc.setOnKeyPressed((keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE || keyEvent.getCode() == KeyCode.ENTER) {
                nextPage(level);
                pictureShower.setImage(showPicture(currPage, level));
            }
        }));
        comicLayout.getChildren().add(pictureShower);
        App.getStage().setScene(comicSc);
    }
    private static Image showPicture(int currPage, int level) {
        Utility.debugOutput("/comic/"+ level + "/" + currPage + ".png");
        if (Utility.getImageRes("/comic/"+ level + "/" + currPage + ".png") != null){
            return new Image(Objects.requireNonNull(Utility.getImageRes("/comic/" + level + "/" + currPage + ".png")));
        } else {
            return null;
        }
    }
    private static void nextPage(int level) {
        if(showPicture(currPage+1, level) == null){
            Game.startGame(level);
        } else {
            currPage++;
        }
    }
}
