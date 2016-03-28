package com.example.ohad.dynamicex;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Samuel on 28/03/2016.
 */
public class GameSlide extends Slide {
    private Activity gameActivity;

    public GameSlide(XmlParser.Game game){
        switch (game) {
            case NUMBERS:
                gameActivity = new GameNumbers();
            case COLORS:
                gameActivity = new GameColors();
            case ANIMALS:
                gameActivity = new GameAnimals();
            default:
                gameActivity = new GameNumbers();
        }
    }

    public void show(Activity activity) {
        //TODO add startActivity of gameActivity and add to each activity the arrow buttons
    }

    public void hide() {
        //TODO kill an activity of the game. check if it will have any impact or it needs to move inside the activity of each game itself!
    }
}
