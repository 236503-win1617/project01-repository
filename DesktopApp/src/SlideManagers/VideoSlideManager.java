package SlideManagers;

import AdditionalClasses.SoundElement;
import Factories.ComponentsFactory;
import Resources.FileResources;
import Resources.MessageErrors;
import screens.Screens;
import slides.AbstractSlide;
import slides.Rotation;
import slides.VideoSlide;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Evgeniy on 4/2/2016.
 */
public class VideoSlideManager extends AbstractSlideManager {
    private VideoSlide currentSlide;
    private JButton choosePictureButton;
    private JButton playVideoButton;

    public VideoSlideManager(JPanel currentSlide, JPanel commandsPanel) {
        super(currentSlide, commandsPanel);
    }

    @Override
    public void onRotateCommand() throws IOException {
        Screens.CreateLessonScreen.showErrorMessage("Rotation isn't supported for video for now");
    }

    @Override
    public void loadSlide(AbstractSlide slide, JButton slideButton) throws IOException {
        setSpecificButtonsVisibility(true);
        if (!slide.getClass().equals(VideoSlide.class)) {
            throw new UnsupportedOperationException();
        }

        currentSlide = (VideoSlide) slide;
        this.slideButton = slideButton;

        File videoFile = currentSlide.getVideoFile();
        double noRotation = Rotation.NO_ROTATION.getRotationInRadians();

        if (videoFile == null) {
            loadImageToSlidePanel(ImageIO.read(FileResources.getNoVideoStream()), noRotation);
            setImageOnSlideButton(ImageIO.read(FileResources.getButtonNoVideo()), noRotation);
        } else {
            loadImageToSlidePanel(ImageIO.read(FileResources.getOkStream()), noRotation);
            setImageOnSlideButton(ImageIO.read(FileResources.getVideoSelectedButton()), noRotation);
            setVideoPathTextPane(videoFile.getAbsolutePath());
        }
    }

    @Override
    protected void setSpecificCommandsButtons() {
        setPlayVideoButton();
        setChooseVideoButton();
    }

    private void setPlayVideoButton() {
        playVideoButton = new JButton("Play Video");
        playVideoButton.addActionListener(e -> playVideo(currentSlide.getVideoFile()));
        setConstraints(0, 6, 1, 1);
        playVideoButton.setVisible(false);
        commandsPanel.add(playVideoButton, constraints);
    }

    //TODO: TBD maybe switch to VLCJ play ???
    private void playVideo(File videoFile) {
        try {
            if (videoFile != null) {
                Desktop.getDesktop().open(videoFile);
            } else {
                Screens.CreateLessonScreen.showErrorMessage("No video to play");
            }
        } catch (Exception ex) {
            Screens.CreateLessonScreen.showErrorMessage(MessageErrors.UNEXPECTED_ERROR + " " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void saveDataToCurrentSlide() {
    }

    @Override
    protected void setSpecificButtonsVisibility(boolean visibility) {
        playVideoButton.setVisible(visibility);
        choosePictureButton.setVisible(visibility);
    }

    @Override
    protected void specificClearContent() {
        currentSlide = null;
    }

    private void setChooseVideoButton() {
        choosePictureButton = new JButton("Select Video");
        choosePictureButton.addActionListener(e -> selectVideoFile());
        setConstraints(0, 5, 1, 1);
        choosePictureButton.setVisible(false);
        commandsPanel.add(choosePictureButton, constraints);
    }

    private void selectVideoFile() {
        if (currentSlide == null) {
            Screens.CreateLessonScreen.showErrorMessage(MessageErrors.DEFAULT_NO_SLIDE_ERROR);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(Screens.CreateLessonScreen) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            loadVideoFile(selectedFile);
        }
    }

    private void loadVideoFile(File videoFile) {
        if (!currentSlide.isFileNameSupported(videoFile.getName())) {
            Screens.CreateLessonScreen.showErrorMessage(videoFile.getName() + " Isn't supported video format");
            return;
        }
        try {
            currentSlide.setSlideFile(videoFile);
            Screens.CreateLessonScreen.showInformationMessage("The video was added to the slide");

            double noRotation = Rotation.NO_ROTATION.getRotationInRadians();
            loadImageToSlidePanel(ImageIO.read(FileResources.getOkStream()), noRotation);
            setImageOnSlideButton(ImageIO.read(FileResources.getVideoSelectedButton()), noRotation);
            setVideoPathTextPane(videoFile.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            Screens.CreateLessonScreen.showErrorMessage(ex.getMessage());
        }
    }

    private void setVideoPathTextPane(String filePath) {
        JTextPane videoText = ComponentsFactory.createBasicTextPane("");
        videoText.setFont(new Font("", Font.PLAIN, 30));
        videoText.setText(String.format("The video for the slide is:\n%s", filePath));
        videoText.setForeground(Color.GREEN);
        videoText.setBackground(Color.BLACK);

        setConstraints(0, 1, 1, 1);
        currentSlidePanel.add(videoText, constraints);
    }

    public void addNewSoundElement(SoundElement soundElement) {
    }

    @Override
    public void loadDroppedFile(File droppedFile) {
        loadVideoFile(droppedFile);
    }
}
