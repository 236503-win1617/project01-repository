package SlideManagers;

import AdditionalClasses.SoundElement;
import SlideObjects.AbstractSlide;
import SlideObjects.GameSlide;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by dan on 08/05/2016.
 */
public class GameSlideManager extends AbstractSlideManager {
    private GameSlide.GameType type;

    public GameSlideManager(JPanel currentSlide, JPanel commandsPanel){
        super(currentSlide, commandsPanel);
    }

    public void onRotateCommand() throws IOException{}

    public void loadSlide(AbstractSlide slide) throws IOException{
        //How do I make this work?
        // There should be a default image on slide depending on game type.
        this.type = ((GameSlide)slide).getGameType();
        switch(type){
            case Animals:
                loadPictureFile(new File(".\\resources\\animals.jpg"));
                break;
            case Colors:
                loadPictureFile(new File(".\\resources\\colors.jpg"));
                break;
            case Numbers:
                loadPictureFile(new File(".\\resources\\numbers.jpg"));
                break;
        }
    }

    public void saveDataToCurrentSlide(){}

    protected void setSpecificCommandsButtons(){}

    protected void specificClearContent(){}

    protected void setSpecificButtonsVisibility(boolean visibility){}

    public void loadPictureFile(File imageFile){
//        currentSlidePanel.removeAll();
//
//        BufferedImage image = ImageIO.read(imageFile);
//
//        int imageHeight = image.getHeight();
//        int imageWidth = image.getWidth();
//
//        AffineTransform tx = new AffineTransform();
//        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//        image = op.filter(image, null);
//
//        Image resizedImage = getResizedImage(image, imageHeight, imageWidth);
//
//        ImageIcon imageIcon = new ImageIcon(resizedImage);
//        JLabel imageLabel = new JLabel("", imageIcon, JLabel.CENTER);
//
//        setConstraints(0, 0, 1, 1);
//
//        currentSlidePanel.add(imageLabel, constraints);
//        currentSlidePanel.revalidate();
//        currentSlidePanel.repaint();
    }

    public void addNewSoundElement(SoundElement soundElement){}
}
