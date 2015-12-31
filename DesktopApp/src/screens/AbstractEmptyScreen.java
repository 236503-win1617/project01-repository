package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Evgeniy on 12/12/2015.
 */
public class AbstractEmptyScreen extends JFrame
{
    protected GridBagConstraints constraints = new GridBagConstraints();

    protected AbstractEmptyScreen() throws HeadlessException
    {
        constraints.fill = GridBagConstraints.BOTH;
    }

    protected void onExit()
    {
        dispose();
        System.exit(0);
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
}