package screens;

import Factories.ComponentsFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class WelcomeScreen extends AbstractEmptyScreen {
    private final String CREATE_LESSON = "Create New Lesson";
    private final String MODIFY_LESSON = "Modify Existing Lesson";
    private final String EXIT = "Exit";

    public WelcomeScreen() {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        setSquareInsets(DEFAULT_BUTTONS_INSETS + 5);
        setCreateButton();
        setModifyButton();
        setSettingsButton();
        setAboutButton();
        setExitButton();

        setLocationRelativeTo(null);
    }

    private void setAboutButton() {
        JButton aboutButton = ComponentsFactory.createBasicButton("About");
        aboutButton.addActionListener(e -> showInformationMessage("© Techion - all rights reserved."));
        setConstraints(0, 3, 1, 1);
        add(aboutButton, constraints);
    }

    private void setSettingsButton() {
        JButton settingsButton = ComponentsFactory.createBasicButton("Settings");
        settingsButton.addActionListener(e -> {
                    Screens.SettingScreen.setVisible(true);
                    Screens.WelcomeScreen.setVisible(false);
                });
        setConstraints(0, 2, 1, 1);
        add(settingsButton, constraints);
    }

    private void setCreateButton() {
        JButton createButton = new JButton(CREATE_LESSON);
        createButton.addActionListener(e -> {
            Screens.CreateLessonScreen.setVisible(true);
            Screens.WelcomeScreen.setVisible(false);
        });
        setConstraints(0, 0, 1, 1);
        add(createButton, constraints);
    }

    private void setModifyButton() {
        JButton modifyButton = ComponentsFactory.createBasicButton(MODIFY_LESSON);
        modifyButton.addActionListener(e -> {
            showInformationMessage("Not supported yet !");
//            Screens.WelcomeScreen.setVisible(false);
//            Screens.ModifyLessonScreen.setVisible(true);
        });
        setConstraints(0, 1, 1, 1);
        add(modifyButton, constraints);
    }

    private void setExitButton() {
        JButton exitButton = ComponentsFactory.createBasicButton(EXIT);
        exitButton.addActionListener(e -> onExitApp(true));
        setConstraints(0, 4, 1, 1);
        add(exitButton, constraints);
    }
}
