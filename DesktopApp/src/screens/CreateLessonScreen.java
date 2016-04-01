package screens;

import AdditionalClasses.*;
import Factories.ComponentsFactory;
import Factories.CreateXmlFactory;
import SlideObjects.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class CreateLessonScreen extends AbstractApplicationScreen {
    private static final String DEFAULT_NO_SLIDE_ERROR = "No slide was created !";
    private static final String CANNOT_SEND_MAIL_ERROR = "Cannot send mail !";
    private static final String UNEXPECTED_ERROR = "Something very bad has happened";
    private static final String MAIL_TO_HEADER = "MailTo:evgenhvost@gmail.com?Subject=";

    private static final int TEXT_AREA_HEIGHT = 100;
    private static final int SLIDE_BUTTON_SIZE = 80;

    private final CurrentSlideManager currentSlideManager;

    //region Panels

    private JPanel currentSlidePanel;
    private JPanel soundsPanel;
    private JPanel lessonSlidesPanel;
    private JPanel commandsPanel;
    private JPanel screenMenuPanel;

    //endregion

    //TODO: Implement good saving

    private ArrayList<IndexedButton> _slidesButtons = new ArrayList<>();
    private ArrayList<AbstractSlide> _slides = new ArrayList<>();

    private Integer currentSlideIndex = -1;
    private UUID selectedSoundId;

    public CreateLessonScreen() {
        super();

        setMenuPanelButtons();
        setCommandsPanelButtons();

        this.currentSlideManager = new CurrentSlideManager(currentSlidePanel, soundsPanel);
    }

    private void setAddSoundRegionButton() {
        JButton addSoundButton = new JButton("Add Sound");
        addSoundButton.addActionListener(e -> addSound());
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        setConstraints(0, 3, 1, 1);
        commandsPanel.add(addSoundButton, constraints);
    }

    private void addSound() {
        if (currentSlideIndex < 0) {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        Screens.SoundAreaScreen.setVisible(true);
        setVisible(false);
    }

    public void addNewSoundElementToCurrentSlide(SoundElement soundElement) {
        if (soundElement == null) {
            showErrorMessage(UNEXPECTED_ERROR);
            return;
        }

        //TODO: make a nicer text display

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
        setElementConstSize(soundTextPane, soundAreaDimension);

        constraints.anchor = GridBagConstraints.PAGE_START;
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        setConstraints(0, currentSlideManager.getSoundsCount(), 0, 0);

        currentSlideManager.addSoundToSlide(soundElement, soundTextPane, constraints);
    }

    private void setElementConstSize(JComponent element, Dimension dimension) {
        element.setPreferredSize(dimension);
        element.setMaximumSize(dimension);
        element.setMinimumSize(dimension);
    }

    private Dimension getSoundAreaDimension() {
        int width = soundsPanel.getWidth() - (DEFAULT_BUTTONS_INSETS * 2);
        int height = TEXT_AREA_HEIGHT;

        return new Dimension(width, height);
    }

    private void chooseFile() {
        if (currentSlideIndex < 0) {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            AbstractSlide currentSlide = _slides.get(currentSlideIndex);
            SlideType currentType = currentSlide.getType();

            if (!currentSlide.isFileNameSupported(selectedFile.getName())) {
                showErrorMessage(String.format("The file '%s' is not supported for slide '%s'", selectedFile.getName(), currentType));
                return;
            }

            currentSlide.setSlideFile(selectedFile);
            try {
                if (currentType == SlideType.Picture) {
                    currentSlideManager.loadPictureFromFile(selectedFile, Rotation.NO_ROTATION);
                } else if (currentType == SlideType.Video) {
                    showInformationMessage("The video was added to the slide");
                    currentSlideManager.loadPictureFromFile(FileResources.okPicture, Rotation.NO_ROTATION);
                } else {
                    showErrorMessage("Not supported type :" + currentType.toString());
                }
            } catch (Exception ex) {
                showErrorMessage(ex.getMessage());
            }
        }
    }

    private void loadPictureSlide(PictureSlide pictureSlide) {
        File pictureToLoad = pictureSlide.getPictureFile();
        if (pictureToLoad == null) {
            pictureToLoad = FileResources.noPictureAvailable;
        }
        try {
            currentSlideManager.loadPictureFromFile(pictureToLoad, pictureSlide.getRotation());
            for (SoundElement element : pictureSlide.getSoundElements()) {
                addNewSoundElementToCurrentSlide(element);
            }
        } catch (Exception ex) {
            showErrorMessage("Error during loading of picture slide " + ex.getMessage());
        }
    }

    private void deleteSlide() {
        if (currentSlideIndex != _slidesButtons.size() - 1) {
            //TODO: implement delete in the middle

            showErrorMessage("Delete is supported only for the last slide");
            return;
        }

        if (currentSlideIndex == -1) {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        int result = showYesNoMessage("Are you sure you want to delete the slide ?");

        if (result == JOptionPane.YES_OPTION) {
            currentSlideManager.clearContent();

            JButton button = _slidesButtons.get(currentSlideIndex);

            lessonSlidesPanel.remove(button);
            lessonSlidesPanel.repaint();

            removeIndexFromContainers(currentSlideIndex);
            currentSlideIndex--;

            if (currentSlideIndex >= 0) {
                loadSlide(currentSlideIndex);
            }
        }
    }

    @Override
    protected void setScreenPanels() {
        currentSlidePanel = new JPanel();
        soundsPanel = new JPanel();
        lessonSlidesPanel = new JPanel();
        screenMenuPanel = new JPanel();
        commandsPanel = new JPanel();
    }

    @Override
    protected void setPanelsContent() {
        //TODO: maybe switch to a fixed size according to the tablet size

        //TODO: switch to gridbag constraints
        setPanel(currentSlidePanel, Color.BLACK, (SCREEN_WIDTH * 4) / 6, (SCREEN_HEIGHT * 4) / 6, BorderLayout.CENTER);
        setPanel(commandsPanel, Color.GREEN, SCREEN_WIDTH / 6, (SCREEN_HEIGHT * 5) / 6, BorderLayout.EAST);
        setPanel(screenMenuPanel, Color.RED, (SCREEN_WIDTH * 4) / 6, SCREEN_HEIGHT / 6, BorderLayout.NORTH);

        soundsPanel.setBackground(Color.YELLOW);
        lessonSlidesPanel.setBackground(Color.BLUE);

        JScrollPane lessonSlides = ComponentsFactory.createScrollPane(lessonSlidesPanel, SCREEN_WIDTH, SCREEN_HEIGHT / 6, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        JScrollPane slideSounds = ComponentsFactory.createScrollPane(soundsPanel, SCREEN_WIDTH / 6, (SCREEN_HEIGHT * 5) / 6, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(slideSounds, BorderLayout.WEST);
        add(lessonSlides, BorderLayout.SOUTH);
    }

    private void setPanel(JPanel panel, Color color, int width, int height, String location) {
        panel.setBackground(color);
        panel.setLayout(new GridBagLayout());
        Dimension dimensions = new Dimension(width, height);
        panel.setPreferredSize(dimensions);
        add(panel, location);
    }

    private void onAutoSaveCurrentLesson(boolean autosave) {
        //TODO: Reimplement
        showInformationMessage("Autosave - Saving lesson");
        String name = "Default name ";
        CreateXmlFactory.generate(_slides, name);
    }

    private void onSaveCurrentLesson(boolean autosave) {
        //TODO: Refactor
        if (!autosave) {
            if (_slides.size() == 0) {
                showInformationMessage("Cannot save empty lesson !");
                return;
            }

            String lessonName = showInputMessage("Insert Lesson Name:");

            if (lessonName != null) {
                CreateXmlFactory.generate(_slides, lessonName);
                showInformationMessage("Lesson '" + lessonName + "' was saved !");
            }
            //TODO: implement a check that lesson doesn't exists or overwrite the previous one
            return;
        }
        if (SettingScreen.a == 1) {
            int delay = 30000; //milliseconds
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    onAutoSaveCurrentLesson(false);
                }
            };
            new Timer(delay, taskPerformer).start();
        }
    }

    private void addNewSlide(SlideType type) {
        if (currentSlideIndex != _slidesButtons.size() - 1) {
            //TODO: implement insert in the middle

            showErrorMessage("insert is supported only for the last slide");
            return;
        }

        if (currentSlideIndex >= 0) {
            saveCurrentSlide();
        }

        currentSlideManager.clearContent();

        //TODO: maybe add a picture to the button for v3.0
        currentSlideIndex++;

        IndexedButton newSlideButton = new IndexedButton(currentSlideIndex, "In " + currentSlideIndex);
        newSlideButton.addActionListener(e -> {
            IndexedButton indexedButton = (IndexedButton) e.getSource();
            onSlideSelected(indexedButton.getIndex());
        });
        Dimension buttonSize = new Dimension(SLIDE_BUTTON_SIZE, SLIDE_BUTTON_SIZE);
        setElementConstSize(newSlideButton, buttonSize);

        //TODO: maybe change to the dimension of the panel

        AbstractSlide newSlide = createNewSlide(type);

        try {
            if (type == SlideType.Picture) {
                currentSlideManager.loadPictureFromFile(FileResources.noPictureAvailable, Rotation.NO_ROTATION);
            } else if (type == SlideType.Video) {
                currentSlideManager.loadPictureFromFile(FileResources.noVideoAvailable, Rotation.NO_ROTATION);
            } else {
                throw new UnsupportedOperationException("Not supported type" + type.toString());
            }
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage());
        }

        addToContainers(currentSlideIndex, newSlideButton, newSlide);

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        setSquareInsets(10);
        setConstraints(currentSlideIndex, 0, 0, 0);
        lessonSlidesPanel.add(newSlideButton, constraints);
        lessonSlidesPanel.revalidate();
    }

    private AbstractSlide createNewSlide(SlideType type) {
        if (type == SlideType.Picture) {
            return new PictureSlide();
        } else if (type == SlideType.Video) {
            return new VideoSlide();
        } else {
            throw new UnsupportedOperationException("Not supported type" + type.toString());
        }
    }

    private void saveCurrentSlide() {
        AbstractSlide slide = _slides.get(currentSlideIndex);
        currentSlideManager.saveDataToSlide(slide);
    }

    private void addToContainers(int index, IndexedButton button, AbstractSlide slide) {
        _slides.add(index, slide);
        _slidesButtons.add(index, button);
    }

    private void removeIndexFromContainers(int index) {
        _slides.remove(index);
        _slidesButtons.remove(index);
    }

    private void onSlideSelected(int selectedIndex) {
        if (selectedIndex == currentSlideIndex) {
            return;
        }

        selectedSoundId = null;
        saveCurrentSlide();

        currentSlideManager.clearContent();
        currentSlideIndex = selectedIndex;

        loadSlide(currentSlideIndex);
    }

    private void loadSlide(Integer index) {
        AbstractSlide slide = _slides.get(index);
        if (slide instanceof PictureSlide) {
            loadPictureSlide((PictureSlide) slide);
        } else if (slide instanceof VideoSlide) {
            loadVideoSlide((VideoSlide) slide);
        } else {
            showErrorMessage("slide is not supported type" + slide.getClass().getTypeName());
        }
    }

    private void loadVideoSlide(VideoSlide slide) {

    }

    private void onDeleteSoundRegion() {
        if (selectedSoundId == null) {
            showErrorMessage("No Sound Region Selected");
        } else {
            currentSlideManager.deleteSoundRegion(selectedSoundId);
            selectedSoundId = null;
        }
    }

    //region Set Buttons

    private void setMenuPanelButtons() {
        setMainMenuButton();
        setSaveLessonButton();
        setSendFeedbackButton();
        setReportBugButton();
        setHelpButton();
    }

    private void setHelpButton() {
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(e -> showInformationMessage("Help Not supported yet"));

        //TODO: Implement a help screen
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        setConstraints(4, 0, 1, 1);
        screenMenuPanel.add(helpButton, constraints);
    }

    private void setCommandsPanelButtons() {
        setChoosePictureButton();
        setCreateNewSlideButton();
        setDeleteCurrentSlideButton();

        //TODO: maybe switch to the panel of sounds
        setAddSoundRegionButton();
        setAddRemoveSoundButton();
        setRotateImageButton();
    }

    private void setRotateImageButton() {
        JButton rotateImage = new JButton("Rotate Slide");
        rotateImage.addActionListener(e -> onRotateImage());
        setConstraints(0, 6, 1, 1);
        commandsPanel.add(rotateImage, constraints);
    }

    private void onRotateImage() {
        if (currentSlideIndex < 0) {
            showErrorMessage(MessageErrors.NO_SLIDE_TO_EXECUTE_ON);
            return;
        }
        AbstractSlide slide = _slides.get(currentSlideIndex);
        if (!(slide instanceof PictureSlide)) {
            showErrorMessage(MessageErrors.NOT_SUPPORTED_FOR_SLIDE);
            return;
        }
        File pictureFile = ((PictureSlide) slide).getPictureFile();

        if (pictureFile == null) { // No picture was selected
            return;
        }
        slide.rotateSlide();
        Rotation pictureRotation = slide.getRotation();

        try {
            currentSlideManager.loadPictureFromFile(pictureFile, pictureRotation);
        } catch (Exception ex) {
            showErrorMessage("Error during rotating of the picture " + ex.getMessage());
        }
    }

    private void setAddRemoveSoundButton() {
        JButton removeSoundButton = new JButton("Remove Sound");
        removeSoundButton.addActionListener(e -> onDeleteSoundRegion());
        setConstraints(0, 5, 1, 1);
        commandsPanel.add(removeSoundButton, constraints);
    }

    //TODO: maybe implement auto save feature
    private void setSaveLessonButton() {
        JButton saveButton = new JButton("Save Lesson");
        saveButton.addActionListener(e -> onSaveCurrentLesson(false));

        setConstraints(1, 0, 1, 1);
        screenMenuPanel.add(saveButton, constraints);
    }

    private void setDeleteCurrentSlideButton() {
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        setConstraints(0, 1, 1, 1);
        JButton deleteButton = new JButton("Delete Slide");

        deleteButton.addActionListener(e -> deleteSlide());
        commandsPanel.add(deleteButton, constraints);
    }

    private void setChoosePictureButton() {
        JButton chooseContentFile = new JButton("Select Slide's Content");
        chooseContentFile.addActionListener(e -> chooseFile());
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        setConstraints(0, 2, 1, 1);
        commandsPanel.add(chooseContentFile, constraints);
    }

    private void setCreateNewSlideButton() {
        JButton createButton = ComponentsFactory.createBasicButton("Add New Slide");

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem pictureSlide = new JMenuItem("Picture Slide");
        pictureSlide.addActionListener(e -> addNewSlide(SlideType.Picture));

        JMenuItem videoSlide = new JMenuItem("Video Slide");
        videoSlide.addActionListener(e -> addNewSlide(SlideType.Video));

        popupMenu.add(pictureSlide);
        popupMenu.add(videoSlide);

        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        setConstraints(0, 0, 1, 1);

        commandsPanel.add(createButton, constraints);
    }

    private void setMainMenuButton() {
        JButton backButton = new JButton("Main menu");
        backButton.addActionListener(e -> {
            Screens.CreateLessonScreen.setVisible(false);
            Screens.WelcomeScreen.setVisible(true);
        });
        setConstraints(0, 0, 1, 1);
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        screenMenuPanel.add(backButton, constraints);
    }


    private void setSendFeedbackButton() {
        JButton sendFeedbackButton = new JButton("Send Feedback");
        sendFeedbackButton.addActionListener(e -> sendMail("Send%20Feedback"));

        setConstraints(2, 0, 1, 1);
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        screenMenuPanel.add(sendFeedbackButton, constraints);
    }


    private void setReportBugButton() {
        JButton reportBugButton = new JButton("Report Bug");
        reportBugButton.addActionListener(e -> sendMail("Report%20Bug"));

        setConstraints(3, 0, 1, 1);
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        screenMenuPanel.add(reportBugButton, constraints);
    }

    //endregion

    private void sendMail(String subject) {
        if (!Desktop.isDesktopSupported()) {
            showErrorMessage(CANNOT_SEND_MAIL_ERROR + "\nupgrade to a newer Java version");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI(MAIL_TO_HEADER + subject);
            desktop.mail(uri);
        } catch (Exception e) {
            showErrorMessage(CANNOT_SEND_MAIL_ERROR + "\n" + e.getMessage());
        }
    }
}
