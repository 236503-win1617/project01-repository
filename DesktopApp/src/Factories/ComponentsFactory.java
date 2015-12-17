package Factories;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class ComponentsFactory
{
    private static Integer DEFAULT_BUTTON_WIDTH = 200;
    private static Integer DEFAULT_BUTTON_HEIGHT = 45;

    private static Integer DEFAULT_TEXT_HEIGHT = 25;
    private static Integer DEFAULT_FONT_SIZE = 20;

    private static String DEFAULT_FONT = "Ariel";

    public static JButton createDefaultButton(String text, int start_x, int start_y)
    {
        return createButon(text, start_x, start_y, DEFAULT_BUTTON_WIDTH, DEFAULT_BUTTON_HEIGHT);
    }

    public static JButton createButon(String text, int start_x, int start_y, int width, int height)
    {
        JButton button = new JButton(text);
        button.setBounds(start_x, start_y, width, height);
        return button;
    }

    public static JTextPane createBasicTexPane(String text)
    {
        JTextPane textPane = new JTextPane();
        textPane.setText(text);
        textPane.setEditable(false);
        textPane.setFont(new Font(DEFAULT_FONT, Font.PLAIN, DEFAULT_FONT_SIZE));
        textPane.setForeground(Color.BLACK);
        textPane.setBackground(SystemColor.menu);

        return textPane;
    }

    /**
     * @param panel
     * @param width
     * @param height
     * @param constantHorizontal JScrollPaneConstant for horizontal scrolling
     * @param constantVertical   JScrollPaneConstant for vertical scrolling
     * @return
     */
    public static JScrollPane createScrollPane(JPanel panel, int width, int height, int constantHorizontal, int constantVertical)
    {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(constantHorizontal);
        scrollPane.setVerticalScrollBarPolicy(constantVertical);

        Dimension dimensions = new Dimension(width, height);
        scrollPane.setPreferredSize(dimensions);

        return scrollPane;
    }

    public static JTextField createBasicTextField(String helperText)
    {
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setToolTipText(helperText);
        textField.setForeground(Color.black);
        textField.setFont(new Font(DEFAULT_FONT, Font.PLAIN, DEFAULT_FONT_SIZE));
        textField.setBackground(SystemColor.menu);

        return textField;
    }

    /**
     * Will create a basic combo box
     *
     * @param values     the values inside the combo box
     * @param helperText the description text
     * @param start_x
     * @param start_y
     * @param width
     * @param height
     * @return
     */
    public static JComboBox createComboBox(Object[] values, String helperText, int start_x, int start_y, int width, int height)
    {
        JComboBox comboBox = new JComboBox();
        comboBox.setToolTipText(helperText);
        comboBox.setModel(new DefaultComboBoxModel(values));
        comboBox.setBounds(start_x, start_y, width, height);

        return comboBox;
    }
}
