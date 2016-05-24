package com.example.ohad.dynamicex;

import android.app.Activity;
import android.app.FragmentManager;


import java.io.File;

/**
 * Created by Samuel on 28/03/2016.
 */
public class GameSlide extends Slide {
    private GameFragment gameFragment;

    public GameSlide(XmlParser.Game game,Activity activity){
        switch (game) {
            case NUMBERS:
                gameFragment = new GameNumbers();
                break;
            case COLORS:
                gameFragment = new GameColors();
                break;
            case ANIMALS:
                gameFragment = new GameAnimals();
                break;
            case ORDER:
                gameFragment = new GameOrder();
                break;
            default:
                gameFragment = new GameNumbers();
        }
        activity.getFragmentManager().beginTransaction().add(R.id.main_container, gameFragment).commit();
        FragmentManager fm = activity.getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .hide(gameFragment)
                .commit();
    }

    public void setOrderGameNum(int maxNum){
        if (gameFragment instanceof GameOrder){
            ((GameOrder)gameFragment).setMaxNum(maxNum);
        }
    }

    public void show(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .show(gameFragment)
                .commit();
    }

    public void hide(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        fm.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .hide(gameFragment)
                .commit();
    }

    public GameFragment getGameFragment(){
        return gameFragment;
    }
}
