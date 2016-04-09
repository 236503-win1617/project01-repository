package Factories;


import java.util.ArrayList;

/**
 * Created by Ohad on 11/12/2015.
 */
public abstract class Slide {
    //abstract void show();
    //abstract void hide();
    public abstract String getPath();
    public abstract ArrayList<DynamicButton> getDynamicButtons();
}
