package screens;

import Factories.ComponentsFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class ModifyLessonScreen extends AbstractScreen
{
    public ModifyLessonScreen()
    {
        setBackButton();
    }

    @Override
    protected void setFrameContent()
    {
        setBasicFrameContent();
    }

    private void setBackButton()
    {
        JButton backButton = ComponentsFactory.createDefaultButton("Main menu", 40, 40);
        backButton.addActionListener(e -> {
            Screens.ModifyLessonScreen.setVisible(false);
            Screens.WelcomeScreen.setVisible(true);
        });
        contentPane.add(backButton);
    }
}
