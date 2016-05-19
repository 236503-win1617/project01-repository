package AdditionalClasses;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

/**
 * Created by Evgeniy on 3/19/2016.
 */
public class FileResources {
    private static final String NO_PICTURE_FILE_NAME = "desktopApp/resources/no_picture.jpg";
    private static final String NO_VIDEO_FILE_NAME = "desktopApp/resources/no_video.jpg";

    private static File getFileSource(String filename) {
        //TODO: maybe switch to resource loader

        return new File(filename);
    }

    public static final File noPictureAvailable = getFileSource(NO_PICTURE_FILE_NAME);
    public static final File noVideoAvailable = getFileSource(NO_VIDEO_FILE_NAME);
}
