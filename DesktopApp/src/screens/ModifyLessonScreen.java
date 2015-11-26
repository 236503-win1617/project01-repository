package screens;

import Factories.ComponentsFactory;

import javax.swing.*;

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
    protected void setPanelsContent()
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
        mainPanel.add(backButton);
    }
}
