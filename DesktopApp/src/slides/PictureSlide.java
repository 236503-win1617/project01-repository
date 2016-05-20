package slides;

import AdditionalClasses.SoundElement;

import java.io.File;

/**
 * Created by Evgeniy on 11/26/2015.
 */
public class PictureSlide extends AbstractSlide {
    private SoundElement[] soundElements = new SoundElement[0];
    private File pictureFile;

    public File getPictureFile() {
        return pictureFile;
    }

    @Override
    public void setSlideFile(File file) {
        validateNotNull(file);
        this.pictureFile = file;
    }

    @Override
    protected String[] getSupportedFormats() {
        return new String[]{"png", "jpg", "jpeg", "bmp",};
    }

    public SoundElement[] getSoundElements() {
        return soundElements;
    }

    public void setSoundElements(SoundElement[] elements) {
        validateNotNull(elements);

        soundElements = elements;
    }

    @Override
    public SlideType getType() {
        return SlideType.Picture;
    }
}
