package screens;

import AdditionalClasses.IndexedButton;
import AdditionalClasses.SoundElement;
import AdditionalClasses.SoundsPanel;
import AdditionalClasses.UniqueTextArea;
import Factories.ComponentsFactory;
import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;
import javafx.scene.effect.ColorInput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class CreateLessonScreen extends AbstractApplicationScreen
{
    private static final String DEFAULT_NO_SLIDE_ERROR = "No slide was created !";
    private static final String UNEXPECTED_ERROR = "Something very bad has happened";

    //region Panels

    private JPanel currentSlidePanel;
    private SoundsPanel soundsPanel;
    private JPanel lessonSlidesPanel;
    private JPanel commandsPanel;
    private JPanel screenMenuPanel;

    //endregion

    private boolean _lessonSaved;

    private Map<UUID, UniqueTextArea> slideSoundAreas = new HashMap<>();
    private ArrayList<IndexedButton> _slidesButtons = new ArrayList<>();
    private ArrayList<AbstractSlide> _slides = new ArrayList<>();

    private Integer currentSlideIndex = -1;
    private UUID selectedSound;

    public CreateLessonScreen()
    {
        super();

        setMenuPanelButtons();
        setCommandsPanelButtons();
    }

    private void setAddSoundRegionButton()
    {
        JButton addSoundButton = new JButton("Add Sound");
        addSoundButton.addActionListener(e -> addSoundToSlide());
        setSquareInsests(DEFAULT_BUTTONS_INSESTE);
        setConstraints(0, 3, 1, 1);
        commandsPanel.add(addSoundButton, constraints);
    }

    private void addSoundToSlide()
    {
        if (currentSlideIndex < 0)
        {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        //TODO: return the original when done developing the scroling of elements
//        Screens.SoundAreaScreen.setVisible(true);
//        setVisible(false);

        SoundElement tmp = new SoundElement("asd", 1, 1, 1, 1);
        addSoundElementToCurrentSlide(tmp);
    }

    public void addSoundElementToCurrentSlide(SoundElement element)
    {
        if (element == null)
        {
            showErrorMessage(UNEXPECTED_ERROR);
            return;
        }

        UUID areaID = UUID.randomUUID();
        UniqueTextArea soundArea = new UniqueTextArea(areaID);
        soundArea.setForeground(Color.green);
        //TODO: create text from sound region

        soundArea.setText("Sound Area\n" + slideSoundAreas.size());
        soundArea.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                UniqueTextArea textArea = (UniqueTextArea) e.getSource();
                setNewSelectedSoundRegion(textArea.getId());
            }
        });

        setConstraints(0, -1 * slideSoundAreas.size(), 1, 1);

        slideSoundAreas.put(areaID, soundArea);

        soundsPanel.add(soundArea, constraints);
        soundsPanel.revalidate();
    }

    private void setNewSelectedSoundRegion(UUID newId)
    {
        if (selectedSound != null)
        {
            //TODO: restore the previous region to default
        }

        selectedSound = newId;
    }

    private void choosePicture()
    {
        if (currentSlideIndex < 0)
        {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File picture = chooser.getSelectedFile();
            String picturePath = picture.getAbsolutePath();

            PictureSlide currentSlide = (PictureSlide) _slides.get(currentSlideIndex);
            currentSlide.setPicturePath(picturePath);

            ImageIcon image = new ImageIcon(picturePath);
            JLabel label = new JLabel("", image, JLabel.CENTER);

            setConstraints(0, 0, 1, 1);
            currentSlidePanel.add(label, constraints);
            currentSlidePanel.revalidate();
        }
    }

    private void deleteSlide()
    {
        if (currentSlideIndex != _slidesButtons.size() - 1)
        {
            //TODO: implement delete in the middle

            showErrorMessage("Delete is supported only for the last slide");
            return;
        }

        if (currentSlideIndex == -1)
        {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        int result = showYesNoMessage("Are you sure you want to delete ?");

        if (result == JOptionPane.YES_OPTION)
        {
            JButton button = _slidesButtons.get(currentSlideIndex);
            lessonSlidesPanel.remove(button);

            removeIndexFromContainers(currentSlideIndex);

            lessonSlidesPanel.revalidate();
            lessonSlidesPanel.repaint();

            currentSlideIndex--;
        }
    }

    @Override
    protected void setScreenPanels()
    {
        currentSlidePanel = new JPanel();
        soundsPanel = new SoundsPanel();
        lessonSlidesPanel = new JPanel();
        screenMenuPanel = new JPanel();
        commandsPanel = new JPanel();
    }

    @Override
    protected void setPanelsContent()
    {
        //TODO: maybe switch to a fixed size according to the tablet size

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

    private void setPanel(JPanel panel, Color color, int width, int height, String location)
    {
        panel.setBackground(color); //TODO: Delete this line when everything is working
        panel.setLayout(new GridBagLayout());
        Dimension dimensions = new Dimension(width, height);
        panel.setPreferredSize(dimensions);
        add(panel, location);
    }

    private void onSaveCurrentLesson(boolean autosave)
    {
        if (!autosave)
        {
            showInformationMessage("Saving lesson");
        }
        //TODO: create the xml file with the content

        _lessonSaved = true;
    }

    private void addNewPictureSlide()
    {
        if (currentSlideIndex != _slidesButtons.size() - 1)
        {
            //TODO: implement insert in the middle

            showErrorMessage("insert is supported only for the last slide");
            return;
        }

        List<SoundElement> elements = soundsPanel.getSoundElements();
        if (elements.size() != 0)
        { //which means that the previous slide was a picture slide with sounds
            PictureSlide pictureSlide = (PictureSlide) _slides.get(currentSlideIndex);
            pictureSlide.setSoundElements(elements);
        }

        soundsPanel.clearContent();

        //TODO: move the common code for video slide and picture slide

        //TODO: maybe add a picture to the button for v3.0
        currentSlideIndex++;

        IndexedButton newSlideButton = new IndexedButton(currentSlideIndex, "In " + currentSlideIndex);
        newSlideButton.addActionListener(e -> {
            IndexedButton pressed = (IndexedButton) e.getSource();
            onSlideSelected(pressed.getIndex());
        });

        //TODO: maybe change to the dimension of the panel
        Dimension buttonSize = new Dimension(80, 80);
        newSlideButton.setPreferredSize(buttonSize);
        newSlideButton.setMaximumSize(buttonSize);
        newSlideButton.setMinimumSize(buttonSize);

        PictureSlide newPictureSlide = new PictureSlide();

        addToContainers(currentSlideIndex, newSlideButton, newPictureSlide);

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        setSquareInsests(10);
        setConstraints(currentSlideIndex, 0, 0, 0);
        lessonSlidesPanel.add(newSlideButton, constraints);
        lessonSlidesPanel.revalidate();
    }

    private void addToContainers(int index, IndexedButton button, AbstractSlide slide)
    {
        _slides.add(index, slide);
        _slidesButtons.add(index, button);
    }

    private void removeIndexFromContainers(int index)
    {
        _slides.remove(index);
        _slidesButtons.remove(index);
    }

    private void onSlideSelected(int selectedIndex)
    {
        currentSlideIndex = selectedIndex;

        showSelectedSlide(currentSlideIndex);
        showInformationMessage("Selected index : " + selectedIndex);
    }

    private void showSelectedSlide(Integer currentSlideIndex)
    {
        // TODO: Implement
    }

    private void onDeleteSoundRegion()
    {
        if (selectedSound == null)
        {
            showErrorMessage("No Sound Region Selected");
            return;
        }

        UniqueTextArea areaToDelete = slideSoundAreas.remove(selectedSound);

        soundsPanel.remove(areaToDelete);
        soundsPanel.revalidate();
        soundsPanel.repaint();

        selectedSound = null;
    }

    //region Set Buttons

    private void setMenuPanelButtons()
    {
        setMainMenuButton();
        setSaveLessonButton();
    }

    private void setCommandsPanelButtons()
    {
        setChoosePictureButton();
        setCreateNewSlideButton();
        setDeleteCurrentSlideButton();

        //TODO: maybe switch to the panel of sounds
        setAddSoundRegionButton();
        setAddRemoveSoundButton();
    }

    private void setAddRemoveSoundButton()
    {
        JButton removeSoundButton = new JButton("Remove Sound");
        removeSoundButton.addActionListener(e -> onDeleteSoundRegion());
        setConstraints(0, 5, 1, 1);
        commandsPanel.add(removeSoundButton, constraints);
    }

    //TODO: maybe implement auto save feature
    private void setSaveLessonButton()
    {
        JButton saveButton = new JButton("Save Lesson");
        saveButton.addActionListener(e -> onSaveCurrentLesson(false));

        setConstraints(2, 1, 1, 1);
        screenMenuPanel.add(saveButton, constraints);
    }

    private void setDeleteCurrentSlideButton()
    {
        setSquareInsests(DEFAULT_BUTTONS_INSESTE);
        setConstraints(0, 1, 1, 1);
        JButton deleteButton = new JButton("Delete Slide");

        deleteButton.addActionListener(e -> deleteSlide());
        commandsPanel.add(deleteButton, constraints);
    }

    private void setChoosePictureButton()
    {
        JButton choosePicture = new JButton("Choose Picture");
        choosePicture.addActionListener(e -> choosePicture());
        setSquareInsests(DEFAULT_BUTTONS_INSESTE);
        setConstraints(0, 2, 1, 1);
        commandsPanel.add(choosePicture, constraints);
    }

    private void setCreateNewSlideButton()
    {
        JButton createButton = ComponentsFactory.createDefaultButton("Add New Slide", 10, 10);

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem pictureSlide = new JMenuItem("Picture Slide");
        pictureSlide.addActionListener(e -> addNewPictureSlide());

        JMenuItem animationSlide = new JMenuItem("Animation Slide");
        animationSlide.addActionListener(e -> addNewAnimationSlide());

        popupMenu.add(pictureSlide);
        popupMenu.add(animationSlide);

        popupMenu.addSeparator();

        createButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //TODO: return the original
                addNewPictureSlide();
                //popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        setConstraints(0, 0, 1, 1);

        commandsPanel.add(createButton, constraints);
    }

    private void addNewAnimationSlide()
    {
        //TODO: implement
    }

    private void setMainMenuButton()
    {
        JButton backButton = ComponentsFactory.createDefaultButton("Main menu", 40, 40);
        backButton.addActionListener(e -> {
            Screens.CreateLessonScreen.setVisible(false);
            Screens.WelcomeScreen.setVisible(true);
        });
        setConstraints(1, 1, 1, 1);
        setSquareInsests(DEFAULT_BUTTONS_INSESTE);
        screenMenuPanel.add(backButton, constraints);
    }

    //endregion
}
