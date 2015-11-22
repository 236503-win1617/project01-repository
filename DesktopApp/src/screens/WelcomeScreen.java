package screens;

import Factories.ComponentsFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private void setCreateButton()
    {
        JButton createButton = ComponentsFactory.createDefaultButton(CREATE_LESSON, 100, 100);
        createButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Screens.CreateLessonScreen.setVisible(true);
            }
        });
        contentPane.add(createButton);
    }

    private void setModifyButton()
    {
        JButton modifyButton = ComponentsFactory.createDefaultButton(MODIFY_LESSON, 100, 200);
        modifyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Screens.WelcomeScreen.setVisible(false);
                Screens.ModifyLessonScreen.setVisible(true);
            }
        });
        contentPane.add(modifyButton);
    }


    private void setExitButton()
    {
        JButton exitButton = ComponentsFactory.createDefaultButton(EXIT, 100, 300);
        exitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onExit();
            }
        });
        contentPane.add(exitButton);
    }
}
