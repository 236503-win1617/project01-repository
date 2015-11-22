package screens;

import javax.swing.*;

/**
 * The class is responsible for storing the created screens
 * Created by Evgeniy on 11/21/2015.
 */
public class Screens {
    protected static JFrame WelcomeScreen = new WelcomeScreen();
    protected static JFrame CreateLessonScreen = new CreateLessonScreen();
    protected static JFrame ModifyLessonScreen = new ModifyLessonScreen();

    public static void start() {
        WelcomeScreen.setVisible(true);
    }
}
