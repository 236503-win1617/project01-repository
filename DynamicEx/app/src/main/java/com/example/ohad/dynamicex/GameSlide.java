package com.example.ohad.dynamicex;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class GameSlide extends Slide {

    private Activity activity;
    private GameFragment gameFragment;

    public GameSlide(Activity a, XmlParser.GameType game){
        activity = a;

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
            case MEMORY:
                gameFragment = new GameMemory();
                break;
            case MISSING:
                gameFragment = new GameMissing();
                break;
            default:
                System.err.println("Game type error!");
                return;
        }

        FragmentManager fm = activity.getFragmentManager();
        fm.beginTransaction().add(R.id.main_container, gameFragment).commit();
        fm.beginTransaction().hide(gameFragment).commit();
    }

    public GameSlide(Activity a, XmlParser.GameType game, int param){
        activity = a;

        switch (game) {
            case ORDER:
                gameFragment = new GameOrder();
                break;
            case MEMORY:
                gameFragment = new GameMemory();
                break;
            default:
                System.err.println("Game type error!");
                return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt("param", param);
        gameFragment.setArguments(bundle);

        FragmentManager fm = activity.getFragmentManager();
        fm.beginTransaction().add(R.id.main_container, gameFragment).commit();
        fm.beginTransaction().hide(gameFragment).commit();
    }



    public void show() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FragmentManager fm = activity.getFragmentManager();
        fm.beginTransaction().show(gameFragment).commit();
        gameFragment.activateSound();
    }

    public void hide() {
        FragmentManager fm = activity.getFragmentManager();
        gameFragment.clearSound();
        fm.beginTransaction().hide(gameFragment).commit();
    }
}
