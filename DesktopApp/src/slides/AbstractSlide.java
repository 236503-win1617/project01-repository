package slides;

import java.io.File;
import java.util.Arrays;

/**
 * Created by Evgeniy on 11/26/2015.
 */
public abstract class AbstractSlide {
    protected Rotation slideRotation;

    public AbstractSlide() {
        slideRotation = Rotation.NO_ROTATION;
    }

    public void rotateSlide() {
        Rotation next = slideRotation.getNext();
        slideRotation = next;
    }

    public Rotation getRotation() {
        return slideRotation;
    }

    public abstract SlideType getType();

    public abstract void setSlideFile(File file);

    protected void validateNotNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    public boolean isFileNameSupported(String filename) {
        return Arrays.stream(getSupportedFormats()).anyMatch(f -> filename.toLowerCase().endsWith(f.toLowerCase()));
    }

    protected abstract String[] getSupportedFormats();
}
