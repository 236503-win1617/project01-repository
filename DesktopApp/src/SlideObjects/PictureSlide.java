package SlideObjects;

import AdditionalClasses.SoundElement;

import java.io.File;

/**
 * Created by Evgeniy on 11/26/2015.
 */
public class PictureSlide extends AbstractSlide {
    private SoundElement[] soundElements;
    private File pictureFile;

    public PictureSlide() {
        soundElements = new SoundElement[0];
        pictureFile = null;
    }

    public File getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(File pictureFile) {
        validateNotNull(pictureFile);

        this.pictureFile = pictureFile;
    }

    public SoundElement[] getSoundElements() {
        return soundElements;
    }

    public void setSoundElements(SoundElement[] elements) {
        validateNotNull(elements);

        soundElements = elements;
    }
}
