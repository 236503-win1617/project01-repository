package Factories;


import java.util.ArrayList;

/**
 * Created by Ohad on 11/12/2015.
 */
public class gameSlide extends Slide {

    public String typeOfGame;
    public int table_size;
    public int max_num;
    //ArrayList<Button> buttons = new ArrayList<>();
    //ArrayList<TextView> texts = new ArrayList<>();


    public gameSlide(String type) {
        this.typeOfGame = type;
    }

    public String getPath(){
        return "";
    }

    public ArrayList<DynamicButton> getDynamicButtons(){
        return null;
    }



}
