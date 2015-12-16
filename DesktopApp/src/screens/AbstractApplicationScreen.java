package screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AbstractApplicationScreen extends AbstractEmptyScreen
{
    //TODO: remove this the the class managing the dynamic size should also be called on window change
    protected static Integer SCREEN_WIDTH;
    protected static Integer SCREEN_HEIGHT;

    protected static Integer X_SCREEN_START_FROM = 20;
    protected static Integer Y_SCREEN_START_FROM = 20;

    public AbstractApplicationScreen()
    {
        super();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setDynamicBounds();
        setScreenPanels();
        setPanelsContent();

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
                onClosingWindow();
            }
        });
    }

    protected abstract void setScreenPanels();

    protected abstract void setPanelsContent();

    protected void onClosingWindow()
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
}
