package AdditionalClasses;

import java.io.File;

/**
 * Created by Evgeniy on 12/7/2015.
 */
public class SoundElement
{
    public File soundFile;

    public int start_x;
    public int start_y;
    public int height;
    public int width;

    public SoundElement(File soundFile, int start_x, int start_y, int height, int width)
    {
        this.soundFile = soundFile;
        this.start_x = start_x;
        this.start_y = start_y;
        this.height = height;
        this.width = width;
    }

    @Override
    public String toString()
    {
        String fileName = (soundFile != null) ? soundFile.getName() : "";
        return "Start X: " + start_x + ",  Start Y: " + start_y +
                "\n" + "Width: " + width + ",  Height: " + height +
                "\n" + "File: " + fileName;
    }
}
