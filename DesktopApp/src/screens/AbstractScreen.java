package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public abstract class AbstractScreen extends JFrame
{
    protected JPanel contentPane;

    //TODO: remove this the the class managing the dynamic size should also be called on window change
    protected static Integer SCREEN_WIDTH;
    protected static Integer SCREEN_HEIGHT;

    protected static Integer X_SCREEN_START_FROM = 20;
    protected static Integer Y_SCREEN_START_FROM = 20;

    public AbstractScreen()
    {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setDynamicBounds();
        setFrameContent();

        //TODO: use this code for resizing of a window
//        frame.addComponentListener(new ComponentListener() {
//            public void componentResized(ComponentEvent e) {
//                // do stuff
//            }
//        });


// call onExit() when cross is clicked
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onClosingApp();
            }
        });
    }

    protected void onClosingApp()
    {
        if (isDataSaved())
        {
            int result = showYesNoMessage("Are you sure ?");
            if (result == JOptionPane.YES_OPTION)
            {
                onExit();
            }
        }
        else
        {
            //TODO: implement a way to save data before leaving - or not allowing exiting
        }
    }

    protected abstract void setFrameContent();

    protected void setBasicFrameContent()
    {
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(SystemColor.menu);
        setContentPane(contentPane);
    }

    protected boolean isDataSaved()
    {
        return true;
    }

    //TODO: create a class responsible for screens size
    private void setDynamicBounds()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_WIDTH = (int) screenSize.getWidth() - 100;
        SCREEN_HEIGHT = (int) screenSize.getHeight() - 100;

        if (SCREEN_HEIGHT > 600)
        {
            setBounds(X_SCREEN_START_FROM, Y_SCREEN_START_FROM, SCREEN_WIDTH, SCREEN_HEIGHT);
        }
        else
        {
            //TODO: implement for a small screen
        }
    }

    protected void onExit()
    {
        showInformationMessage("exiting");
        dispose();
        System.exit(0);
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
