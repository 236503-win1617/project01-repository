package SlideManagers;

import AdditionalClasses.SoundElement;
import SlideObjects.AbstractSlide;
import SlideObjects.ListenAndFindGameSlide;
import SlideObjects.Rotation;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import SlideObjects.OrderGameSlide;

/**
 * Created by dan on 17/05/2016.
 */
public class OrderGameSlideManager extends AbstractSlideManager{
    private boolean first = true;
    public OrderGameSlideManager(JPanel currentSlide, JPanel commandsPanel){
        super(currentSlide, commandsPanel);
    }

    public void onRotateCommand() throws IOException {}

    public void loadSlide(AbstractSlide slide) throws IOException{
        if (first) {
            String[] gameTypes = {"5", "10", "15", "20"};
            String choice = (String) JOptionPane.showInputDialog(null, "What range of numbers do you want?",
                    "Choose Maximal Number", JOptionPane.QUESTION_MESSAGE, null, // Use
                    gameTypes, // Array of choices
                    gameTypes[0]); // Initial choice

            if (choice == null) return;
            int maxNum = Integer.parseInt(choice);
            ((OrderGameSlide) slide).setMaxNum(maxNum);
            first = false;
        }
        loadPictureFromFile(new FileInputStream(".\\src\\Resources\\order.png"), Rotation.NO_ROTATION);
    }

    public void saveDataToCurrentSlide(){}

    protected void setSpecificCommandsButtons(){}

    protected void specificClearContent(){}

    protected void setSpecificButtonsVisibility(boolean visibility){}

    public void loadPictureFile(File imageFile){}

    public void addNewSoundElement(SoundElement soundElement){}
}
