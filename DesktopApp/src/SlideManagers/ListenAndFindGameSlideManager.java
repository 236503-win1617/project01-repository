package SlideManagers;

import AdditionalClasses.SoundElement;
import slides.AbstractSlide;
import slides.ListenAndFindGameSlide;
import slides.Rotation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by dan on 17/05/2016.
 */
public class ListenAndFindGameSlideManager extends AbstractSlideManager {
    private boolean first = true;

    public ListenAndFindGameSlideManager(JPanel currentSlide, JPanel commandsPanel) {
        super(currentSlide, commandsPanel);
    }

    public void onRotateCommand() throws IOException {
    }

    public void loadSlide(AbstractSlide slide, JButton button) throws IOException {
        this.slideButton = button;
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
        ListenAndFindGameSlide.GameType type = ((ListenAndFindGameSlide) slide).getGameType();
        String path = "";
        switch (type) {
            case Animals:
                path = ".\\resources\\animals.jpg";
                break;
            case Colors:
                path = ".\\resources\\colors.png";
                break;
            case Numbers:
                path = ".\\resources\\numbers.jpg";
                break;
        }
        loadImageToSlidePanel(ImageIO.read(new FileInputStream(path)), Rotation.NO_ROTATION.getRotationInRadians());
    }

    public void saveDataToCurrentSlide() {
    }

    protected void setSpecificCommandsButtons() {
    }

    protected void specificClearContent() {
    }

    protected void setSpecificButtonsVisibility(boolean visibility) {
    }

    public void addNewSoundElement(SoundElement soundElement) {
    }
}
