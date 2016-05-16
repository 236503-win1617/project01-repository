package screens;

import javax.swing.*;
import AdditionalClasses.ComboBoxes;
/**
 * Created by apluda on 4/9/2016.
 */
public class ModifyLesson {
    public  JFrame frame;
    public  void run() {
        run_aux(new ComboBoxes(), 200, 125);
    }

    public  void run_aux(JApplet applet, int width, int height) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(width, height);
        applet.init();
        applet.start();
        frame.setVisible(true);
    }
    public  void hide(){
        frame.setVisible(false);
    }
}
