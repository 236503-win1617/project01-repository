package Resources;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Evgeniy on 3/19/2016.
 */
public class FileResources {
    private static final String NO_PICTURE_FILE_NAME = "no_picture.jpg";
    private static final String NO_VIDEO_FILE_NAME = "no_video.jpg";
    private static final String OK_FILE_NAME = "ok.jpg";

    //TODO: switch to use input stream instead of file
    private static File getFileSource(String filename) {
        try {
            URI uri = FileResources.class.getResource(filename).toURI();
            return new File(uri);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static final File noPictureAvailable = getFileSource(NO_PICTURE_FILE_NAME);
    public static final File noVideoAvailable = getFileSource(NO_VIDEO_FILE_NAME);
    public static final File okPicture = getFileSource(OK_FILE_NAME);
}
