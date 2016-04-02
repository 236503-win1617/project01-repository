package AdditionalClasses;

import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;
import SlideObjects.Rotation;
import SlideObjects.VideoSlide;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Evgeniy on 3/19/2016.
 */
public class CurrentSlideManager {
    private final GridBagConstraints pictureConstraints;

    private Map<UUID, UniqueTextPane> slideSoundAreas = new HashMap<>();
    private Map<UUID, SoundElement> slideSoundElements = new HashMap<>();

    private final JPanel currentSlide;
    private final JPanel soundsPanel;

    public CurrentSlideManager(JPanel currentSlide, JPanel soundsPanel) {
        this.pictureConstraints = new GridBagConstraints();
        pictureConstraints.weightx = 0;
        pictureConstraints.weighty = 0;
        pictureConstraints.gridx = 1;
        pictureConstraints.gridy = 1;

        this.currentSlide = currentSlide;
        this.soundsPanel = soundsPanel;
    }

    public void deleteSoundRegion(UUID soundId) {
        UniqueTextPane areaToDelete = slideSoundAreas.remove(soundId);
        if (areaToDelete == null) {
            throw new RuntimeException("No such id exists");
        }
        slideSoundElements.remove(areaToDelete.getId());

        soundsPanel.remove(areaToDelete);
        soundsPanel.repaint();
    }

    public int getSoundsCount() {
        return slideSoundAreas.size();
    }

    public void addSoundToSlide(SoundElement element, UniqueTextPane soundTextPane, GridBagConstraints constraints) {
        UUID uuid = soundTextPane.getId();
        slideSoundAreas.put(uuid, soundTextPane);
        slideSoundElements.put(uuid, element);

        soundsPanel.add(soundTextPane, constraints);
        soundsPanel.revalidate();
    }

    public void saveDataToSlide(AbstractSlide slide) {
        if (slide instanceof PictureSlide) {
            PictureSlide pictureSlide = (PictureSlide) slide;
            if (pictureSlide.getPictureFile() == null) {
                // showErrorMessage("Saving without picture");

                //TODO: let the user create slide without any pictures
            }

            SoundElement[] soundElements = slideSoundElements.values().toArray(new SoundElement[0]);
            pictureSlide.setSoundElements(soundElements);
        } else if (slide instanceof VideoSlide) {
            VideoSlide videoSlide = (VideoSlide) slide;
            // TODO: implement video saving
        } else {
            throw new UnsupportedOperationException("Slide is not supported type" + slide.getClass().toString());
        }
    }

    public void clearContent() {
        slideSoundElements.clear();
        slideSoundAreas.clear();
        clearPanels();
    }

    public void loadPictureFromFile(File imageFile, Rotation pictureRotation) throws IOException {
        currentSlide.removeAll();

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

        currentSlide.add(imageLabel, pictureConstraints);
        currentSlide.revalidate();
        currentSlide.repaint();
    }

    private Image getResizedImage(BufferedImage image, int imageHeight, int imageWidth) {
        int panelHeight = currentSlide.getHeight();
        int panelWidth = currentSlide.getWidth();

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

    private void clearPanels() {
        currentSlide.removeAll();
        currentSlide.repaint();
        soundsPanel.removeAll();
        soundsPanel.repaint();
    }
}
