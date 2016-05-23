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
import slides.PictureSlide;
import slides.VideoSlide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class LessonsFactory {
	//TODO: start using the name
	//TODO: what is the index for ?
	//TODO: the file saving shouldn't be there
	//TODO: Delete the creating of file from this method
	//TODO: Stop using the xmlDir and switch to ../LessonNameDir
	public static void generateXmlFromLesson(List<AbstractSlide> list, String name) {
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
					pictureElement.addElement("rotation").addText(slide.getRotation().toString());
					File pictureFile = item.getPictureFile();
					String fileName = (pictureFile == null) ? "" : pictureFile.toString();
					String [] relPath = fileName.split("\\\\");
					if(relPath.length<2){
						relPath = fileName.split("////");
					}
					String lastOne = relPath[relPath.length-1];
					pictureElement.addElement("image_file").addText("./"+lastOne);
					Element soundsList = pictureElement.addElement("sounds_list");
					for (SoundElement soundItem : item.getSoundElements()) {
						Element soundButton = soundsList.addElement("sound_button");
						String soundName=soundItem.soundFile.toString();
						String [] relPath2 = soundName.split("\\\\");
						if(relPath2.length<2){
							relPath2 = soundName.split("////");
						}
						String lastOne2 = relPath2[relPath2.length-1];

						soundButton.addElement("sound_file")
						.addText("./"+lastOne2);
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
				}if(slide instanceof  ListenAndFindGameSlide) {
                    ListenAndFindGameSlide item = (ListenAndFindGameSlide) slide;
                    Element GameElement = root.addElement("slide");
                    GameElement.addElement("order").addText(Integer.toString((index)));
                    GameElement.addElement("slide_type").addText("game");
                    GameElement.addElement("gameType").addText(item.getGameType().name());
                    GameElement.addElement("game").addText(item.getGameType().name());
                    index++;
                } if (slide instanceof  OrderGameSlide) {
                    OrderGameSlide item = (OrderGameSlide) slide;
                    Element GameElement = root.addElement("slide");
                    GameElement.addElement("order").addText(Integer.toString((index)));
                    GameElement.addElement("slide_type").addText("game");
                    GameElement.addElement("max_num").addText(Integer.toString(item.getMaxNum()));
                    //GameElement.addElement("game").addText(item.getGameType().name());
                }
				if(slide instanceof VideoSlide)
				{
					VideoSlide item = (VideoSlide) slide;
					Element videoElement = root.addElement("slide");
					videoElement.addElement("order").addText(Integer.toString((index)));
					videoElement.addElement("slide_type").addText("Video");
					File videoFile = item.getVideoFile();
					String fileName = (videoFile == null) ? "" : videoFile.toString();
					String [] relPath = fileName.split("\\\\");
					if(relPath.length<2){
						relPath = fileName.split("////");
					}
					String lastOne = relPath[relPath.length-1];
					videoElement.addElement("image_file").addText("./"+lastOne);
				}
			}
			boolean success = (new File(".//xmlDir")).mkdirs();
			if (!success) {
				// Directory already exists which is good
			}

			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter writer;
			int counter = 0;
			File f = new File(".\\xmlDir\\db"+Integer.toString(counter)+".xml");
			while(f.exists()) { 
				counter++;
				f = new File(".\\xmlDir\\db"+Integer.toString(counter)+".xml");
			}
			OutputStream output = new FileOutputStream(f.getPath());
			writer = new XMLWriter( output, format );
			writer.write( document );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//TODO: implement
	public static List<AbstractSlide> generateLessonFromXml(Document document) {
		throw new UnsupportedOperationException();
	}
}