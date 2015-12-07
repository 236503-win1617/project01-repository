package AdditionalClasses;

/**
 * Created by Evgeniy on 12/7/2015.
 */
public class SoundElement
{
    public String filePath;

    public int start_x;
    public int start_y;
    public int height;
    public int width;

    public SoundElement(String filePath, int start_x, int start_y, int height, int width)
    {
        this.filePath = filePath;
        this.start_x = start_x;
        this.start_y = start_y;
        this.height = height;
        this.width = width;
    }
}
