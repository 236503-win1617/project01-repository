package screens;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class WelcomeScreen extends AbstractEmptyScreen {
    public WelcomeScreen() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        setCreateButton();
        setModifyButton();
        setSettingsButton();
        setAboutButton();
        setExitButton();

        setLocationRelativeTo(null);
    }

    private void setAboutButton() {
        JButton aboutButton = new JButton("About");

        aboutButton.addActionListener(e -> showInformationMessage("© Technion - all rights reserved."));
        setConstraints(0, 3, 1, 1);
        add(aboutButton, constraints);
    }

    private void setSettingsButton() {
        JButton settingsButton = new JButton("Settings");
        settingsButton.addActionListener(e -> {
                    Screens.SettingScreen.setVisible(true);
                    Screens.WelcomeScreen.setVisible(false);
                });
        setConstraints(0, 2, 1, 1);
        add(settingsButton, constraints);
    }

    private void setCreateButton() {
        JButton createButton = new JButton("Create New Lesson");
        createButton.addActionListener(e -> {
            String lessonName = showInputMessage("Insert Lesson Name:");
            //Screens.CreateLessonScreen.les
            Screens.CreateLessonScreen = new CreateLessonScreen();
            Screens.CreateLessonScreen.setLessonName(lessonName);


            File xmlDir = new File("xmlDir");
            // if the directory does not exist, create it
            if (!xmlDir.exists()) {
                xmlDir.mkdir();
            }


            File dir = new File("xmlDir\\" + lessonName);
            dir.mkdir();
            File AAImages = new File("xmlDir\\" + lessonName + "\\AAImages");
            AAImages.mkdir();
            File AASounds = new File("xmlDir\\" + lessonName + "\\AASounds");
            AASounds.mkdir();
            Screens.CreateLessonScreen.setVisible(true);
            Screens.WelcomeScreen.setVisible(false);
        });
        setConstraints(0, 0, 1, 1);
        add(createButton, constraints);
    }

    private void setModifyButton() {
        JButton modifyButton = new JButton("Modify Existing Lesson");
        modifyButton.addActionListener(e -> {
//            JFileChooser chooser = new JFileChooser();
//            chooser.showOpenDialog(Screens.WelcomeScreen);
//            File selectedFile = chooser.getSelectedFile();

            Screens.ModifyLesson.run();
//            Lesson LoadedLeson = new Lesson();
//            XmlParser.parse(selectedFile.getPath(),"xmlDir\\",LoadedLeson);
//            Screens.CreateLessonScreen =  new CreateLessonScreen();
//            Screens.CreateLessonScreen.setVisible(true);
//            Screens.WelcomeScreen.setVisible(false);
//            Screens.CreateLessonScreen.loadLesson(LoadedLeson);
            //showInformationMessage("Not supported yet !");
        });
        setConstraints(0, 1, 1, 1);
        add(modifyButton, constraints);
    }

    private void setExitButton() {
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> onExitApp(true));
        setConstraints(0, 4, 1, 1);
        add(exitButton, constraints);
    }
}
