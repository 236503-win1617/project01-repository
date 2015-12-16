package screens;

import AdditionalClasses.IndexedButton;
import AdditionalClasses.SoundElement;
import Factories.ComponentsFactory;
import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class CreateLessonScreen extends AbstractApplicationScreen
{
    private static final String DEFAULT_NO_SLIDE_ERROR = "No slide was created !";

    //region Panels

    private JPanel currentSlidePanel;
    private JPanel soundsPanel;
    private JPanel lessonSlidesPanel;
    private JPanel commandsPanel;
    private JPanel screenMenuPanel;

    //endregion

    private boolean _lessonSaved;

    private ArrayList<IndexedButton> _slidesButtons;
    private ArrayList<AbstractSlide> _slides;

    private Integer currentSlideIndex = 0;
    private JScrollPane _scrollPane;

    public CreateLessonScreen()
    {
        super();

        _slides = new ArrayList<>();
        _slidesButtons = new ArrayList<>();

        setButtons();
    }

    //TODO: maybe implement auto save feature
    private void setSaveLessonButton()
    {
        JButton saveButton = new JButton("Save Lesson");
        saveButton.addActionListener(e -> onSaveCurrentLesson());

        setConstraints(2, 1, 1, 1);
        screenMenuPanel.add(saveButton, _constraints);
    }

    private void setButtons()
    {
        //TODO: maybe provide preview mode of the created lesson
        //TODO: break to each panel buttons

        setChoosePictureButton();
        setMainMenuButton();
        setCreateNewSlideButton();
        setSaveLessonButton();
        setDeleteCurrentSlideButton();
        setAddSoundRegionButton();
    }

    private void setAddSoundRegionButton()
    {
        JButton addSoundButton = new JButton("Add Sound");
        addSoundButton.addActionListener(e -> addSoundToSlide());
        setSquareInsests(DEFAULT_BUTTONS_INSESTE);
        setConstraints(0, 3, 1, 1);
        commandsPanel.add(addSoundButton, _constraints);
    }

    private void addSoundToSlide()
    {
        if (currentSlideIndex == 0)
        {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        Screens.SoundAreaScreen.setVisible(true);
        setVisible(false);
    }

    public void addSoundElementToCurrentSlide(SoundElement element)
    {

    }

    private void setChoosePictureButton()
    {
        JButton choosePicture = new JButton("Choose Picture");
        choosePicture.addActionListener(e -> choosePicture());
        setSquareInsests(DEFAULT_BUTTONS_INSESTE);
        setConstraints(0, 2, 1, 1);
        commandsPanel.add(choosePicture, _constraints);
    }

    private void choosePicture()
    {
        if (currentSlideIndex == 0)
        {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            File picture = chooser.getSelectedFile();

            ImageIcon image = new ImageIcon(picture.getAbsolutePath());
            JLabel label = new JLabel("", image, JLabel.CENTER);

            setConstraints(0, 0, 1, 1);
            currentSlidePanel.add(label, _constraints);
            currentSlidePanel.revalidate();
        }
    }

    private void addSoundAreaButton()
    {
        // TODO: implement
    }

    private void setDeleteCurrentSlideButton()
    {
        setSquareInsests(DEFAULT_BUTTONS_INSESTE);
        setConstraints(0, 1, 1, 1);
        JButton deleteButton = new JButton("Delete Slide");

        deleteButton.addActionListener(e -> deleteSlide());
        commandsPanel.add(deleteButton, _constraints);
    }

    private void deleteSlide()
    {
        if (currentSlideIndex == -1)
        {
            showErrorMessage(DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        int result = showYesNoMessage("Are you sure you want to delete ?");

        if (result == JOptionPane.YES_OPTION)
        {
            currentSlideIndex--;
            JButton button = _slidesButtons.get(currentSlideIndex);
            lessonSlidesPanel.remove(button);
            removeIndexFromContainers(currentSlideIndex);

            lessonSlidesPanel.revalidate();
            lessonSlidesPanel.repaint();
        }
    }

    @Override
    protected void setScreenPanels()
    {
        currentSlidePanel = new JPanel();
        soundsPanel = new JPanel();
        lessonSlidesPanel = new JPanel();
        screenMenuPanel = new JPanel();
        commandsPanel = new JPanel();
    }

    @Override
    protected void setPanelsContent()
    {
        //TODO: maybe switch to a fixed size according to the tablet size

        setPanel(currentSlidePanel, Color.black, (SCREEN_WIDTH * 4) / 6, (SCREEN_HEIGHT * 4) / 6, BorderLayout.CENTER);
        setPanel(soundsPanel, Color.YELLOW, SCREEN_WIDTH / 6, (SCREEN_HEIGHT * 4) / 6, BorderLayout.WEST);
        setPanel(screenMenuPanel, Color.RED, SCREEN_WIDTH, SCREEN_HEIGHT / 6, BorderLayout.NORTH);
        setPanel(commandsPanel, Color.GREEN, SCREEN_WIDTH / 6, (SCREEN_HEIGHT * 4) / 6, BorderLayout.EAST);
        //   setPanel(lessonSlidesPanel, Color.blue, SCREEN_WIDTH, SCREEN_HEIGHT / 6, BorderLayout.SOUTH);

        lessonSlidesPanel.setBackground(Color.BLUE);
        _scrollPane = new JScrollPane(lessonSlidesPanel);
        _scrollPane.setBackground(Color.BLUE);
        _scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        _scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        Dimension dimensions = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT / 6);
        _scrollPane.setPreferredSize(dimensions);
        add(_scrollPane, BorderLayout.SOUTH);
    }

    private void setPanel(JPanel panel, Color color, int width, int height, String location)
    {
        panel.setBackground(color); //TODO: Delete this line when everything is working
        panel.setLayout(new GridBagLayout());
        Dimension dimensions = new Dimension(width, height);
        panel.setPreferredSize(dimensions);
        add(panel, location);
    }

    private void onSaveCurrentLesson()
    {
        showInformationMessage("Saving lesson");

        //TODO: create the xml file with the content

        _lessonSaved = true;
    }

    //region Set Buttons

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

        commandsPanel.add(createButton, _constraints);
    }

    private void addNewAnimationSlide()
    {
        //TODO: implement
    }

    private void addNewPictureSlide()
    {
        //TODO: maybe add a picture to the button for v3.0
        currentSlideIndex++;

        savePreviousSlide();

        IndexedButton newSlideButton = new IndexedButton(currentSlideIndex, "In " + currentSlideIndex);
        newSlideButton.addActionListener(e -> {
            IndexedButton pressed = (IndexedButton) e.getSource();
            onSlideSelected(pressed.getIndex());
        });

        //TODO: maybe change to the dimension of the panel
        Dimension buttonSize = new Dimension(70, 70);
        newSlideButton.setPreferredSize(buttonSize);
        newSlideButton.setMaximumSize(buttonSize);
        newSlideButton.setMinimumSize(buttonSize);

        PictureSlide newSlide = new PictureSlide();

        addToContainers(currentSlideIndex - 1, newSlideButton, newSlide);

        _constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        setConstraints(currentSlideIndex - 1, 0, 0, 0);
        lessonSlidesPanel.add(newSlideButton, _constraints);
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

    private void savePreviousSlide()
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
        screenMenuPanel.add(backButton, _constraints);
    }

    //endregion
}
