package newProject;

import AdditionalClasses.SoundElement;
import SlideObjects.AbstractSlide;
import SlideObjects.PictureSlide;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;

public class CreateXmlFactory {
    //TODO: start using the name
    //TODO: what is the index for ?
    public static void generate(ArrayList<AbstractSlide> list, String name) {
        try {
            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("lesson");
            int index = 0;
            for (AbstractSlide slide : list) {
                if (slide instanceof PictureSlide) {
                    PictureSlide item = (PictureSlide) slide;
                    Element pictureElement = root.addElement("slide");

                    pictureElement.addElement("order").addText(Integer.toString((index)));
                    pictureElement.addElement("slide_type").addText("Picture");

                    File pictureFile = item.getPictureFile();
                    String fileName = (pictureFile == null) ? "" : pictureFile.toString();

                    pictureElement.addElement("image_file").addText(fileName);
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
		boolean success = (new File(".//xmlDir")).mkdirs();
		if (!success) {
		    // Directory already exists which is good
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer;
	    Random random = new Random();
	    File f = new File(".\\xmlDir\\db"+Integer.toString(random.nextInt())+".xml");
	    while(f.exists()) { 
	    	random = new Random();
	    	 f = new File(".\\xmlDir\\db"+Integer.toString(random.nextInt())+".xml");
	    }
		OutputStream output = new FileOutputStream(f.getPath());
		writer = new XMLWriter( output, format );
		writer.write( document );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}