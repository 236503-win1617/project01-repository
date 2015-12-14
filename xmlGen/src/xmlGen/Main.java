package xmlGen;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.util.HashMap;
public class Main {

	public static void main(String[] args) {
	      try {
	          DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	          DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	          Document doc = dBuilder.newDocument();
	          // root element
	          Element rootElement = doc.createElement("slides");
	          doc.appendChild(rootElement);


	          // write the content into xml file
	          TransformerFactory transformerFactory =
	          TransformerFactory.newInstance();
	          Transformer transformer =
	          transformerFactory.newTransformer();
	          DOMSource source = new DOMSource(doc);
	          StreamResult result =
	          new StreamResult(new File("db.xml"));
	          transformer.transform(source, result);
	          // Output to console for testing

	          //testing adding a slide
	          HashMap<String, String> attrs= new HashMap<String, String>();
	          for(int i=0;i<20;i++){
	        	  attrs.put("attr_"+Integer.toString(i), Integer.toString(i+1));
	          }
	          XmlGenerator slideGenerator=new XmlGenerator("Picture", attrs);
	          slideGenerator.xmlAppend();
	          StreamResult consoleResult =
	          new StreamResult(System.out);
	          transformer.transform(source, consoleResult);
	          
	          
	          
	       } catch (Exception e) {
	          e.printStackTrace();
	       }
	}

}
