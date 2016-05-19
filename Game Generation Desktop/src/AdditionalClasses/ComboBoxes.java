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
import java.util.Arrays;

public class ComboBoxes extends JApplet {

    public static List<File> listf(String directoryName) {
        File directory = new File(directoryName);

        List<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                //System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath()));
            }
        }
        //System.out.println(fList);
        return resultList;
    }


    public String[] returnAllXmlFiles(String directoryName){
        List<String> toReturn = new ArrayList<String>();
        List<File> allFiles = listf(directoryName);
        for (File f : allFiles){
            if((f.getName()).endsWith(".xml")){
                toReturn.add(f.getName());
            }
        }
        //return toReturn.toArray(new String[toReturn.size()]);

        List<String> test = new ArrayList<String>();
        test.add("2");

        //return test.toArray(new String[toReturn.size()]);
        return toReturn.toArray(new String[toReturn.size()]);
    }

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

    //private String[] description =listFilesForFolder(new File(".\\xmlDir"));

    private String[] description =returnAllXmlFiles(".\\xmlDir");
    //listFiles();
    private JTextField t = new JTextField(15);

    private JComboBox c = new JComboBox();

    private JButton b = new JButton("load lesson");

    private int count = 0;

    public void init() {
        listf(".\\xmlDir");
        for (int i = 0; i < description.length; i++)
            c.addItem(description[count++]);

        String Test = "db-1794418580.xml";
        System.out.println(Test.substring(0,Test.length()-4));
        t.setEditable(false);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String toLoadName = t.getText();
                String FileSelected = ".\\xmlDir\\"+ toLoadName.substring(0,toLoadName.length()-4) + "\\" + toLoadName;
                Lesson LoadedLeson = new Lesson();
                //change here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11 the path for this function.
                XmlParser.parse(FileSelected,"xmlDir\\"+toLoadName.substring(0,toLoadName.length()-4),LoadedLeson);
                Screens.CreateLessonScreen =  new CreateLessonScreen();
                Screens.CreateLessonScreen.setVisible(true);
                Screens.WelcomeScreen.setVisible(false);
                Screens.CreateLessonScreen.loadExistingLesson(LoadedLeson,toLoadName.substring(0,toLoadName.length()-4));
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
