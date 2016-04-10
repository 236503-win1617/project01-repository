package Factories;

/**
 * Created by Ohad on 26/12/2015.
 */
public class DynamicText {

    private String text;
    private int start_x, start_y;
    private int textSize;

    public DynamicText(String text, int start_x, int start_y, int textSize) {
        this.text = text;
        this.start_x = start_x;
        this.start_y = start_y;
        this.textSize = textSize;
    }

    public String getText() {
        return text;
    }

    public int getStartX() {
        return start_x;
    }

    public int getStartY() {
        return start_y;
    }

    public int getTextSize() {
        return textSize;
    }
}
