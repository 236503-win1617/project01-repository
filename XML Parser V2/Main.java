import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Main {
	public static void main(String[] args) {

	      // create a new DocumentBuilderFactory
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	      try {
	         // use the factory to create a documentbuilder
	         DocumentBuilder builder = factory.newDocumentBuilder();

	         // create a new document from input source
	         FileInputStream fis = new FileInputStream("C:\\Users\\dan\\workspace\\XML Parser 1.0\\src\\db.xml");
	         InputSource is = new InputSource(fis);
	         Document doc = builder.parse(is);

	         // get the first element
	         Element root = doc.getDocumentElement();
	         if (!root.getNodeName().equals("slide")){
	        	 System.out.print("Not a valid xml file");
	        	 return;
	         }
	         
	         NodeList nodes = root.getChildNodes(); 
	         
	         Slide slide = new Slide();
	         
	         // print the text content of each child
	         for (int i = 0; i < nodes.getLength(); i++) {
	        	 Node n = nodes.item(i);
	        	 if (n.getNodeType() == Node.ELEMENT_NODE){
	        		 System.out.println("\nCurrent Element :"  + n.getNodeName());
	        		 Picture picture;
	        		 Element eElement = (Element) n;
	        		 System.out.println("Element ID: " + eElement.getAttribute("id"));
	        		 int id = Integer.parseInt(eElement.getAttribute("id"));
	        		 System.out.println("Path: " + eElement.getElementsByTagName("path")
	        				 .item(0).getTextContent());
	        		 String path = eElement.getElementsByTagName("path").item(0).getTextContent();
	        		 System.out.println("X Size: " + eElement.getElementsByTagName("xsize")
	        				 .item(0).getTextContent());
	        		 int xSize = Integer.parseInt(eElement.getElementsByTagName("xsize").
	        				 item(0).getTextContent());
	        		 System.out.println("Y Size: " + eElement.getElementsByTagName("ysize")
	        				 .item(0).getTextContent());
	        		 int ySize = Integer.parseInt(eElement.getElementsByTagName("ysize").
	        				 item(0).getTextContent());
	        		 System.out.println("X Coordinate: " + eElement.getElementsByTagName("xcord")
	        				 .item(0).getTextContent());
	        		 int xCord = Integer.parseInt(eElement.getElementsByTagName("xcord").
	        				 item(0).getTextContent());
	        		 System.out.println("Y Coordinate: " + eElement.getElementsByTagName("ycord")
	        				 .item(0).getTextContent());
	        		 int yCord = Integer.parseInt(eElement.getElementsByTagName("ycord").
	        				 item(0).getTextContent());
	        		 slide.addPicture(new Picture(id, path,xSize, ySize, xCord, yCord));
	        	 }
	         }
	      } catch (Exception ex) {
	         ex.printStackTrace();
	      }
	}
}
