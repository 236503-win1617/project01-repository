import screens.Screens;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class Main {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            Screens.start();
        } catch (Exception ex) {
            System.err.print(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
