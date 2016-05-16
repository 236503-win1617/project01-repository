package SlideObjects;

import java.io.File;

/**
 * Created by Evgeniy on 11/26/2015.
 */
public class VideoSlide extends AbstractSlide {
    private File videoFile;

    public File getVideoFile() {
        return videoFile;
    }

    @Override
    public SlideType getType() {
        return SlideType.Video;
    }

    @Override
    public void setSlideFile(File file) {
        validateNotNull(file);
        this.videoFile = file;
    }

    @Override
    protected String[] getSupportedFormats() {
        return new String[]{"wmv", "avi", "mpg", "mpeg", "mp4", "mkv"};
    }
}
