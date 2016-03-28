package screens;

import AdditionalClasses.IndexedButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Evgeniy on 12/12/2015.
 */
public class AbstractEmptyScreen extends JFrame
{
    //TODO: remove this the the class managing the dynamic size should also be called on window change
    protected static Integer SCREEN_WIDTH;
    protected static Integer SCREEN_HEIGHT;

    protected final static int DEFAULT_BUTTONS_INSETS = 15;

    protected final static Integer X_SCREEN_START_FROM = 20;
    protected final static Integer Y_SCREEN_START_FROM = 20;

    protected final static Integer DEFAULT_WIDTH = 550;
    protected final static Integer DEFAULT_HEIGHT = 550;

    protected GridBagConstraints constraints = new GridBagConstraints();

    protected AbstractEmptyScreen() throws HeadlessException
    {
        super();
//        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        constraints.fill = GridBagConstraints.BOTH;
    }

    protected void onExit()
    {
        dispose();
        System.exit(0);
    }

    protected void onExitApp(boolean safeExit)
    {
        if (!safeExit)
        {
            if (showYesNoMessage("Are you sure ?") != JOptionPane.YES_OPTION)
            {
                return;
            }
        }

        onExit();
    }

    protected void setConstraints(int grid_x, int grid_y, double weight_x, double weight_y)
    {
        constraints.weightx = weight_x;
        constraints.weighty = weight_y;
        constraints.gridx = grid_x;
        constraints.gridy = grid_y;
    }

    protected void setSquareInsets(int value)
    {
        constraints.insets = new Insets(value, value, value, value);
    }

    protected void showErrorMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, null, JOptionPane.ERROR_MESSAGE);
    }

    protected int showYesNoMessage(String message)
    {
        return JOptionPane.showConfirmDialog(this, message, null, JOptionPane.YES_NO_OPTION);
    }

    protected void showInformationMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, null, JOptionPane.INFORMATION_MESSAGE);
    }

    protected String showInputMessage(String message)
    {
        return JOptionPane.showInputDialog(this,message);
    }
}