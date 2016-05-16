package Factories;


import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ohad on 11/12/2015.
 */
public class PictureSlide extends Slide {

    String path;
    //ArrayList<Button> buttons = new ArrayList<>();
    //ArrayList<TextView> texts = new ArrayList<>();
    ArrayList<DynamicButton> dynamicButtons = new ArrayList<>();
    ArrayList<DynamicText> dynamicTexts = new ArrayList<>();
    boolean firstShow = true;

    public PictureSlide(String path, ArrayList<DynamicButton> dynamicButtons, ArrayList<DynamicText> dynamicTexts) {
        this.path = path;
        this.dynamicButtons = dynamicButtons;
        this.dynamicTexts = dynamicTexts;
    }

    public String getPath(){
        return this.path;
    }

    public ArrayList<DynamicButton> getDynamicButtons(){
        return this.dynamicButtons;
    }



}
