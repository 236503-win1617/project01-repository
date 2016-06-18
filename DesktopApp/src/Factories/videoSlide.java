package Factories;


import java.util.ArrayList;

/**
 * Created by Ohad on 11/12/2015.
 */
public class videoSlide extends Slide {

    String path;
    //ArrayList<Button> buttons = new ArrayList<>();
    //ArrayList<TextView> texts = new ArrayList<>();


    public videoSlide(String path) {
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    public ArrayList<DynamicButton> getDynamicButtons(){
        return null;
    }



}
