package screens;

import Factories.ComponentsFactory;

import javax.swing.*;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class WelcomeScreen extends AbstractScreen
{
    private final String CREATE_LESSON = "Create New Lesson";
    private final String MODIFY_LESSON = "Modify Existing Lesson";
    private final String EXIT = "Exit";

    public WelcomeScreen()
    {
        setCreateButton();
        setModifyButton();
        setExitButton();
    }

    @Override
    protected void setPanelsContent()
    {
        setBasicFrameContent();
    }

    private void setCreateButton()
    {
        JButton createButton = ComponentsFactory.createDefaultButton(CREATE_LESSON, 100, 100);
        createButton.addActionListener(e -> {
            Screens.CreateLessonScreen.setVisible(true);
            Screens.WelcomeScreen.setVisible(false);
        });
        mainPanel.add(createButton);
    }

    private void setModifyButton()
    {
        JButton modifyButton = ComponentsFactory.createDefaultButton(MODIFY_LESSON, 100, 200);
        modifyButton.addActionListener(e -> {
            Screens.WelcomeScreen.setVisible(false);
            Screens.ModifyLessonScreen.setVisible(true);
        });
        mainPanel.add(modifyButton);
    }

    private void setExitButton()
    {
        JButton exitButton = ComponentsFactory.createDefaultButton(EXIT, 100, 300);
        exitButton.addActionListener(e -> onClosingApp());
        mainPanel.add(exitButton);
    }
}