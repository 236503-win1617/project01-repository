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

    private static final String HOME_BUTTON_IMAGE = "home.png";
    private static final String HELP_BUTTON_IMAGE = "help.png";
    private static final String EMAIL_BUTTON_IMAGE = "email.png";
    private static final String BUG_REPORT_BUTTON_IMAGE = "bug.png";
    private static final String SAVE_BUTTON_IMAGE = "save.png";
    private static final String USB_BUTTON_IMAGE = "usb.png";
    private static final String ORDER_GAME_IMAGE = "order.png";
    private static final String NUMBERS_GAME_IMAGE = "numbers.jpg";
    private static final String COLORS_GAME_IMAGE = "colors.png";
    private static final String ANIMALS_GAME_IMAGE = "animals.jpg";
    private static final String MEMORY_GAME_IMAGE = "memory.jpg";

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

    public static InputStream getHomeButtonImage() {
        return getFileSource(HOME_BUTTON_IMAGE);
    }

    public static InputStream getHelpButtonImage() {
        return getFileSource(HELP_BUTTON_IMAGE);
    }

    public static InputStream getEmailButtonImage() {
        return getFileSource(EMAIL_BUTTON_IMAGE);
    }

    public static InputStream getBugReportButtonImage() {
        return getFileSource(BUG_REPORT_BUTTON_IMAGE);
    }

    public static InputStream getSaveButtonImage() {
        return getFileSource(SAVE_BUTTON_IMAGE);
    }

    public static InputStream getUsbButtonImage() {
        return getFileSource(USB_BUTTON_IMAGE);
    }

    public static InputStream getOrderGameImage() {
        return getFileSource(ORDER_GAME_IMAGE);
    }

    public static InputStream getColorsGameImage() {
        return getFileSource(COLORS_GAME_IMAGE);
    }

    public static InputStream getNumbersGameImage() {
        return getFileSource(NUMBERS_GAME_IMAGE);
    }

    public static InputStream getAnimalsGameImage() {
        return getFileSource(ANIMALS_GAME_IMAGE);
    }

    public static InputStream getMemoryGameImage() {
        return getFileSource(MEMORY_GAME_IMAGE);
    }
}
