package screens;

import AdditionalClasses.SoundElement;
import Factories.ComponentsFactory;
import SlideObjects.Rotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;

/**
 * Created by Evgeniy on 12/7/2015.
 */
public class SoundAreaScreen extends AbstractEmptyScreen
{
    protected final String[] SUPPORTED_AUDIO_FORMATS = {".wav", ".mp3"};
    private final String NO_FILE_IS_SELECTED = "No File Selected";
    private final String INSERT_INTEGER = "Insert Integer Number";

    private final JTextField start_x_TextField;
    private final JTextField start_y_TextField;
    private final JTextField widthTextField;
    private final JTextField heightTextField;

    private final JTextPane selectedFilePane;
    private final JTextPane isFileSelectedPane;

    private Integer _start_x;
    private Integer _start_y;
    private Integer _height;
    private Integer _width;

    private File _soundFile;

    protected SoundAreaScreen() throws HeadlessException
    {
        super();

        //TODO: remove to parent
        setLayout(new GridBagLayout());

        setDefaultCloseOperation(HIDE_ON_CLOSE);

        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        JTextPane start_x_pane = ComponentsFactory.createBasicTextPane("Start X:");
        JTextPane start_y_pane = ComponentsFactory.createBasicTextPane("Start Y:");
        JTextPane widthPane = ComponentsFactory.createBasicTextPane("Width:");
        JTextPane heightPane = ComponentsFactory.createBasicTextPane("Height:");
        selectedFilePane = ComponentsFactory.createBasicTextPane("");
        isFileSelectedPane = ComponentsFactory.createBasicTextPane(NO_FILE_IS_SELECTED);
        isFileSelectedPane.setForeground(Color.RED);

        start_x_TextField = ComponentsFactory.createBasicTextField(INSERT_INTEGER);
        start_y_TextField = ComponentsFactory.createBasicTextField(INSERT_INTEGER);
        widthTextField = ComponentsFactory.createBasicTextField(INSERT_INTEGER);
        heightTextField = ComponentsFactory.createBasicTextField(INSERT_INTEGER);

        start_x_TextField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                String input = ((JTextField) e.getSource()).getText();
                _start_x = getIntValue(input);
            }
        });

        start_y_TextField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                String input = ((JTextField) e.getSource()).getText();
                _start_y = getIntValue(input);
            }
        });

        widthTextField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                String input = ((JTextField) e.getSource()).getText();
                _width = getIntValue(input);
            }
        });

        heightTextField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                String input = ((JTextField) e.getSource()).getText();
                _height = getIntValue(input);
            }
        });

        //TODO: add sound file verification

        addPaneAndTextField(0, start_x_pane, start_x_TextField);
        addPaneAndTextField(1, start_y_pane, start_y_TextField);
        addPaneAndTextField(2, widthPane, widthTextField);
        addPaneAndTextField(3, heightPane, heightTextField);

        JButton chooseFileButton = new JButton("Select Audio File");
        chooseFileButton.addActionListener(e -> onSelectAudioFile());

        setConstraints(0, 4, 1, 1);
        add(isFileSelectedPane, constraints);

        setConstraints(1, 4, 1, 1);
        add(chooseFileButton, constraints);

        setConstraints(0, 5, 1, 1);
        constraints.gridwidth = 3;
        add(selectedFilePane, constraints);

        constraints.gridwidth = 1;

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> onOkButtonPressed());

        setConstraints(0, 6, 1, 1);
        add(okButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> onCancelButtonPressed());

        setConstraints(1, 6, 1, 1);
        add(cancelButton, constraints);

        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                onClosingWindow();
            }
        });

        setLocationRelativeTo(null);
    }

    private void onSelectAudioFile()
    {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = chooser.getSelectedFile();
            String filePathToShow = selectedFile.getAbsolutePath();
            String fileIsSelected = "Selected Audio File";
            Color selectedColor = Color.GREEN;
            try {
                File NewLocation = new File(".\\xmlDir\\AASounds\\" + selectedFile.getName());
                Files.copy(selectedFile.toPath(),NewLocation.toPath());
            } catch (Exception ex) {
                Screens.CreateLessonScreen.showErrorMessage(ex.getMessage());
            }

            //TODO: validate it can be played

            if (!isFileSupportedType(filePathToShow))
            {
                showErrorMessage("The file format isn't supported");
                filePathToShow = "";
                selectedFile = null;
                fileIsSelected = NO_FILE_IS_SELECTED;
                selectedColor = Color.RED;
            }

            _soundFile = selectedFile;
            isFileSelectedPane.setText(fileIsSelected);
            isFileSelectedPane.setForeground(selectedColor);
            selectedFilePane.setText(filePathToShow);
        }
    }

    private boolean isFileSupportedType(String filePath)
    {
        for (int i = 0; i < SUPPORTED_AUDIO_FORMATS.length; i++)
        {
            if (filePath.endsWith(SUPPORTED_AUDIO_FORMATS[i]))
            {
                return true;
            }
        }

        return false;
    }

    private void onCancelButtonPressed()
    {
        //Same as on closing window
        onClosingWindow();
    }

    private void onOkButtonPressed()
    {
        boolean success = allValuesNotNull() && allValuesLegit();

        if (success)
        {
            //TODO: maybe switch to a swing worker instead of this

            SoundElement soundElement = new SoundElement(_soundFile, _start_x, _start_y, _height, _width);

            Screens.CreateLessonScreen.addNewSoundElement(soundElement);

            //TODO: change this for using the same screen for multiple lessons

            hideFrame();
        }
        else
        {
            showErrorMessage("Unable to create sound area - Not all values were correct");
        }
    }

    private boolean allValuesLegit()
    {
        // TODO: implement check for the sizes according to 7 inch pixels sizes
        return true;
    }

    private void addPaneAndTextField(int row, JTextPane textPane, JTextField textField)
    {
        setConstraints(0, row, 1, 1);
        add(textPane, constraints);

        setConstraints(1, row, 1, 1);
        add(textField, constraints);
    }

    protected void onClosingWindow()
    {
        // Should do nothing - only clear values and hide
        hideFrame();
    }

    private void hideFrame()
    {
        Screens.CreateLessonScreen.setVisible(true);
        clearValues();
        setVisible(false);
    }

    private Integer getIntValue(String input)
    {
        if (input == null || input.isEmpty())
        {
            return null;
        }

        input = input.trim();
        Integer tmp;

        try
        {
            tmp = Integer.valueOf(input);
        }
        catch (Exception ex)
        {
            tmp = null;
            showErrorMessage(input + " isn't a supported integer value" + "\n" + ex.getMessage());
        }

        return tmp;
    }

    private boolean allValuesNotNull()
    {
        return ((_start_x != null) && (_start_y != null) && (_width != null) && (_height != null) && (_soundFile != null));
    }

    private void clearValues()
    {
        start_x_TextField.setText("");
        start_y_TextField.setText("");
        widthTextField.setText("");
        heightTextField.setText("");
        selectedFilePane.setText("");

        isFileSelectedPane.setText(NO_FILE_IS_SELECTED);
        isFileSelectedPane.setForeground(Color.RED);

        _height = null;
        _width = null;
        _start_x = null;
        _start_y = null;

        _soundFile = null;
    }
}
