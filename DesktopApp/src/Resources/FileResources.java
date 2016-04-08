package Resources;

import java.io.File;

/**
 * Created by Evgeniy on 3/19/2016.
 */
public class FileResources {
    private static final String NO_PICTURE_FILE_NAME = "desktopApp/resources/no_picture.jpg";
    private static final String NO_VIDEO_FILE_NAME = "desktopApp/resources/no_video.jpg";
    private static final String OK_FILE_NAME = "desktopApp/resources/ok.jpg";

    //TODO: maybe switch to resource loader
    private static File getFileSource(String filename) {
        return new File(filename);
    }

    public static final File noPictureAvailable = getFileSource(NO_PICTURE_FILE_NAME);
    public static final File noVideoAvailable = getFileSource(NO_VIDEO_FILE_NAME);
    public static final File okPicture = getFileSource(OK_FILE_NAME);
}
