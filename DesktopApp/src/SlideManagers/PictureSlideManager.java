package SlideManagers;

import AdditionalClasses.SoundElement;
import AdditionalClasses.UniqueTextPane;
import Factories.ComponentsFactory;
import Resources.DefaultSizes;
import Resources.FileResources;
import Resources.MessageErrors;
import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;
import SlideObjects.Rotation;
import screens.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Evgeniy on 4/2/2016.
 */
public class PictureSlideManager extends AbstractSlideManager {
    private Map<UUID, UniqueTextPane> slideSoundAreas = new HashMap<>();
    private Map<UUID, SoundElement> slideSoundElements = new HashMap<>();

    private final JPanel soundsPanel;
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

        if (pictureFile == null) { // No picture was selected
            return;
        }
        currentSlide.rotateSlide();
        Rotation pictureRotation = currentSlide.getRotation();

        loadPictureFromFile(pictureFile, pictureRotation);
    }

    @Override
    public void loadSlide(AbstractSlide slide) throws IOException {
        setSpecificButtonsVisibility(true);

        if (!slide.getClass().equals(PictureSlide.class)) {
            throw new UnsupportedOperationException();
        }

        currentSlide = (PictureSlide) slide;

        File pictureToLoad = currentSlide.getPictureFile();
        if (pictureToLoad == null) {
            pictureToLoad = FileResources.noPictureAvailable;
        }
        loadPictureFromFile(pictureToLoad, currentSlide.getRotation());

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
        setConstraints(0, 4, 1, 1);
        choosePictureButton.setVisible(false);
        commandsPanel.add(choosePictureButton, constraints);
    }

    private void setAddSoundButton() {
        addSoundButton = new JButton("Add Sound");
        addSoundButton.addActionListener(e -> addSound());
        setConstraints(0, 5, 1, 1);
        addSoundButton.setVisible(false);
        commandsPanel.add(addSoundButton, constraints);
    }

    private void setRemoveSoundButton() {
        removeSoundButton = new JButton("Remove Sound");
        removeSoundButton.addActionListener(e -> onDeleteSoundRegion());
        setConstraints(0, 6, 1, 1);
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

    private void addSound() {
        //TODO: change this method
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
                loadPictureFromFile(selectedFile, Rotation.NO_ROTATION);
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
