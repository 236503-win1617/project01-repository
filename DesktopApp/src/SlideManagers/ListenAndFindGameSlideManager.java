package SlideManagers;

import AdditionalClasses.SoundElement;
import SlideObjects.AbstractSlide;
import SlideObjects.GameSlide;
import SlideObjects.ListenAndFindGameSlide;
import SlideObjects.Rotation;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by dan on 17/05/2016.
 */
public class ListenAndFindGameSlideManager extends AbstractSlideManager {
    private boolean first = true;
    public ListenAndFindGameSlideManager(JPanel currentSlide, JPanel commandsPanel){
        super(currentSlide, commandsPanel);
    }

    public void onRotateCommand() throws IOException {}

    public void loadSlide(AbstractSlide slide) throws IOException{
        if (first) {
            String[] gameTypes = {"Animals", "Colors", "Numbers"};
            String choice = (String) JOptionPane.showInputDialog(null, "What game do you want?",
                    "Choose Type of Game", JOptionPane.QUESTION_MESSAGE, null, // Use
                    gameTypes, // Array of choices
                    gameTypes[0]); // Initial choice

            ListenAndFindGameSlide.GameType type = null;
            if (choice == null) return;
            if (choice.equals("Animals")) {
                type = ListenAndFindGameSlide.GameType.Animals;
            } else if (choice.equals("Colors")) {
                type = ListenAndFindGameSlide.GameType.Colors;
            } else if (choice.equals("Numbers")) {
                type = ListenAndFindGameSlide.GameType.Numbers;
            }
            ((ListenAndFindGameSlide) slide).setGameType(type);
            first = false;
        }
        ListenAndFindGameSlide.GameType type =  ((ListenAndFindGameSlide) slide).getGameType();
        switch(type){
            case Animals:
                loadPictureFromFile(new FileInputStream(".\\resources\\animals.jpg"), Rotation.NO_ROTATION);
                break;
            case Colors:
                loadPictureFromFile(new FileInputStream(".\\resources\\colors.png"), Rotation.NO_ROTATION);
                break;
            case Numbers:
                loadPictureFromFile(new FileInputStream(".\\resources\\numbers.jpg"), Rotation.NO_ROTATION);
                break;
        }
    }

    public void saveDataToCurrentSlide(){}

    protected void setSpecificCommandsButtons(){}

    protected void specificClearContent(){}

    protected void setSpecificButtonsVisibility(boolean visibility){}

    public void loadPictureFile(File imageFile){}

    public void addNewSoundElement(SoundElement soundElement){}
}
