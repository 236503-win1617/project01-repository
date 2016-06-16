package SlideManagers;

import AdditionalClasses.SoundElement;
import Resources.FileResources;
import slides.AbstractSlide;
import slides.Rotation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by dan on 17/05/2016.
 */
public class OrderGameSlideManager extends AbstractSlideManager{
    public OrderGameSlideManager(JPanel currentSlide, JPanel commandsPanel){
        super(currentSlide, commandsPanel);
    }

    public void onRotateCommand() throws IOException {}

    public void loadSlide(AbstractSlide slide, JButton button) throws IOException {
        this.slideButton = button;
        loadSameImageToPanelAndButton(ImageIO.read(FileResources.getOrderGameImage()), Rotation.NO_ROTATION.getRotationInRadians());
    }

    public void saveDataToCurrentSlide(){}

    protected void setSpecificCommandsButtons(){}

    protected void specificClearContent(){}

    protected void setSpecificButtonsVisibility(boolean visibility){}

    public void addNewSoundElement(SoundElement soundElement){}

    @Override
    public void loadDroppedFile(File droppedFile) {

    }
}
