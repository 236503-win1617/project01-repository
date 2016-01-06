package newProject;
import newProject.SoundElement;

import java.io.File;
import java.util.Collection;
import java.util.List;


/**
 * Created by Evgeniy on 11/26/2015.
 */
public class PictureSlide extends AbstractSlide
{
	private SoundElement[] soundElements;
    File pictureFile;

    public void setPictureFile(File pictureFile)
    {
        validateNotNull(pictureFile);

        this.pictureFile = pictureFile;
    }

    public File getPictureFile()
    {
        return pictureFile;
    }

    public void setSoundElements(SoundElement[] elements)
    {
        validateNotNull(elements);

        soundElements = elements;
    }

    public SoundElement[] getSoundElements()
    {
        return soundElements;
    }
}
