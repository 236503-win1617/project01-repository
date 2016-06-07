/**
 * @author philip&avi
 *
 */
package Factories;

import AdditionalClasses.SoundElement;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import slides.AbstractSlide;
import slides.ListenAndFindGameSlide;
import slides.OrderGameSlide;
import slides.PictureSlide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class LessonsFactory {
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
                    String[] bits = fileName.split("\\\\");
                    if (bits.length < 2) {
                        bits = fileName.split("////");
                    }
                    String lastOne = bits[bits.length - 1];
                    pictureElement.addElement("image_file").addText("./" + lastOne);
                    Element soundsList = pictureElement.addElement("sounds_list");
                    for (SoundElement soundItem : item.getSoundElements()) {
                        Element soundButton = soundsList.addElement("sound_button");
                        String soundName = soundItem.soundFile.toString();
                        String[] bits2 = soundName.split("\\\\");
                        if (bits2.length < 2) {
                            bits2 = soundName.split("////");
                        }
                        String lastOne2 = bits2[bits2.length - 1];

                        soundButton.addElement("sound_file")
                                .addText("./" + lastOne2);
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
                if (slide instanceof ListenAndFindGameSlide) {
                    ListenAndFindGameSlide item = (ListenAndFindGameSlide) slide;
                    Element GameElement = root.addElement("slide");
                    GameElement.addElement("order").addText(Integer.toString((index)));
                    GameElement.addElement("slide_type").addText("game");
                    GameElement.addElement("gameType").addText(item.getGameType().name());
                    GameElement.addElement("game").addText(item.getGameType().name());
                    index++;
                }
                if (slide instanceof OrderGameSlide) {
                    OrderGameSlide item = (OrderGameSlide) slide;
                    Element GameElement = root.addElement("slide");
                    GameElement.addElement("order").addText(Integer.toString((index)));
                    GameElement.addElement("slide_type").addText("game");
                    GameElement.addElement("max_num").addText(Integer.toString(item.getMaxNum()));
                    GameElement.addElement("gameType").addText("ORDER");
                    //GameElement.addElement("game").addText(item.getGameType().name());
                }
            }
            boolean success = (new File(".//xmlDir")).mkdirs();
            if (!success) {
                // Directory already exists which is good
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer;
            //Random random = new Random();

            File f = new File(".\\xmlDir\\" + name + "\\" + name + ".xml");
            //f.createNewFile();
            //while(f.exists()) {
            //	random = new Random();
            //	 f = new File(".\\xmlDir\\db"+Integer.toString(random.nextInt())+".xml");
            //}
            OutputStream output = new FileOutputStream(f.getPath());
            writer = new XMLWriter(output, format);
            writer.write(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}