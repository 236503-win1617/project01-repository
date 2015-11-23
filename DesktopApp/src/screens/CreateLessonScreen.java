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
    protected JPanel slidesListPanel;

    public CreateLessonScreen()
    {
        setBackButton();
    }

    @Override
    protected void setFrameContent()
    {
        setLayout(new GridBagLayout());

        currentSlidePanel = new JPanel();
        slidesListPanel = new JPanel();
        contentPane = new JPanel();

        //TODO: remove this
        currentSlidePanel.setBackground(Color.green);
        slidesListPanel.setBackground(Color.blue);

        GridBagConstraints constraints = new GridBagConstraints();

        // we want the layout to stretch the components in both directions
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 10;

        // Vertical space is divided in proportion to the Y weights of the components
        constraints.weighty = 0.2;
        constraints.gridy = 0;
        add(contentPane, constraints);


        // It's fine to reuse the constraints object; add makes a copy.
        constraints.weighty = 0.6;
        constraints.gridy = 1;
        add(currentSlidePanel, constraints);

        constraints.weighty = 0.2;
        constraints.gridy = 2;
        add(slidesListPanel, constraints);
    }

    private void setBackButton()
    {
        JButton backButton = ComponentsFactory.createDefaultButton("Main menu", 40, 40);
        backButton.addActionListener(e -> {
            Screens.CreateLessonScreen.setVisible(false);
            Screens.WelcomeScreen.setVisible(true);
        });

        contentPane.add(backButton);
    }
}
