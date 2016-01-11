package screens;

import Factories.ComponentsFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alon on 12/22/2015.
 */

//TODO: implement settings screen - done
public class SettingScreen extends AbstractEmptyScreen
{
    public static int a = 0;
    private final String OPTION1 = "enable auto save";
    private final String OPTION2 = "disable auto save";
    private final String OPTION3 = "option3";
    private final String OPTION4 = "option4";
    private final String BACK = "back";

    public SettingScreen()
    {
        super();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        setSquareInsets(DEFAULT_BUTTONS_INSETS + 5);
        setCreateButton();
        setModifyButton();
//        setSettingsButton();
//        setAboutButton();
        setExitButton();

        setLocationRelativeTo(null);
    }

//    private void setAboutButton() {
//        JButton aboutButton = ComponentsFactory.createBasicButton("About");
//        aboutButton.addActionListener(e -> showInformationMessage("© Techion - all rights reserved."));
//        setConstraints(0, 3, 1, 1);
//        add(aboutButton, constraints);
//    }
//
//    private void setSettingsButton() {
//        JButton settingsButton = ComponentsFactory.createBasicButton("Settings");
//        settingsButton.addActionListener(e -> showInformationMessage("Not supported yet !"));
//        setConstraints(0, 2, 1, 1);
//        add(settingsButton, constraints);
//    }

    private void setCreateButton() {
        JButton createButton = new JButton(OPTION1);
        createButton.addActionListener(e -> {
            a = 1;
            showInformationMessage("enable auto save");
            //Screens.CreateLessonScreen.setVisible(true);
            //Screens.WelcomeScreen.setVisible(false);
        });
        setConstraints(0, 0, 1, 1);
        add(createButton, constraints);
    }

    private void setModifyButton() {
        JButton modifyButton = ComponentsFactory.createBasicButton(OPTION2);
        modifyButton.addActionListener(e -> {
            a = 0;
            showInformationMessage("disable auto save");
//            Screens.WelcomeScreen.setVisible(false);
//            Screens.ModifyLessonScreen.setVisible(true);
        });
        setConstraints(0, 1, 1, 1);
        add(modifyButton, constraints);
    }

    private void setExitButton() {
        JButton exitButton = ComponentsFactory.createBasicButton(BACK);
        exitButton.addActionListener(e -> {
            Screens.WelcomeScreen.setVisible(true);
            Screens.SettingScreen.setVisible(false);
        });
        setConstraints(0, 4, 1, 1);
        add(exitButton, constraints);
    }
}
