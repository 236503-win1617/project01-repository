package screens;

import AdditionalClasses.IndexedButton;
import AdditionalClasses.SoundElement;
import Factories.*;
import Resources.MessageErrors;
import SlideManagers.AbstractSlideManager;
import SlideManagers.PictureSlideManager;
import SlideManagers.VideoSlideManager;
import SlideManagers.GameSlideManager;
import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;
import SlideObjects.SlideType;
import SlideObjects.VideoSlide;
import SlideObjects.GameSlide;

import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Evgeniy on 11/21/2015.
 */
public class CreateLessonScreen extends AbstractEmptyScreen {
    private static final String MAIL_TO_HEADER = "MailTo:evgenhvost@gmail.com?Subject=";

    private static final int SLIDE_BUTTON_SIZE = 80;

    private HashMap<SlideType, AbstractSlideManager> slideTypeToManager = new HashMap<>();
    private AbstractSlideManager currentSlideManager;

    //region Panels

    private JPanel currentSlidePanel = new JPanel();
    private JPanel soundsPanel = new JPanel();
    private JPanel lessonSlidesPanel = new JPanel();
    private JPanel commandsPanel = new JPanel();
    private JPanel screenMenuPanel = new JPanel();

    //endregion

    private ArrayList<IndexedButton> _slidesButtons = new ArrayList<>();
    private ArrayList<AbstractSlide> _slides = new ArrayList<>();

    private int currentSlideIndex = -1;
    private String lessonName = "";
    public CreateLessonScreen() {
        super();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                boolean dataSaved = false;
                onExitApp(dataSaved);
            }
        });

        setDynamicBounds();
        setPanelsLocations();

        setMenuPanelButtons();
        setCommandsPanelButtons();

        slideTypeToManager.put(SlideType.Picture, new PictureSlideManager(currentSlidePanel, commandsPanel, soundsPanel));
        slideTypeToManager.put(SlideType.Video, new VideoSlideManager(currentSlidePanel, commandsPanel));
        slideTypeToManager.put(SlideType.Game, new GameSlideManager(currentSlidePanel, commandsPanel));
    }

    public void loadExistingLesson(Lesson toLoad,String lessonName){
        for(Slide s: toLoad.slides){

            if(s instanceof Factories.PictureSlide){
                PictureSlide picSlide = new PictureSlide();

                addNewSlide(picSlide);
                try {
                    SlideType slideType = picSlide.getType();
                    currentSlideManager = slideTypeToManager.get(slideType);
                    String toLoadPath = s.getPath();

                    currentSlideManager.loadPictureFile(new File(s.getPath()));
                    for(DynamicButton b: s.getDynamicButtons()){
                        SoundElement soundElement = new SoundElement(new File(b.getPath()),b.getStartX(), b.getStartY(), b.getHeight(), b.getWidth());
                        currentSlideManager.addNewSoundElement(soundElement);
                    }
//                    s.getDynamicButtons();



                    //Screens.CreateLessonScreen.addNewSoundElement(soundElement);
                    this.lessonName = lessonName;
                } catch (Exception ex) {
                    showErrorMessage(MessageErrors.UNEXPECTED_ERROR + ex.getMessage());
                }
            }
        }

    }
