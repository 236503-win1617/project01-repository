package AdditionalClasses;

import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;
import SlideObjects.SlideType;
import com.sun.corba.se.impl.io.TypeMismatchException;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.UUID;

/**
 * Created by Evgeniy on 3/19/2016.
 */
public class CurrentSlideManager {
    private final GridBagConstraints pictureConstraints;
    //TODO: extend this class to abstract + picture + video

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
        if(areaToDelete == null){
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
        } else {
            throw new IllegalArgumentException("Slide is not supported type" + slide.getClass().toString());
        }
    }

    public void clearContent() {
        slideSoundElements.clear();
        slideSoundAreas.clear();
        clearPanels();
    }

    private void clearPanels() {
        currentSlide.removeAll();
        currentSlide.repaint();
        soundsPanel.removeAll();
        soundsPanel.repaint();
    }

    public void loadPicture(String path) {
        currentSlide.removeAll();
        //TODO: reimplement picture loading according to the dimensions
        ImageIcon imageIcon = new ImageIcon(path);

        int panelHeight = currentSlide.getHeight();
        int panelWidth = currentSlide.getWidth();

        int imageHeight = imageIcon.getIconHeight();
        int imageWidth = imageIcon.getIconWidth();

        if (imageHeight > panelHeight) {
            double factor = ((double) panelHeight / imageHeight);
            int newWidth = (int) (imageWidth * factor);
//        if(imageIcon.getIconHeight() < imageIcon.getIconWidth()){
//            showInformationMessage("vertical");
//        }
            Image image = imageIcon.getImage();
            Image resizedImage = image.getScaledInstance(newWidth, panelHeight, Image.SCALE_SMOOTH); // scale it the smooth way

            imageIcon = new ImageIcon(resizedImage);
        }

        JLabel label = new JLabel("", imageIcon, JLabel.CENTER);

        currentSlide.add(label, pictureConstraints);
        currentSlide.revalidate();
        currentSlide.repaint();
    }
}
