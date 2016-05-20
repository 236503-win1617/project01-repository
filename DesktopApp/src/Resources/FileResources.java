package Resources;

import java.io.InputStream;

/**
 * Created by Evgeniy on 3/19/2016.
 */
public class FileResources {
    private static final String NO_PICTURE_FILE_NAME = "no_picture.jpg";
    private static final String NO_VIDEO_FILE_NAME = "no_video.jpg";
    private static final String OK_FILE_NAME = "ok.jpg";
    private static final String NO_PICTURE_BUTTON_FIL_NAME = "no-picture-button.jpg";
    private static final String NO_VIDEO_BUTTON_FILE_NAME = "no-video-button.jpg";
    private static final String VIDEO_SELECTED_BUTTON_FILE_NAME = "video-selected-button.jpg";

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

    public static InputStream getButtonNoPicture() {
        return getFileSource(NO_PICTURE_BUTTON_FIL_NAME);
    }

    public static InputStream getButtonNoVideo() {
        return getFileSource(NO_VIDEO_BUTTON_FILE_NAME);
    }

    public static InputStream getVideoSelectedButton() {
        return getFileSource(VIDEO_SELECTED_BUTTON_FILE_NAME);
    }
}
