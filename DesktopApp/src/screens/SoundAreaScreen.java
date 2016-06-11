package screens;

import AdditionalClasses.SoundElement;
import Factories.ComponentsFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Created by Evgeniy on 12/7/2015.
 * Last Edited by Eliran on 4/10/2016
 */
public class SoundAreaScreen extends AbstractEmptyScreen {
    private static final String[] SUPPORTED_AUDIO_FORMATS = {".wav", ".mp3", ".wav"};
    private final Integer resolution_X = 1920;
    private final Integer resolution_Y = 1080;
    private final String NO_FILE_IS_SELECTED = "No File Selected";
    private final String INSERT_INTEGER = "Insert Integer Number";

    private final JTextField start_x_TextField;
    private final JTextField start_y_TextField;
    private final JTextField widthTextField;
    private final JTextField heightTextField;

    private final JTextPane selectedFilePane;
    private final JTextPane isFileSelectedPane;

    private final String[] sizesArray = {"0", "1/8", "1/4", "3/8", "1/2", "5/8", "3/4", "7/8"};

    private Integer _start_x;
    private Integer _start_y;
    private Integer _height;
    private Integer _width;

    private File _soundFile;

    protected SoundAreaScreen() throws HeadlessException {
        super();

        setLayout(new GridBagLayout());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(new Dimension(DEFAULT_WIDTH + 50, DEFAULT_HEIGHT + 50));

        JTextPane instructions = ComponentsFactory.createBasicTextPane("Please Choose from the ComboBoxes below the " +
                "coordinates and size you would like the sound component to have");
        JScrollPane instructionsScrollPane = new JScrollPane(instructions);
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

        JComboBox<String> start_x_ComboBox = ComponentsFactory.createBasicComboBox(sizesArray);
        JComboBox<String> start_y_ComboBox = ComponentsFactory.createBasicComboBox(sizesArray);
        JComboBox<String> widthComboBox = ComponentsFactory.createBasicComboBox(sizesArray);
        JComboBox<String> heightComboBox = ComponentsFactory.createBasicComboBox(sizesArray);

        start_x_ComboBox.addActionListener(e -> _start_x = getValueFromComboBox(e, resolution_X, start_x_TextField));
        start_y_ComboBox.addActionListener(e -> _start_y = getValueFromComboBox(e, resolution_Y, start_y_TextField));
        widthComboBox.addActionListener(e -> _width = getValueFromComboBox(e, resolution_X, widthTextField));
        heightComboBox.addActionListener(e -> _height = getValueFromComboBox(e, resolution_Y, heightTextField));

        addInstructionScrollPane(0, instructionsScrollPane);
        addComponentsToRow(1, start_x_pane, start_x_TextField, start_x_ComboBox);
        addComponentsToRow(2, start_y_pane, start_y_TextField, start_y_ComboBox);
        addComponentsToRow(3, widthPane, widthTextField, widthComboBox);
        addComponentsToRow(4, heightPane, heightTextField, heightComboBox);

        JButton chooseFileButton = new JButton("Select Audio File");
        chooseFileButton.addActionListener(e -> onSelectAudioFile());

        setConstraints(0, 5, 1, 1);
        add(isFileSelectedPane, constraints);

        setConstraints(1, 5, 1, 1);
        add(chooseFileButton, constraints);

        setConstraints(0, 6, 1, 1);
        constraints.gridwidth = 3;
        add(selectedFilePane, constraints);

        constraints.gridwidth = 1;

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> onOkButtonPressed());

        setConstraints(0, 7, 1, 1);
        add(okButton, constraints);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> onCancelButtonPressed());

        setConstraints(1, 7, 1, 1);
        add(cancelButton, constraints);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                hideFrame();
            }
        });

        setLocationRelativeTo(null);
    }

    public static boolean isSoundFile(String filePath) {
        return Arrays.stream(SUPPORTED_AUDIO_FORMATS).anyMatch(f -> filePath.toLowerCase().endsWith(f));
    }

    private int getValueFromComboBox(ActionEvent e, int resolution, JTextField textField) {
        JComboBox cb = (JComboBox) e.getSource();
        String size = (String) cb.getSelectedItem();
        Double tmp = getSizeByString(size) * resolution;
        int iSize = tmp.intValue();
        textField.setText(String.valueOf(iSize));
        textField.setEditable(false);
        textField.repaint();
        return iSize;
    }

    //TODO: validate it can be played
    //TODO: remove the copy of audio files to the save method
    private void onSelectAudioFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            loadSoundFile(selectedFile);
        }
    }

    public void loadSoundFile(File selectedFile) {
        String filePathToShow = selectedFile.getAbsolutePath();
        String fileIsSelected = "Selected Audio File";
        Color selectedColor = Color.GREEN;
        try {
            File NewLocation = new File(".\\xmlDir\\" + Screens.CreateLessonScreen.getLessonName() + "\\AASounds\\" + selectedFile.getName());
            Files.copy(selectedFile.toPath(), NewLocation.toPath());
        } catch (Exception ex) {
            Screens.CreateLessonScreen.showErrorMessage(ex.getMessage());
        }
        if (!isSoundFile(filePathToShow)) {
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

    private void onCancelButtonPressed() {
        hideFrame();
    }

    //TODO: maybe switch to a swing worker instead of this
    //TODO: change this for using the same screen for multiple lessons
    private void onOkButtonPressed() {
        boolean success = allValuesNotNull() && allValuesLegit();
        if (!success) {
            showErrorMessage("Unable to create sound area - Not all values were correct");
        } else {
            SoundElement soundElement = new SoundElement(_soundFile, _start_x, _start_y, _height, _width);
            Screens.CreateLessonScreen.addNewSoundElement(soundElement);

            hideFrame();
        }
    }

    // TODO: implement check for the sizes according to 7 inch pixels sizes
    // TODO: check the current element is within borders
    // TODO: verify the current element doesn't intersect with previous ones
    private boolean allValuesLegit() {
        return true;
    }

    private void addComponentsToRow(int row, JTextPane textPane, JTextField textField, JComboBox comboBox) {
        setConstraints(0, row, 1, 1);
        add(textPane, constraints);

        setConstraints(1, row, 1, 1);
        add(textField, constraints);

        setConstraints(2, row, 1, 1);
        add(comboBox, constraints);
    }

    private void addInstructionScrollPane(int row, JScrollPane scrollPane) {
        setConstraints(0, row, 2, 2);
        constraints.gridwidth = 3;
        add(scrollPane, constraints);
        constraints.gridwidth = 1;
    }

    private void hideFrame() {
        Screens.CreateLessonScreen.setVisible(true);
        clearValues();
        setVisible(false);
    }

    private boolean allValuesNotNull() {
        return ((_start_x != null) && (_start_y != null) && (_width != null) && (_height != null) && (_soundFile != null));
    }

    private double getSizeByString(String str) {
        switch (str) {
            case "1/8":
                return 0.125;
            case "1/4":
                return 0.25;
            case "3/8":
                return 0.375;
            case "1/2":
                return 0.5;
            case "5/8":
                return 0.625;
            case "3/4":
                return 0.75;
            case "7/8":
                return 0.875;
            default:
                return 0;
        }
    }

    private void clearValues() {
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
