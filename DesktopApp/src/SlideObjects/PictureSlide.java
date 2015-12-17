package SlideObjects;

import AdditionalClasses.SoundElement;

import java.util.List;


/**
 * Created by Evgeniy on 11/26/2015.
 */
public class PictureSlide extends AbstractSlide
{
    private List<SoundElement> soundElements;
    private String picturePath;

    public void setPicturePath(String path)
    {
        validateNotNull(path);

        picturePath = path;
    }

    public String getPicturePath()
    {
        return picturePath;
    }

    public void setSoundElements(List<SoundElement> elements)
    {
        validateNotNull(elements);

        soundElements = elements;
    }

    public List<SoundElement> getSoundElements()
    {
        return soundElements;
    }
}
