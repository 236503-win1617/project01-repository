package screens;

import Factories.ComponentsFactory;

import javax.swing.*;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class WelcomeScreen extends AbstractEmptyScreen
{
    private final String CREATE_LESSON = "Create New Lesson";
    private final String MODIFY_LESSON = "Modify Existing Lesson";
    private final String EXIT = "Exit";

    public WelcomeScreen()
    {

        setCreateButton();
        setModifyButton();
//        setSettingsButton();
//        setAboutButton();
        setExitButton();
    }

    private void setCreateButton()
    {
        JButton createButton = ComponentsFactory.createBasicButton(CREATE_LESSON);
        createButton.addActionListener(e -> {
            Screens.CreateLessonScreen.setVisible(true);
            Screens.WelcomeScreen.setVisible(false);
        });
        add(createButton);
    }

    private void setModifyButton()
    {
        JButton modifyButton = ComponentsFactory.createBasicButton(MODIFY_LESSON);
        modifyButton.addActionListener(e -> {
            Screens.WelcomeScreen.setVisible(false);
            Screens.ModifyLessonScreen.setVisible(true);
        });
        add(modifyButton);
    }

    private void setExitButton()
    {
        JButton exitButton = ComponentsFactory.createBasicButton(EXIT);
     //   exitButton.addActionListener(e -> onClosingWindow());
        add(exitButton);
    }
}
