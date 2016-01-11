import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlParser {
	private String path;

	XmlParser(String path) {
		this.path = path;
	}

	void parse() { // parse(Lesson lesson)
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
					Element e = (Element) n;
					//Get the type of slide
					String type = e.getElementsByTagName("slide_type").item(0).getTextContent();
					System.out.println(type);
					NodeList nPaths;
					if (type.equals("Picture")) {
						//Get the path of picture
						nPaths = e.getElementsByTagName("image_file"); 
						System.out.println(nPaths.item(0).getTextContent());
					}
					//Iterate over all the buttons
					NodeList nButtons = e.getElementsByTagName("sound_button");
					for (int j = 0; j < nButtons.getLength(); j++) {
						Node nBut = nButtons.item(j);
						if (nBut.getNodeType() == Node.ELEMENT_NODE) {
							Element eBut = (Element) nBut;
							//Get the path for sound file
							String bSoundPath = eBut
									.getElementsByTagName("sound_file").item(0)
									.getTextContent();
							//Get the start x coordinate
							int bStartX = Integer.parseInt(eBut
									.getElementsByTagName("start_x").item(0)
									.getTextContent());
							//Get the start y coordinate
							int bStartY = Integer.parseInt(eBut
									.getElementsByTagName("start_y").item(0)
									.getTextContent());
							//Get the width
							int bWidth = Integer.parseInt(eBut
									.getElementsByTagName("width").item(0)
									.getTextContent());
							//Get the height
							int bHeight = Integer.parseInt(eBut
									.getElementsByTagName("height").item(0)
									.getTextContent());
							System.out.println(bSoundPath);
							System.out.println(Integer.toString(bStartX));
							System.out.println(Integer.toString(bStartY));
							System.out.println(Integer.toString(bWidth));
							System.out.println(Integer.toString(bHeight));
						}
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
