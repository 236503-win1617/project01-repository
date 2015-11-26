package screens;

import Factories.ComponentsFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class CreateLessonScreen extends AbstractScreen
{
    protected JPanel currentSlidePanel;
    protected JPanel soundsPanel;
    protected JPanel slidesListPanel;
    protected JPanel willSeePanel;

    public CreateLessonScreen()
    {
        setBackButton();
    }

    @Override
    protected void setPanels()
    {
        currentSlidePanel = new JPanel();
        soundsPanel = new JPanel();
        slidesListPanel = new JPanel();
        mainPanel = new JPanel();
        willSeePanel = new JPanel();
    }

    @Override
    protected void setPanelsContent()
    {
        setPanel(slidesListPanel, Color.blue, SCREEN_WIDTH, SCREEN_HEIGHT / 6, BorderLayout.SOUTH);
        setPanel(currentSlidePanel, Color.black, (SCREEN_WIDTH * 4) / 6, (SCREEN_HEIGHT * 4) / 6, BorderLayout.CENTER);
        setPanel(soundsPanel, Color.YELLOW, SCREEN_WIDTH / 6, (SCREEN_HEIGHT * 4) / 6, BorderLayout.WEST);
        setPanel(mainPanel, Color.RED, SCREEN_WIDTH, SCREEN_HEIGHT / 6, BorderLayout.NORTH);
        setPanel(willSeePanel, Color.GREEN, SCREEN_WIDTH / 6, (SCREEN_HEIGHT * 4) / 6, BorderLayout.EAST);

        pack();
    }

    private void setPanel(JPanel panel, Color color, int width, int height, String location)
    {
        panel.setBackground(color); //TODO: Delete this line when everything is working
        panel.setLayout(new GridBagLayout());
        Dimension dimensions = new Dimension(width, height);
        panel.setPreferredSize(dimensions);
        getContentPane().add(panel, location);
    }

    private void setBackButton()
    {
        JButton backButton = ComponentsFactory.createDefaultButton("Main menu", 40, 40);
        backButton.addActionListener(e -> {
            Screens.CreateLessonScreen.setVisible(false);
            Screens.WelcomeScreen.setVisible(true);
        });

        mainPanel.add(backButton);
    }
}
