package AdditionalClasses;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Evgeniy on 5/14/2016.
 */
public class RotatedIcon extends ImageIcon {
    private double radians;

    public RotatedIcon(Image image, double radians) {
        super(image);
        this.radians = radians;
    }

    @Override
    public int getIconWidth() {
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        return (int) Math.floor(super.getIconWidth() * cos + super.getIconHeight() * sin);
    }

    @Override
    public int getIconHeight() {
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        return (int) Math.floor(super.getIconHeight() * cos + super.getIconWidth() * sin);
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();

        int cWidth = super.getIconWidth() / 2;
        int cHeight = super.getIconHeight() / 2;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(x, y, getIconWidth(), getIconHeight());
        g2.translate((getIconWidth() - super.getIconWidth()) / 2, (getIconHeight() - super.getIconHeight()) / 2);
        g2.rotate(radians, x + cWidth, y + cHeight);
        super.paintIcon(c, g2, x, y);

        g2.dispose();
    }
}