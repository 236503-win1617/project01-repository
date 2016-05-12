package Resources;

import java.io.InputStream;

/**
 * Created by Evgeniy on 3/19/2016.
 */
public class FileResources {
    private static final String NO_PICTURE_FILE_NAME = "no_picture.jpg";
    private static final String NO_VIDEO_FILE_NAME = "no_video.jpg";
    private static final String OK_FILE_NAME = "ok.jpg";
//    private static final String ANIMALS_FILE_NAME = "animals.jpg";
//    private static final String NUMBERS_FILE_NAME = "numbers.jpg";
//    private static final String COLORS_FILE_NAME = "colors.jpg";

    private static InputStream getFileSource(String filename) {
        return FileResources.class.getResourceAsStream(filename);
    }

    public static InputStream getNoPictureStream() {
        return getFileSource(NO_PICTURE_FILE_NAME);
    }

    public static InputStream getNoVideoStream() {
        return getFileSource(NO_VIDEO_FILE_NAME);
    }

    public static InputStream getOkStream() {
        return getFileSource(OK_FILE_NAME);
    }
}
