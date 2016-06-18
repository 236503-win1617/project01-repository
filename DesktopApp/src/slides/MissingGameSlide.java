package slides;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by dan on 16/05/2016.
 */
public class MissingGameSlide extends AbstractSlide {

    public MissingGameSlide() {
        super();
    }

    @Override
    public SlideType getType() {
        return SlideType.MissingGame;
    }

    @Override
    public void setSlideFile(File file) {

    }

    @Override
    protected String[] getSupportedFormats() {
        return new String[0];
    }
}

