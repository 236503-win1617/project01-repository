package AdditionalClasses;

import Factories.Lesson;
import Factories.XmlParser;
import screens.CreateLessonScreen;
import screens.Screens;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class ComboBoxes extends JApplet {

    public String[] listFilesForFolder(final File folder) {
        List<String> toReturn = new ArrayList<String>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                toReturn.add(fileEntry.getName());
            }
        }
        return toReturn.toArray(new String[toReturn.size()]);
    }

    //private String[] description = { "Ebullient", "Obtuse", "Recalcitrant",
    //        "Brilliant", "Somnescent", "Timorous", "Florid", "Putrescent" };

    private String[] description =listFilesForFolder(new File(".\\xmlDir"));
    private JTextField t = new JTextField(15);

    private JComboBox c = new JComboBox();

    private JButton b = new JButton("load lesson");

    private int count = 0;

    public void init() {
        for (int i = 0; i < description.length; i++)
            c.addItem(description[count++]);
        t.setEditable(false);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String FileSelected = ".\\xmlDir\\"+ t.getText();
                Lesson LoadedLeson = new Lesson();
                XmlParser.parse(FileSelected,"xmlDir\\",LoadedLeson);
                Screens.CreateLessonScreen =  new CreateLessonScreen();
                Screens.CreateLessonScreen.setVisible(true);
                Screens.WelcomeScreen.setVisible(false);
                Screens.CreateLessonScreen.loadExistingLesson(LoadedLeson);
                Screens.ModifyLesson.hide();
            }
        });
        c.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.setText("" + ((JComboBox) e.getSource()).getSelectedItem());

            }
        });
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(t);
        cp.add(c);
        cp.add(b);
    }



} ///:~
