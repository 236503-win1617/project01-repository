package Factories;

import AdditionalClasses.SoundElement;
import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;

public class DOM4JCreateXMLDemo {

    //TODO: start using the name
    //TODO: what is the index for ?
    public static void generate(ArrayList<AbstractSlide> list, String name) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("lesson");
        int index = 0;
        for (AbstractSlide slide : list) {
            if (slide instanceof PictureSlide) {
                PictureSlide item = (PictureSlide) slide;
                Element pictureElement = root.addElement("slide");
                pictureElement.addElement("order")
                        .addText(Integer.toString((index)));
                pictureElement.addElement("slide_type")
                        .addText("Picture");
                pictureElement.addElement("image_file")
                        .addText(item.getPictureFile().toString());
                Element soundsList = pictureElement.addElement("sounds_list");
                for (SoundElement soundItem : item.getSoundElements()) {
                    Element soundButton = soundsList.addElement("sound_button");
                    soundButton.addElement("sound_file")
                            .addText(soundItem.soundFile.toString());
                    soundButton.addElement("start_x")
                            .addText(Integer.toString(soundItem.start_x));
                    soundButton.addElement("start_y")
                            .addText(Integer.toString(soundItem.start_y));
                    soundButton.addElement("width")
                            .addText(Integer.toString(soundItem.height));
                    soundButton.addElement("height")
                            .addText(Integer.toString(soundItem.width));
                }
                index++;
            }
        }
    }
}