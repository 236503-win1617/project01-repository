package AdditionalClasses;

import com.sun.javaws.exceptions.MissingFieldException;
import screens.AbstractScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

/**
 * Created by Evgeniy on 12/7/2015.
 */
public class SoundAreaFrame extends AbstractScreen
{
    private static int _start_x;
    private static int _start_y;
    private static int _height;
    private static int _width;

    private static File _soundFile;

    public SoundAreaFrame() throws HeadlessException
    {
        super();

        JPanel tmp = new JPanel();
        setLayout(new GridBagLayout());

        JTextField _start_x_TextField = new JTextField();
        setConstraints(0,0,1,1);

        add(_start_x_TextField,_constraints);

//        _start_x_TextField = new JTextField();
//        _start_x_TextField.addFocusListener(new FocusListener()
//        {
//            @Override
//            public void focusGained(FocusEvent e)
//            {
//
//            }
//
//            @Override
//            public void focusLost(FocusEvent e)
//            {
//                updateFieldWithValue()
//
//            }
//        });
    }

    @Override
    protected void setPanelsContent()
    {

    }

    public static SoundElement getSoundElement() throws MissingFieldException
    {
        validateFieldsNotEmpty();
        populateFields();

        SoundElement returnElement = new SoundElement(_soundFile.getAbsolutePath(), _start_x, _start_y, _height, _width);

        clearValues();

        return returnElement;
    }

    private static void clearValues()
    {
        _height = 0;
        _width = 0;
        _start_x = 0;
        _start_y = 0;

        _soundFile = null;
    }

    private static void populateFields()
    {
//        String start_xText = _start_x_TextField.getText();
//        String start_yText = _start_y_TextField.getText();
//        String widthText = _width_TextField.getText();
//        String heightText = _height_TextField.getText();


    //    Integer.getInteger(start_xText);


        //TODO: check that the sound file is supported type
    }

    private static void validateFieldsNotEmpty() throws MissingFieldException
    {
//        if (_start_x_TextField.getText().isEmpty())
//        {
//            throw new MissingFieldException("Start X is missing", "what is for");
//        }
//        if (_start_y_TextField.getText().isEmpty())
//        {
//            throw new MissingFieldException("Start Y is missing", "what is for");
//        }
//        if (_width_TextField.getText().isEmpty())
//        {
//            throw new MissingFieldException("Width is missing", "what is for");
//        }
//        if (_height_TextField.getText().isEmpty())
//        {
//            throw new MissingFieldException("Height is missing", "what is for");
//        }
//        if (_soundFile == null)
//        {
//            throw new MissingFieldException("Must select sound file", "what is for");
//        }
    }
}
