package SlideManagers;

import AdditionalClasses.SoundElement;
import AdditionalClasses.UniqueTextPane;
import Factories.ComponentsFactory;
import Resources.DefaultSizes;
import Resources.FileResources;
import Resources.MessageErrors;
import screens.Screens;
import slides.AbstractSlide;
import slides.PictureSlide;
import slides.Rotation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Evgeniy on 4/2/2016.
 */
public class PictureSlideManager extends AbstractSlideManager {
    private final JPanel soundsPanel;
    private Map<UUID, UniqueTextPane> slideSoundAreas = new HashMap<>();
    private Map<UUID, SoundElement> slideSoundElements = new HashMap<>();
    private PictureSlide currentSlide;
    private UUID selectedSoundId;

    private JButton choosePictureButton;
    private JButton addSoundButton;
    private JButton removeSoundButton;

    public PictureSlideManager(JPanel currentSlide, JPanel commandsPanel, JPanel soundsPanel) {
        super(currentSlide, commandsPanel);

        this.soundsPanel = soundsPanel;
    }

    @Override
    public void onRotateCommand() throws IOException {
        if (currentSlide == null) {
            throw new NullPointerException("The current slide wasn't set");
        }

        File pictureFile = currentSlide.getPictureFile();
        if (pictureFile == null) {
            Screens.CreateLessonScreen.showErrorMessage("No picture to rotate");
            return;
        }
        currentSlide.rotateSlide();

        InputStream pictureStream = new FileInputStream(pictureFile);
        loadSameImageToPanelAndButton(ImageIO.read(pictureStream), currentSlide.getRotation().getRotationInRadians());
    }

    @Override
    public void loadSlide(AbstractSlide slide, JButton slideButton) throws IOException {
        setSpecificButtonsVisibility(true);

        if (!slide.getClass().equals(PictureSlide.class)) {
            throw new UnsupportedOperationException();
        }

        currentSlide = (PictureSlide) slide;
        this.slideButton = slideButton;

        File pictureFile = currentSlide.getPictureFile();
        Rotation rotation = currentSlide.getRotation();

        if (pictureFile != null) {
            InputStream pictureStream = new FileInputStream(pictureFile);
            loadSameImageToPanelAndButton(ImageIO.read(pictureStream), rotation.getRotationInRadians());
        } else { // Load defaults
            double rotationRadians = Rotation.NO_ROTATION.getRotationInRadians();
            loadImageToSlidePanel(ImageIO.read(FileResources.getNoPictureStream()), rotationRadians);
            setImageOnSlideButton(ImageIO.read(FileResources.getButtonNoPicture()), rotationRadians);
        }

        for (SoundElement element : currentSlide.getSoundElements()) {
            addNewSoundElement(element);
        }
    }

    @Override
    protected void setSpecificCommandsButtons() {
        setChoosePictureButton();
        setRemoveSoundButton();
        setAddSoundButton();
    }

    @Override
    public void saveDataToCurrentSlide() {
        SoundElement[] soundElements = slideSoundElements.values().toArray(new SoundElement[0]);
        currentSlide.setSoundElements(soundElements);
    }

    @Override
    protected void setSpecificButtonsVisibility(boolean visibility) {
        choosePictureButton.setVisible(visibility);
        addSoundButton.setVisible(visibility);
        removeSoundButton.setVisible(visibility);
    }

    @Override
    protected void specificClearContent() {
        slideSoundElements.clear();
        slideSoundAreas.clear();

        selectedSoundId = null;
        currentSlide = null;

        soundsPanel.removeAll();
        soundsPanel.revalidate();
        soundsPanel.repaint();
    }

    //region Commands buttons

    private void setChoosePictureButton() {
        choosePictureButton = new JButton("Select Picture");
        choosePictureButton.addActionListener(e -> selectPictureFile());
        setConstraints(0, 5, 1, 1);
        choosePictureButton.setVisible(false);
        commandsPanel.add(choosePictureButton, constraints);
    }

