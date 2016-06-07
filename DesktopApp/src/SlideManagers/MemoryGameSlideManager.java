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
public class MemoryGameSlideManager extends AbstractSlideManager{
    private boolean first;
    public MemoryGameSlideManager(JPanel currentSlide, JPanel commandsPanel){
        super(currentSlide, commandsPanel);
        first = true;
    }

    public void onRotateCommand() throws IOException {}

    public void loadSlide(AbstractSlide slide, JButton button) throws IOException{
        FileInputStream fis = new FileInputStream("resources/memory.jpg");
        loadImageToSlidePanel(ImageIO.read(fis), Rotation.NO_ROTATION.getRotationInRadians());
    }

    public void saveDataToCurrentSlide(){}

    protected void setSpecificCommandsButtons(){}

    protected void specificClearContent(){}

    protected void setSpecificButtonsVisibility(boolean visibility){}

    public void loadPictureFile(File imageFile){}

    public void addNewSoundElement(SoundElement soundElement){}
}