//
//    private void loadGameSlide(GameSlide gameSlide) {
//        GameSlide.GameType type = gameSlide.getGameType();
//        switch(type){
//            case Animals:
//                currentSlideManager.loadPicture(".\\resources\\animals.jpg");
//                break;
//            case Colors:
//                currentSlideManager.loadPicture(".\\resources\\colors.png");
//                break;
//            case Numbers:
//                currentSlideManager.loadPicture(".\\resources\\numbers.jpg");
//                break;
//        }
//    }

    //TODO: remove this method when switching to different sound selecting
    //TODO: make a nicer text display
    public void addNewSoundElement(SoundElement soundElement) {
        if (soundElement == null) {
            showErrorMessage(MessageErrors.UNEXPECTED_ERROR);
            return;
        }

        PictureSlideManager manager = (PictureSlideManager) currentSlideManager;
        manager.addNewSoundElement(soundElement);
    }
	
    //TODO: implement delete in the middle
    private void deleteSlide() {
        if (currentSlideIndex != _slidesButtons.size() - 1) {
            showErrorMessage("Delete is supported only for the last slide");
        } else if (currentSlideIndex == -1) {
            showErrorMessage(MessageErrors.DEFAULT_NO_SLIDE_ERROR);
        } else if (showYesNoMessage("Are you sure you want to delete the slide ?") == JOptionPane.YES_OPTION) {
            currentSlideManager.clearContent();

            JButton button = _slidesButtons.get(currentSlideIndex);

            lessonSlidesPanel.remove(button);
            lessonSlidesPanel.repaint();

            _slides.remove(currentSlideIndex);
            _slidesButtons.remove(currentSlideIndex);

            currentSlideIndex--;

            if (currentSlideIndex >= 0) {
                AbstractSlide slide = _slides.get(currentSlideIndex);
                loadSlide(slide);
            }
        }
    }

    //TODO: maybe switch to a fixed size according to the tablet size
    //TODO: switch to gridbag constraints
    private void setPanelsLocations() {
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

    //TODO: Reimplement
    //TODO: add saving options
    //TODO: no saving for default name - auto save only after first successful 'save as'
    private void onAutoSaveCurrentLesson(boolean autosave) {
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

            //String lessonName = showInputMessage("Insert Lesson Name:");

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

    //TODO: implement insert in the middle
    //TODO: add a picture to the button for v4.0
    private void addNewSlide(AbstractSlide newSlide) {
        if (currentSlideIndex != _slidesButtons.size() - 1) {
            showErrorMessage("insert is supported only for the last slide");
            return;
        }

        if (currentSlideIndex > -1) {
            currentSlideManager.saveDataToCurrentSlide();
            currentSlideManager.clearContent();
        }

        currentSlideIndex++;

        IndexedButton newSlideButton = new IndexedButton(currentSlideIndex, "In " + currentSlideIndex);
        newSlideButton.addActionListener(e -> {
            IndexedButton indexedButton = (IndexedButton) e.getSource();
            onSlideSelected(indexedButton.getIndex());
        });
        Dimension buttonSize = new Dimension(SLIDE_BUTTON_SIZE, SLIDE_BUTTON_SIZE);
        ComponentsFactory.setElementConstSize(newSlideButton, buttonSize);

        _slides.add(currentSlideIndex, newSlide);
        _slidesButtons.add(currentSlideIndex, newSlideButton);

        loadSlide(newSlide);

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        setConstraints(currentSlideIndex, 0, 0, 0);
        lessonSlidesPanel.add(newSlideButton, constraints);
        lessonSlidesPanel.revalidate();
    }

    private void onSlideSelected(int selectedIndex) {
        if (selectedIndex == currentSlideIndex) {
            return;
        }

        currentSlideManager.saveDataToCurrentSlide();
        currentSlideManager.clearContent();

        currentSlideIndex = selectedIndex;
        AbstractSlide slide = _slides.get(currentSlideIndex);
        loadSlide(slide);
    }

    //TODO: maybe switch to index loading
    private void loadSlide(AbstractSlide slide) {
        try {
            SlideType slideType = slide.getType();
            currentSlideManager = slideTypeToManager.get(slideType);
            currentSlideManager.loadSlide(slide);
        } catch (Exception ex) {
            showErrorMessage(MessageErrors.UNEXPECTED_ERROR + ex.getMessage());
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

    private void setCommandsPanelButtons() {
        setAddPictureSlideButton();
        setAddVideoSlideButton();
        setAddGameSlideButton();
        setDeleteCurrentSlideButton();
        setRotateSlideButton();
    }

    private void setDeleteCurrentSlideButton() {
        setConstraints(0, 3, 1, 1);
        JButton deleteButton = new JButton("Delete Slide");

        deleteButton.addActionListener(e -> deleteSlide());
        commandsPanel.add(deleteButton, constraints);
    }

    private void setRotateSlideButton() {
        JButton rotateImage = new JButton("Rotate Slide");
        rotateImage.addActionListener(e -> {
            if (currentSlideIndex == -1) {
                showErrorMessage(MessageErrors.NO_SLIDE_TO_EXECUTE_ON);
            } else {
                try {
                    currentSlideManager.onRotateCommand();
                } catch (Exception ex) {
                    showErrorMessage("Error during rotate command " + ex.getMessage());
                }
            }
        });
        setConstraints(0, 4, 1, 1);
        commandsPanel.add(rotateImage, constraints);
    }

    private void setAddPictureSlideButton() {
        JButton addPictureSlide = new JButton("Add Picture Slide");
        addPictureSlide.addActionListener(e -> addNewSlide(new PictureSlide()));
        setConstraints(0, 0, 1, 1);
        commandsPanel.add(addPictureSlide, constraints);
    }

    private void setAddGameSlideButton(){
        JButton addGameSlide = new JButton("Add Game Slide");
        addGameSlide.addActionListener(new ActionListener() {

                                           public void actionPerformed(ActionEvent e) {
                                               String[] gameTypes = { "Animals", "Colors", "Numbers"};
                                               String choice = (String) JOptionPane.showInputDialog(null, "What game do you want?",
                                                       "Choose Type of Game", JOptionPane.QUESTION_MESSAGE, null, // Use
                                                       gameTypes, // Array of choices
                                                       gameTypes[0]); // Initial choice

                                               GameSlide.GameType type = null;
                                               if (choice.equals("Animals")){
                                                   type = GameSlide.GameType.Animals;
                                               } else if (choice.equals("Colors")){
                                                   type = GameSlide.GameType.Colors;
                                               } else if (choice.equals("Numbers")){
                                                   type = GameSlide.GameType.Numbers;
                                               }
                                               GameSlide newGameSlide = new GameSlide(type);
                                               addNewSlide(newGameSlide);
                                           }
                                       });
        setConstraints(0, 1, 1, 1);//What should be here?
        commandsPanel.add(addGameSlide, constraints);
    }

    private void setAddVideoSlideButton() {
        JButton addPictureSlide = new JButton("Add Video Slide");
        addPictureSlide.addActionListener(e -> addNewSlide(new VideoSlide()));
        setConstraints(0, 2, 1, 1);
        commandsPanel.add(addPictureSlide, constraints);
    }

    private void setMainMenuButton() {
        JButton backButton = new JButton("Main menu");
        backButton.addActionListener(e -> {
            Screens.CreateLessonScreen.setVisible(false);
            Screens.WelcomeScreen.setVisible(true);
        });
        setConstraints(0, 0, 1, 1);
        screenMenuPanel.add(backButton, constraints);
    }

    private void setSendFeedbackButton() {
        JButton sendFeedbackButton = new JButton("Send Feedback");
        sendFeedbackButton.addActionListener(e -> sendMail("Send%20Feedback"));

        setConstraints(2, 0, 1, 1);
        screenMenuPanel.add(sendFeedbackButton, constraints);
    }

    //TODO: Implement good saving
    //TODO: maybe implement auto save feature
    private void setSaveLessonButton() {
        JButton saveButton = new JButton("Save Lesson");
        saveButton.addActionListener(e -> onSaveCurrentLesson(false));

        setConstraints(1, 0, 1, 1);
        screenMenuPanel.add(saveButton, constraints);
    }

    //TODO: Implement a help screen
    private void setHelpButton() {
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(e -> showInformationMessage("Help Not supported yet"));
        setConstraints(4, 0, 1, 1);
        screenMenuPanel.add(helpButton, constraints);
    }

    private void setReportBugButton() {
        JButton reportBugButton = new JButton("Report Bug");
        reportBugButton.addActionListener(e -> sendMail("Report%20Bug"));

        setConstraints(3, 0, 1, 1);
        screenMenuPanel.add(reportBugButton, constraints);
    }

    //endregion

    //TODO: Create mail manager class
    private void sendMail(String subject) {
        if (!Desktop.isDesktopSupported()) {
            showErrorMessage(MessageErrors.CANNOT_SEND_MAIL_ERROR + "\nupgrade to a newer Java version");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI(MAIL_TO_HEADER + subject);
            desktop.mail(uri);
        } catch (Exception e) {
            showErrorMessage(MessageErrors.CANNOT_SEND_MAIL_ERROR + "\n" + e.getMessage());
        }
    }

    private void setDynamicBounds() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        SCREEN_WIDTH = (int) screenSize.getWidth() - 100;
        SCREEN_HEIGHT = (int) screenSize.getHeight() - 100;

        if (SCREEN_HEIGHT > 600) {
            setBounds(X_SCREEN_START_FROM, Y_SCREEN_START_FROM, SCREEN_WIDTH, SCREEN_HEIGHT);
        } else {
            //TODO: implement for a small screen
        }
    }


    public void setLessonName(String name){
        this.lessonName = name;
    }
    public String getLessonName(){
        return this.lessonName;
    }
}
