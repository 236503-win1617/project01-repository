package SlideManagers;

import AdditionalClasses.SoundElement;
import slides.AbstractSlide;
import slides.Rotation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import slides.OrderGameSlide;

/**
 * Created by dan on 17/05/2016.
 */
public class OrderGameSlideManager extends AbstractSlideManager{
    private boolean first = true;
    public OrderGameSlideManager(JPanel currentSlide, JPanel commandsPanel){
        super(currentSlide, commandsPanel);
    }

    public void onRotateCommand() throws IOException {}

    public void loadSlide(AbstractSlide slide, JButton button) throws IOException{
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
        FileInputStream fis = (FileInputStream)this.getClass().getResourceAsStream(".\\resources\\order.png");
        loadImageToSlidePanel(ImageIO.read(fis),
                Rotation.NO_ROTATION.getRotationInRadians());
    }

    public void saveDataToCurrentSlide(){}

    protected void setSpecificCommandsButtons(){}

    protected void specificClearContent(){}

    protected void setSpecificButtonsVisibility(boolean visibility){}

    public void loadPictureFile(File imageFile){}

    public void addNewSoundElement(SoundElement soundElement){}
}
