package SlideManagers;

import Factories.ComponentsFactory;
import Resources.FileResources;
import Resources.MessageErrors;
import SlideObjects.AbstractSlide;
import SlideObjects.Rotation;
import SlideObjects.VideoSlide;
import screens.Screens;

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

    public VideoSlideManager(JPanel currentSlide, JPanel commandsPanel) {
        super(currentSlide, commandsPanel);
    }

    @Override
    public void onRotateCommand() throws IOException {
        Screens.CreateLessonScreen.showErrorMessage("Rotation isn't supported for video for now");
    }

    //TODO: load video
    @Override
    public void loadSlide(AbstractSlide slide) throws IOException {
        setSpecificButtonsVisibility(true);
        if (!slide.getClass().equals(VideoSlide.class)) {
            throw new UnsupportedOperationException();
        }

        currentSlide = (VideoSlide) slide;

        File videoFile = currentSlide.getVideoFile();
        if (videoFile == null) {
            loadPictureFromFile(FileResources.noVideoAvailable, Rotation.NO_ROTATION);
        } else {
            loadPictureFromFile(FileResources.okPicture, currentSlide.getRotation());
            setVideoFileTextPane(videoFile);
        }
    }

    @Override
    protected void setSpecificCommandsButtons() {
        setChooseVideoButton();
    }

    @Override
    public void saveDataToCurrentSlide() {
    }

    @Override
    protected void setSpecificButtonsVisibility(boolean visibility) {
        choosePictureButton.setVisible(visibility);
    }

    @Override
    protected void specificClearContent() {
        currentSlide = null;
    }

    private void setChooseVideoButton() {
        choosePictureButton = new JButton("Select Video");
        choosePictureButton.addActionListener(e -> selectVideoFile());
        setConstraints(0, 4, 1, 1);
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
            File videoFile = chooser.getSelectedFile();

            if (!currentSlide.isFileNameSupported(videoFile.getName())) {
                Screens.CreateLessonScreen.showErrorMessage(videoFile.getName() + " Isn't supported video format");
            } else {
                try {
                    currentSlide.setSlideFile(videoFile);
                    Screens.CreateLessonScreen.showInformationMessage("The video was added to the slide");

                    loadPictureFromFile(FileResources.okPicture, Rotation.NO_ROTATION);
                    setVideoFileTextPane(videoFile);
                } catch (Exception ex) {
                    Screens.CreateLessonScreen.showErrorMessage(ex.getMessage());
                }
            }
        }
    }

    private void setVideoFileTextPane(File videoFile) {
        JTextPane videoText = ComponentsFactory.createBasicTextPane("");
        videoText.setFont(new Font("", Font.PLAIN, 30));
        videoText.setText(String.format("The selected video file is: \n%s", videoFile.getAbsolutePath()));
        videoText.setForeground(Color.GREEN);
        videoText.setBackground(Color.BLACK);

        setConstraints(0, 1, 1, 1);
        currentSlidePanel.add(videoText, constraints);
    }
}
