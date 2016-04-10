package com.example.ohad.dynamicex;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Samuel on 28/03/2016.
 */
public class GameSlide extends Slide {
    private GameFragment gameFragment;

    public GameSlide(XmlParser.Game game){
        switch (game) {
            case NUMBERS:
                gameFragment = new GameNumbers();
            case COLORS:
                gameFragment = new GameColors();
            case ANIMALS:
                gameFragment = new GameAnimals();
            default:
                gameFragment = new GameNumbers();
        }
    }

    public void show(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .show(gameFragment)
                .commit();
    }

    public void hide() {
        //TODO kill an activity of the game. check if it will have any impact or it needs to move inside the activity of each game itself!
    }
}
