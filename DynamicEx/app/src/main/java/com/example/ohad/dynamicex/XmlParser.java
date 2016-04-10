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

public class XmlParser {
	private String path;

	public enum Game {
		COLORS,NUMBERS,ANIMALS,MEMORY
	}

	XmlParser(String path) {
		this.path = path;
	}

	void parse(Lesson lesson) { // parse(Lesson lesson)
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();

			FileInputStream fis = new FileInputStream(this.path);
			InputSource is = new InputSource(fis);
			Document doc = builder.parse(is);

			Element root = doc.getDocumentElement();
			NodeList nodes = root.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node n = nodes.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {

					//create list for all buttons and texts for this slide
					ArrayList<DynamicButton> dynamicButtonsArr = new ArrayList<>();
					ArrayList<DynamicText> dynamicTextsArr = new ArrayList<>();

					Element e = (Element) n;
					//Get the type of slide
					String type = e.getElementsByTagName("slide_type").item(0).getTextContent();
					System.out.println(type);

					if (type.equals("Picture")) {
						//Get the path of picture
						String picturePath = e.getElementsByTagName("image_file").item(0).getTextContent();
						System.out.println(picturePath);

						//Iterate over all the buttons
						NodeList nButtons = e.getElementsByTagName("sound_button");
						for (int j = 0; j < nButtons.getLength(); j++) {
							Node nBut = nButtons.item(j);
							if (nBut.getNodeType() == Node.ELEMENT_NODE) {

								Element eBut = (Element) nBut;

								//Get the path for sound file
								String bSoundPath = eBut.getElementsByTagName("sound_file").item(0).getTextContent();

								//Get the start x coordinate
								int bStartX = Integer.parseInt(eBut.getElementsByTagName("start_x").item(0).getTextContent());

								//Get the start y coordinate
								int bStartY = Integer.parseInt(eBut
										.getElementsByTagName("start_y").item(0)
										.getTextContent());

								//Get the width
								int bWidth = Integer.parseInt(eBut.getElementsByTagName("width").item(0).getTextContent());

								//Get the height
								int bHeight = Integer.parseInt(eBut.getElementsByTagName("height").item(0).getTextContent());

								//create new DynamicButton
								DynamicButton newDynamicButton = new DynamicButton("Push Me", bStartX, bStartY, bWidth, bHeight, bSoundPath);
								dynamicButtonsArr.add(newDynamicButton);
							}
						}

						//
						//for testing
						Slide newSlide = new PictureSlide(picturePath, dynamicButtonsArr, dynamicTextsArr);
						//Slide newSlide
						lesson.addSlide(newSlide);
					}
					if(type.equals("game")){
						XmlParser.Game gameType = XmlParser.Game.valueOf(e.getElementsByTagName("gameType").item(0).getTextContent());
						System.out.println(gameType);
						Slide newSlide = new GameSlide(gameType,lesson.activity);
						lesson.addSlide(newSlide);
					}
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Slide newSlide = new GameSlide(Game.NUMBERS,lesson.activity);
		lesson.addSlide(newSlide);
	}
}
