package screens;

import AdditionalClasses.IndexedButton;
import AdditionalClasses.SoundElement;
import AdditionalClasses.UniqueTextPane;
import Factories.ComponentsFactory;
import Factories.DOM4JCreateXMLDemo;
import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    //TODO: find a way to add pictures to jar
    private static final File noPictureAvailable = new File(".\\desktopApp\\resources\\no_picture.jpg");

    //region Panels

    private JPanel currentSlidePanel;
    private JPanel soundsPanel;
    private JPanel lessonSlidesPanel;
    private JPanel commandsPanel;
    private JPanel screenMenuPanel;

    //endregion

    private boolean _lessonSaved;

    private Map<UUID, UniqueTextPane> slideSoundAreas = new HashMap<>();
    private Map<UUID, SoundElement> slideSoundElements = new HashMap<>();

    private ArrayList<IndexedButton> _slidesButtons = new ArrayList<>();
    private ArrayList<AbstractSlide> _slides = new ArrayList<>();

    private Integer currentSlideIndex = -1;
    private UUID selectedSound;

    public CreateLessonScreen() {
        super();

        setMenuPanelButtons();
        setCommandsPanelButtons();
    }

    private void setAddSoundRegionButton() {
        JButton addSoundButton = new JButton("Add Sound");
        addSoundButton.addActionListener(e -> addSoundToSlide());
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        setConstraints(0, 3, 1, 1);
        commandsPanel.add(addSoundButton, constraints);
    }

    private void addSoundToSlide() {
        if (currentSlideIndex < 0) {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        Screens.SoundAreaScreen.setVisible(true);
        setVisible(false);

//        debug purpose
//        SoundElement tmp = new SoundElement(null, 1, 1, 1, 1);
//        addNewSoundElementToCurrentSlide(tmp);
    }

    public void addNewSoundElementToCurrentSlide(SoundElement element) {
        if (element == null) {
            showErrorMessage(UNEXPECTED_ERROR);
            return;
        }

        //TODO: make a nicer text display

        UUID uuid = UUID.randomUUID();

        UniqueTextPane soundTextPane = new UniqueTextPane(uuid);
        soundTextPane.setForeground(Color.red);

        soundTextPane.setFont(new Font("Ariel", Font.BOLD, 16));
        soundTextPane.setText(element.toString());
        soundTextPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                UniqueTextPane textArea = (UniqueTextPane) e.getSource();
                selectedSound = textArea.getId();
            }
        });

        Dimension textAreaDimension = getSoundAreaDimension();
        setElementConstSize(soundTextPane, textAreaDimension);

        slideSoundAreas.put(uuid, soundTextPane);
        slideSoundElements.put(uuid, element);

        //TODO: add the sound to the current slide

        constraints.anchor = GridBagConstraints.PAGE_START;
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        setConstraints(0, slideSoundAreas.size(), 0, 0);

        soundsPanel.add(soundTextPane, constraints);

        soundsPanel.revalidate();
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

    private void choosePicture() {
        if (currentSlideIndex < 0) {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File pictureFile = chooser.getSelectedFile();

            PictureSlide currentSlide = (PictureSlide) _slides.get(currentSlideIndex);
            currentSlide.setPictureFile(pictureFile);

            loadPicture(pictureFile.getAbsolutePath());
        }
    }

    private void loadPicture(String path) {
        currentSlidePanel.removeAll();

        ImageIcon image = new ImageIcon(path);
        JLabel label = new JLabel("", image, JLabel.CENTER);

        setConstraints(0, 0, 1, 1);

        currentSlidePanel.add(label, constraints);
        currentSlidePanel.revalidate();
        currentSlidePanel.repaint();
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

        int result = showYesNoMessage("Are you sure you want to delete ?");

        if (result == JOptionPane.YES_OPTION) {
            JButton button = _slidesButtons.get(currentSlideIndex);
            lessonSlidesPanel.remove(button);

            removeIndexFromContainers(currentSlideIndex);

            lessonSlidesPanel.revalidate();
            lessonSlidesPanel.repaint();

            currentSlideIndex--;
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
        setPanel(currentSlidePanel, Color.black, (SCREEN_WIDTH * 4) / 6, (SCREEN_HEIGHT * 4) / 6, BorderLayout.CENTER);
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
        panel.setBackground(color); //TODO: Delete this line when everything is working
        panel.setLayout(new GridBagLayout());
        Dimension dimensions = new Dimension(width, height);
        panel.setPreferredSize(dimensions);
        add(panel, location);
    }

    private void onSaveCurrentLesson(boolean autosave) {
        if (!autosave) {
            String name = "Default name ";
            DOM4JCreateXMLDemo.generate(_slides, name);
        }

        _lessonSaved = true;
    }

    private void addNewPictureSlide() {
        if (currentSlideIndex != _slidesButtons.size() - 1) {
            //TODO: implement insert in the middle

            showErrorMessage("insert is supported only for the last slide");
            return;
        }

        if (currentSlideIndex >= 0) {
            saveCurrentSlide();

            clearSoundElementsContainers();
            clearPanel(soundsPanel);
            clearPanel(currentSlidePanel);
        }

        //TODO: move the common code for video slide and picture slide

        //TODO: maybe add a picture to the button for v3.0
        currentSlideIndex++;

        IndexedButton newSlideButton = new IndexedButton(currentSlideIndex, "In " + currentSlideIndex);
        newSlideButton.addActionListener(e -> {
            IndexedButton indexedButton = (IndexedButton) e.getSource();
            onSlideSelected(indexedButton.getIndex());
        });

        loadPicture(noPictureAvailable.getAbsolutePath());

        //TODO: maybe change to the dimension of the panel
        Dimension buttonSize = new Dimension(SLIDE_BUTTON_SIZE, SLIDE_BUTTON_SIZE);
        setElementConstSize(newSlideButton, buttonSize);

        PictureSlide newPictureSlide = new PictureSlide();

        addToContainers(currentSlideIndex, newSlideButton, newPictureSlide);

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        setSquareInsets(10);
        setConstraints(currentSlideIndex, 0, 0, 0);
        lessonSlidesPanel.add(newSlideButton, constraints);
        lessonSlidesPanel.revalidate();
    }

    private void saveCurrentSlide() {
        AbstractSlide slide = _slides.get(currentSlideIndex);
        if (slide instanceof PictureSlide) {
            PictureSlide pictureSlide = (PictureSlide) slide;
            if (pictureSlide.getPictureFile() == null) {
                // showErrorMessage("Saving without picture");

                //TODO: let the user create slide without any pictures
            }

            SoundElement[] soundElements = (slideSoundElements.values().toArray(new SoundElement[0]));
            pictureSlide.setSoundElements(soundElements);
        } else {
            showErrorMessage("Slide is not supported type" + slide.getClass());
        }
        //TODO: implement the other types
    }

    private void clearPanel(JPanel panel) {
        panel.removeAll();
        panel.repaint();
    }

    private void clearSoundElementsContainers() {
        slideSoundElements.clear();
        slideSoundAreas.clear();
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

        selectedSound = null;

        saveCurrentSlide();
        clearSoundElementsContainers();
        clearPanel(soundsPanel);
        clearPanel(currentSlidePanel);

        if (slideSoundAreas.size() > 0) {
            clearPanel(soundsPanel);
        }

        clearPanel(currentSlidePanel);

        currentSlideIndex = selectedIndex;

        AbstractSlide slide = _slides.get(selectedIndex);

        if (slide instanceof PictureSlide) {
            loadPictureSlide((PictureSlide) slide);
        } else {
            showErrorMessage("slide is not supported type" + slide.getClass().getTypeName());
        }
    }

    private void loadPictureSlide(PictureSlide pictureSlide) {
        File picture = pictureSlide.getPictureFile();
        if (picture == null) {
            picture = noPictureAvailable;
        }

        loadPicture(picture.getAbsolutePath());

        for (SoundElement element : pictureSlide.getSoundElements()) {
            addNewSoundElementToCurrentSlide(element);
        }
    }

    private void onDeleteSoundRegion() {
        if (selectedSound == null) {
            showErrorMessage("No Sound Region Selected");
            return;
        }

        UniqueTextPane areaToDelete = slideSoundAreas.remove(selectedSound);
        slideSoundElements.remove(areaToDelete.getId());

        soundsPanel.remove(areaToDelete);
        soundsPanel.revalidate();
        soundsPanel.repaint();

        selectedSound = null;
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
        JButton choosePicture = new JButton("Choose Picture");
        choosePicture.addActionListener(e -> choosePicture());
        setSquareInsets(DEFAULT_BUTTONS_INSETS);
        setConstraints(0, 2, 1, 1);
        commandsPanel.add(choosePicture, constraints);
    }

    private void setCreateNewSlideButton() {
        JButton createButton = ComponentsFactory.createBasicButton("Add New Slide");

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem pictureSlide = new JMenuItem("Picture Slide");
        pictureSlide.addActionListener(e -> addNewPictureSlide());

        JMenuItem animationSlide = new JMenuItem("Animation Slide");
        animationSlide.addActionListener(e -> addNewAnimationSlide());

        popupMenu.add(pictureSlide);
        popupMenu.add(animationSlide);

        popupMenu.addSeparator();

        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO: return the original
                addNewPictureSlide();
                //popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        setConstraints(0, 0, 1, 1);

        commandsPanel.add(createButton, constraints);
    }

    private void addNewAnimationSlide() {
        //TODO: implement
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
