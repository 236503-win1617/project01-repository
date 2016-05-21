package screens;

import javax.swing.*;

/**
 * The class is responsible for storing the created screens
 * Created by Evgeniy on 11/21/2015.
 */
public class Screens {
    //TODO: Change to protected when stop using sound area screen
    public static JFrame WelcomeScreen = new WelcomeScreen();
    public static CreateLessonScreen CreateLessonScreen = new CreateLessonScreen();
    public static SoundAreaScreen SoundAreaScreen = new SoundAreaScreen();
    public static SettingScreen SettingScreen = new SettingScreen();
    public static ModifyLesson ModifyLesson = new ModifyLesson();

    public static void start() {
        WelcomeScreen.setVisible(true);
    }
}
