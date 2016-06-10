package SlideManagers;

import AdditionalClasses.SoundElement;
import Resources.FileResources;
import slides.AbstractSlide;
import slides.ListenAndFindGameSlide;
import slides.Rotation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dan on 17/05/2016.
 */
public class ListenAndFindGameSlideManager extends AbstractSlideManager {
    private boolean first;

    public ListenAndFindGameSlideManager(JPanel currentSlide, JPanel commandsPanel) {
        super(currentSlide, commandsPanel);
        first = true;
    }

    public void onRotateCommand() throws IOException {
    }

    public void loadSlide(AbstractSlide slide, JButton button) throws IOException {
        ListenAndFindGameSlide.GameType type = ((ListenAndFindGameSlide) slide).getGameType();
        InputStream is = null;
        switch (type) {
            case ANIMALS:
                is = FileResources.getAnimalsGameImage();
                break;
            case COLORS:
                is = FileResources.getColorsGameImage();
                break;
            case NUMBERS:
                is = FileResources.getNumbersGameImage();
                break;
        }
        loadImageToSlidePanel(ImageIO.read(is), Rotation.NO_ROTATION.getRotationInRadians());
    }

    public void saveDataToCurrentSlide() {
    }

    protected void setSpecificCommandsButtons() {
    }

    protected void specificClearContent() {
    }

    protected void setSpecificButtonsVisibility(boolean visibility) {
    }

    public void loadPictureFile(File imageFile) {
    }

    public void addNewSoundElement(SoundElement soundElement) {
    }
}
