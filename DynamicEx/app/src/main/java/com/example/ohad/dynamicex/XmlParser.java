package com.example.ohad.dynamicex;

import java.io.FileInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Created by Ohad.
 */

public class XmlParser {

	public enum Game {
		COLORS,NUMBERS,ANIMALS,ORDER
	};

	public void parse(String xmlPath, String lessonPath, Lesson lesson) {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			FileInputStream fis = new FileInputStream(xmlPath);
			InputSource is = new InputSource(fis);
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			NodeList nodes = root.getChildNodes();

			for (int i = 0; i < nodes.getLength(); i++) {
				Node n = nodes.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {

					Element e = (Element) n;
					String type = e.getElementsByTagName("slide_type").item(0).getTextContent();

					if (type.equals("Picture")) {
						handlePictureSlide(e, lessonPath, lesson);
					}

					else if (type.equals("Video")) {
						handleVideoSlide(e, lessonPath, lesson);
					}
					else if(type.equals("game")){
						handleGameSlide(e, lesson);
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleGameSlide(Element e, Lesson lesson) {
		XmlParser.Game gameType = XmlParser.Game.valueOf(e.getElementsByTagName("gameType").item(0).getTextContent());
		Slide newSlide = new GameSlide(gameType,lesson.activity);
		if (gameType == XmlParser.Game.ORDER){
			int maxNum = Integer.parseInt(e.getElementsByTagName("max_num").item(0).getTextContent());
			((GameSlide)newSlide).setOrderGameNum(maxNum);
		}
		lesson.addSlide(newSlide);
	}

	private void handlePictureSlide(Element e, String lessonPath, Lesson lesson) {
		//create list for all buttons and texts for this slide
		ArrayList<DynamicButton> dynamicButtonsArr = new ArrayList<>();
		ArrayList<DynamicText> dynamicTextsArr = new ArrayList<>();

		//Get the path of picture
		String picturePath = e.getElementsByTagName("image_file").item(0).getTextContent();
		picturePath = lessonPath + picturePath;

		//Get rotation - not supported by XML generators yet
		// String rotation = Integer.parseInt(e.getElementsByTagName("rotation").item(0).getTextContent());
		Rotation rotation = null;

		//Iterate over all the buttons
		NodeList nButtons = e.getElementsByTagName("sound_button");
		for (int j = 0; j < nButtons.getLength(); ++j) {
			Node nBut = nButtons.item(j);
			if (nBut.getNodeType() == Node.ELEMENT_NODE) {
				Element eBut = (Element) nBut;

				//Get the path for sound file
				String bSoundPath = eBut.getElementsByTagName("sound_file").item(0).getTextContent();
				bSoundPath = lessonPath + bSoundPath;

				//Get x and y position
				int bStartX = Integer.parseInt(eBut.getElementsByTagName("start_x").item(0).getTextContent());
				int bStartY = Integer.parseInt(eBut.getElementsByTagName("start_y").item(0).getTextContent());

				//Get width and height of button
				int bWidth = Integer.parseInt(eBut.getElementsByTagName("width").item(0).getTextContent());
				int bHeight = Integer.parseInt(eBut.getElementsByTagName("height").item(0).getTextContent());

				//create DynamicButton
				DynamicButton newDynamicButton = new DynamicButton("Push Me", bStartX, bStartY, bWidth, bHeight, bSoundPath);
				dynamicButtonsArr.add(newDynamicButton);
			}
		}

		Slide newSlide = new PictureSlide(picturePath, dynamicButtonsArr, dynamicTextsArr, rotation);
		lesson.addSlide(newSlide);
	}

	private void handleVideoSlide(Element e, String lessonPath, Lesson lesson) {
		String videoPath = e.getElementsByTagName("video_file").item(0).getTextContent();
		videoPath = lessonPath + videoPath;
		Slide newSlide = new VideoSlide(videoPath);
		lesson.addSlide(newSlide);
	}
}
