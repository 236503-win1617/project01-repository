package Factories;

import AdditionalClasses.RotatedIcon;
import AdditionalClasses.UniqueTextPane;
import Resources.DefaultSizes;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class ComponentsFactory {
    private static String DEFAULT_FONT_NAME = "Ariel";
    private static Font DEFAULT_FONT = new Font(DEFAULT_FONT_NAME, Font.PLAIN, DefaultSizes.DEFAULT_FONT_SIZE);

    public static GridBagConstraints getDefaultConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(DefaultSizes.DEFAULT_INSET, DefaultSizes.DEFAULT_INSET, DefaultSizes.DEFAULT_INSET, DefaultSizes.DEFAULT_INSET);
        constraints.fill = GridBagConstraints.BOTH;
        return constraints;
    }

    public static JTextPane createBasicTextPane(String text) {
        JTextPane textPane = new JTextPane();
        setTextPaneDefaultParams(textPane, text);
        return textPane;
    }

    public static UniqueTextPane createUniqueTextPane(UUID uuid, String text) {
        UniqueTextPane uniquePane = new UniqueTextPane(uuid);
        setTextPaneDefaultParams(uniquePane, text);
        return uniquePane;
    }

    public static JScrollPane createScrollPane(JPanel panel, int width, int height, int constantHorizontal, int constantVertical) {
        panel.setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(constantHorizontal);
        scrollPane.setVerticalScrollBarPolicy(constantVertical);

        Dimension dimensions = new Dimension(width, height);
        scrollPane.setPreferredSize(dimensions);

        return scrollPane;
    }

    public static JTextField createBasicTextField(String helperText) {
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setToolTipText(helperText);
        textField.setForeground(Color.black);
        textField.setFont(DEFAULT_FONT);
        textField.setBackground(SystemColor.menu);

        return textField;
    }

    public static void setElementConstSize(JComponent element, Dimension dimension) {
        element.setPreferredSize(dimension);
        element.setMaximumSize(dimension);
        element.setMinimumSize(dimension);
    }

    public static void setImageOnButton(Image image, JButton button, double rotationRadians) {
        button.removeAll();
        RotatedIcon rotatedIcon = new RotatedIcon(image, rotationRadians);
        button.setIcon(rotatedIcon);
    }

    public static Image getResizedImage(BufferedImage image, int toHeight, int toWidth) {
        int originalHeight = image.getHeight();
        int originalWidth = image.getWidth();

        if ((originalHeight <= toHeight) && (originalWidth <= toWidth)) {
            return image;
        }

        double heightFactor = ((double) toHeight / originalHeight);
        double widthFactor = ((double) toWidth / originalWidth);
        double factor = Double.min(heightFactor, widthFactor);

        int newWidth = (int) (originalWidth * factor);
        int newHeight = (int) (originalHeight * factor);

        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    public static JComboBox<String> createBasicComboBox(String[] values) {
        JComboBox<String> comboBox = new JComboBox<>(values);
        comboBox.setSelectedIndex(0);
        comboBox.setForeground(Color.BLACK);
        comboBox.setFont(new Font(DEFAULT_FONT_NAME, Font.BOLD, 14));
        comboBox.setMaximumRowCount(10);

        return comboBox;
    }

    private static void setTextPaneDefaultParams(JTextPane textPane, String text) {
        textPane.setForeground(Color.BLACK);
        textPane.setText(text);
        textPane.setEditable(false);
        textPane.setOpaque(false);
        textPane.setFont(DEFAULT_FONT);
    }
}
