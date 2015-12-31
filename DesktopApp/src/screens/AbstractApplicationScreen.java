package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AbstractApplicationScreen extends AbstractEmptyScreen
{
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
                boolean dataSaved = isDataSaved();
                onExitApp(dataSaved);
            }
        });
    }

    protected abstract void setScreenPanels();

    protected abstract void setPanelsContent();

    //TODO: maybe switch to abstract
    protected boolean isDataSaved()
    {
        return false;
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