    private void setAddSoundButton() {
        addSoundButton = new JButton("Add Sound");
        addSoundButton.addActionListener(e -> addSound());
        setConstraints(0, 6, 1, 1);
        addSoundButton.setVisible(false);
        commandsPanel.add(addSoundButton, constraints);
    }

    private void setRemoveSoundButton() {
        removeSoundButton = new JButton("Remove Sound");
        removeSoundButton.addActionListener(e -> onDeleteSoundRegion());
        setConstraints(0, 7, 1, 1);
        removeSoundButton.setVisible(false);
        commandsPanel.add(removeSoundButton, constraints);
    }

    //endregion

    private void onDeleteSoundRegion() {
        if (selectedSoundId == null) {
            Screens.CreateLessonScreen.showErrorMessage("No Sound Region Selected");
            return;
        }

        deleteSoundRegion(selectedSoundId);
        selectedSoundId = null;
    }

    //TODO: change this method
    private void addSound() {
        if (currentSlide == null) {
            Screens.CreateLessonScreen.showErrorMessage(MessageErrors.NO_SLIDE_TO_EXECUTE_ON);
            return;
        }

        Screens.SoundAreaScreen.setVisible(true);
        Screens.CreateLessonScreen.setVisible(false);
    }

    private void selectPictureFile() {
        if (currentSlide == null) {
            Screens.CreateLessonScreen.showErrorMessage(MessageErrors.DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(Screens.CreateLessonScreen) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            if (!currentSlide.isFileNameSupported(selectedFile.getName())) {
                Screens.CreateLessonScreen.showErrorMessage(selectedFile.getName() + "Isn't supported picture format");
                return;
            }

            currentSlide.setSlideFile(selectedFile);
            try {
                BufferedImage image = ImageIO.read(new FileInputStream(selectedFile));
                loadSameImageToPanelAndButton(image, Rotation.NO_ROTATION.getRotationInRadians());
            } catch (Exception ex) {
                Screens.CreateLessonScreen.showErrorMessage(ex.getMessage());
                ex.printStackTrace();
            }
            try {
                loadPictureFromFile(new FileInputStream(selectedFile), Rotation.NO_ROTATION);



                File NewLocation = new File(".\\xmlDir\\"+ Screens.CreateLessonScreen.getLessonName() + "\\AAImages\\" + selectedFile.getName());
                Files.copy(selectedFile.toPath(),NewLocation.toPath());
            } catch (Exception ex) {
                Screens.CreateLessonScreen.showErrorMessage(ex.getMessage());
            }
        }
    }

    private Dimension getSoundAreaDimension() {
        int width = soundsPanel.getWidth() - (DefaultSizes.DEFAULT_INSET * 2);
        int height = soundsPanel.getHeight() / 5;

        return new Dimension(width, height);
    }

    public void addNewSoundElement(SoundElement soundElement) {
        UUID uuid = UUID.randomUUID();
        UniqueTextPane soundTextPane = ComponentsFactory.createUniqueTextPane(uuid, soundElement.toString());

        soundTextPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                UniqueTextPane textArea = (UniqueTextPane) e.getSource();
                selectedSoundId = textArea.getId();
            }
        });

        Dimension soundAreaDimension = getSoundAreaDimension();
        ComponentsFactory.setElementConstSize(soundTextPane, soundAreaDimension);

        constraints.anchor = GridBagConstraints.PAGE_START;
        setConstraints(0, slideSoundElements.size(), 0, 0);

        slideSoundAreas.put(uuid, soundTextPane);
        slideSoundElements.put(uuid, soundElement);

        soundsPanel.add(soundTextPane, constraints);
        soundsPanel.revalidate();
    }

    private void deleteSoundRegion(UUID soundId) {
        UniqueTextPane areaToDelete = slideSoundAreas.remove(soundId);
        if (areaToDelete == null) {
            throw new RuntimeException("No such id exists");
        }
        slideSoundElements.remove(areaToDelete.getId());

        soundsPanel.remove(areaToDelete);
        soundsPanel.revalidate();
        soundsPanel.repaint();
    }
}