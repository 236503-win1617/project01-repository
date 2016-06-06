package SlideManagers;

import AdditionalClasses.RotatedIcon;
import AdditionalClasses.SoundElement;
import Factories.ComponentsFactory;
import screens.CreateLessonScreen;
import slides.AbstractSlide;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import slides.Rotation;

/**
 * Created by Evgeniy on 4/2/2016.
 */
public abstract class AbstractSlideManager {
    protected final JPanel commandsPanel;
    protected final JPanel currentSlidePanel;

    protected GridBagConstraints constraints = ComponentsFactory.getDefaultConstraints();
    protected JButton slideButton;

    public AbstractSlideManager(JPanel currentSlidePanel, JPanel commandsPanel) {
        this.currentSlidePanel = currentSlidePanel;
        this.commandsPanel = commandsPanel;

        setSpecificCommandsButtons();
    }

    //region Abstract Methods

    public abstract void onRotateCommand() throws IOException;

    public abstract void loadSlide(AbstractSlide slide, JButton slideButton) throws IOException;

    public abstract void saveDataToCurrentSlide();

    protected abstract void setSpecificCommandsButtons();

    protected abstract void specificClearContent();

    protected abstract void setSpecificButtonsVisibility(boolean visibility);

    //endregion

    public void clearContent() {
        setSpecificButtonsVisibility(false);

        commandsPanel.repaint();

        currentSlidePanel.removeAll();
        currentSlidePanel.repaint();

        specificClearContent();
    }

    protected void setConstraints(int grid_x, int grid_y, double weight_x, double weight_y) {
        constraints.weightx = weight_x;
        constraints.weighty = weight_y;
        constraints.gridx = grid_x;
        constraints.gridy = grid_y;
    }

    protected void loadImageToSlidePanel(BufferedImage image, double rotationRadians) throws IOException {
        currentSlidePanel.removeAll();

        Image resizedImage = ComponentsFactory.getResizedImage(image, currentSlidePanel.getHeight() - 10, currentSlidePanel.getWidth() - 10);
        RotatedIcon icon = new RotatedIcon(resizedImage, rotationRadians);

        JLabel imageLabel = new JLabel("", icon, JLabel.CENTER);

        setConstraints(0, 0, 1, 1);

        currentSlidePanel.add(imageLabel, constraints);
        currentSlidePanel.revalidate();
        currentSlidePanel.repaint();
    }

    protected void setImageOnSlideButton(BufferedImage image, double radianRotation) {
        int size = CreateLessonScreen.SLIDE_BUTTON_SIZE - 5;
        Image resizedImage = ComponentsFactory.getResizedImage(image, size, size);
        ComponentsFactory.setButtonImage(resizedImage, slideButton, radianRotation);
    }

    protected void loadSameImageToPanelAndButton(BufferedImage image, double rotationRadians) throws IOException {
        loadImageToSlidePanel(image, rotationRadians);
        setImageOnSlideButton(image, rotationRadians);
    }
    protected void loadPictureFromFile(InputStream imageFile, Rotation pictureRotation) throws IOException {
        currentSlidePanel.removeAll();

        BufferedImage image = ImageIO.read(imageFile);

        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();

        AffineTransform tx = new AffineTransform();
        tx.rotate(pictureRotation.getRotationInRadians(), imageWidth / 2, imageHeight / 2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        image = op.filter(image, null);

        Image resizedImage = getResizedImage(image, imageHeight, imageWidth);

        ImageIcon imageIcon = new ImageIcon(resizedImage);
        JLabel imageLabel = new JLabel("", imageIcon, JLabel.CENTER);

        setConstraints(0, 0, 1, 1);

        currentSlidePanel.add(imageLabel, constraints);
        currentSlidePanel.revalidate();
        currentSlidePanel.repaint();
    }
    private Image getResizedImage(BufferedImage image, int imageHeight, int imageWidth) {
        int panelHeight = currentSlidePanel.getHeight();
        int panelWidth = currentSlidePanel.getWidth();

        if ((imageHeight <= panelHeight) && (imageWidth <= panelWidth)) {
            return image;
        }

        double heightFactor = ((double) panelHeight / imageHeight);
        double widthFactor = ((double) panelWidth / imageWidth);
        double factor = Double.max(heightFactor, widthFactor);

        int newWidth = (int) (imageWidth * factor);
        int newHeight = (int) (imageHeight * factor);

        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    public abstract void addNewSoundElement(SoundElement soundElement);
}

