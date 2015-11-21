package screens;

import javax.swing.*;
import java.awt.event.*;

public abstract class AbstractScreen extends JFrame {
    private JPanel contentPane;

    public AbstractScreen() {
        setContentPane(contentPane);

// call onExit() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

// call onExit() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onExit();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onExit() {
// add your code here if necessary
        dispose();
        System.exit(0);
    }

    protected void showErrorMessage(JFrame frame, String message) {
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.ERROR_MESSAGE);
    }

    protected void showInformationMessage(JFrame frame,String message) {
        JOptionPane.showMessageDialog(frame, message, null, JOptionPane.INFORMATION_MESSAGE);
    }
}
