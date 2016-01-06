/**
 * @author philip&avi
 *
 */
package newProject;

import newProject.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import newProject.Picture;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
public class DOM4JCreateXMLDemo {
//	public static void main(String[] args) throws IOException {
//		try {
//			Document document = DocumentHelper.createDocument();
//			Element root = document.addElement( "slide" );
//			
//			Picture picture1= new Picture("\"C:\\Users\\akozokin\\Desktop\\cat.jpg\"",85,85,100,100);
//			Picture picture2= new Picture("\"C:\\Users\\akozokin\\Desktop\\cat.jpg\"",150,150,0,0);
//			Element pictureElement= root.addElement("picture")
//					.addAttribute("id", "1");
//
//			pictureElement.addElement("path")
//			.addText(picture1.getPath());
//			pictureElement.addElement("xsize")
//			.addText(picture1.getxsize().toString());
//			pictureElement.addElement("ysize")
//			.addText(picture1.getysize().toString());
//			pictureElement.addElement("xcord")
//			.addText(picture1.getxcord().toString());
//			pictureElement.addElement("ycord")
//			.addText(picture1.getycord().toString());
//			Element pictureElement2= root.addElement("picture")
//					.addAttribute("id", "2");
//
//			pictureElement2.addElement("path")
//			.addText(picture2.getPath());
//			pictureElement2.addElement("xsize")
//			.addText(picture2.getxsize().toString());
//			pictureElement2.addElement("ysize")
//			.addText(picture2.getysize().toString());
//			pictureElement2.addElement("xcord")
//			.addText(picture2.getxcord().toString());
//			pictureElement2.addElement("ycord")
//			.addText(picture2.getycord().toString());
//
//			// Pretty print the document to System.out
//			OutputFormat format = OutputFormat.createPrettyPrint();
//			XMLWriter writer;
//			OutputStream output = new FileOutputStream("C:\\Users\\akozokin\\Desktop\\db1234.xml");
//			writer = new XMLWriter( output, format );
//			writer.write( document );
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			//      } catch (IOException e) {
//			//         e.printStackTrace();
//			//      }
//		}
//	}
//}
	static void generate(ArrayList<AbstractSlide> list,String name) throws IOException{
		try{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement( "lesson" );
		int index=0;
		for(AbstractSlide slide:list){
			if(slide instanceof PictureSlide){
				PictureSlide item = (PictureSlide) slide; 
				Element pictureElement= root.addElement("slide");
				pictureElement.addElement("order")
				.addText(Integer.toString((index)));
				pictureElement.addElement("slide_type")
				.addText("Picture");
				pictureElement.addElement("image_file")	
				.addText(item.getPictureFile().toString());
				Element soundsList= pictureElement.addElement("sounds_list");
				for(SoundElement soundItem:item.getSoundElements()){
					Element soundButton=soundsList.addElement("sound_button");
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
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer;
OutputStream output = new FileOutputStream(".\\db1234.xml");
		writer = new XMLWriter( output, format );
		writer.write( document );
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		//      } catch (IOException e) {
		//         e.printStackTrace();
		//      }
	}
	}
}