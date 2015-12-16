package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Evgeniy on 12/12/2015.
 */
public class AbstractEmptyScreen extends JFrame
{
    protected final int DEFAULT_BUTTONS_INSESTE = 15;

    protected GridBagConstraints _constraints = new GridBagConstraints();

    protected AbstractEmptyScreen() throws HeadlessException
    {
        _constraints.fill = GridBagConstraints.BOTH;
    }

    protected void setBasicFrameContent()
    {
        JPanel basicPanel = new JPanel();
        basicPanel.setLayout(null);
        basicPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        basicPanel.setBackground(SystemColor.menu);
        setContentPane(basicPanel);
    }

    protected void onExit()
    {
        // showInformationMessage("exiting");
        dispose();
        System.exit(0);
    }

    protected void setConstraints(int grid_x, int grid_y, double weight_x, double weight_y)
    {
        _constraints.weightx = weight_x;
        _constraints.weighty = weight_y;
        _constraints.gridx = grid_x;
        _constraints.gridy = grid_y;
    }

    protected void setSquareInsests(int value)
    {
        _constraints.insets = new Insets(value, value, value, value);
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