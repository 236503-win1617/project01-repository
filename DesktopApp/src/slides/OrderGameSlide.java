package slides;

import javax.swing.*;
import java.io.File;

/**
 * Created by dan on 16/05/2016.
 */
public class OrderGameSlide extends AbstractSlide {
    private int max_num;

    public OrderGameSlide() {
        super();
        String[] gameTypes = {"5", "10", "15", "20"};
        String choice = (String) JOptionPane.showInputDialog(null, "What range of numbers do you want?",
                "Choose Maximal Number", JOptionPane.QUESTION_MESSAGE, null, // Use
                gameTypes, // Array of choices
                gameTypes[0]); // Initial choice

        if (choice == null) return;
        this.max_num = Integer.parseInt(choice);
    }

    public OrderGameSlide(int max_num) {
        this.max_num = max_num;
    }

    public int getMaxNum() {
        return this.max_num;
    }

    public void setMaxNum(int max_num) {
        this.max_num = max_num;
    }

    @Override
    public SlideType getType() {
        return SlideType.OrderGame;
    }

    @Override
    public void setSlideFile(File file) {

    }

    @Override
    protected String[] getSupportedFormats() {
        return new String[0];
    }
}
