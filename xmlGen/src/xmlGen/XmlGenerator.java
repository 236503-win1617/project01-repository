package xmlGen;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;

//this class is responsible for adding an xml representation to a specific slide to the big xml file.
public class XmlGenerator {
	String type;
	HashMap<String, String> attrs;
	public XmlGenerator(String type,HashMap<String, String> attrs) {
		this.type = type;
		this.attrs = attrs;
	}
	
	public void xmlAppend() throws Exception{
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("db.xml");
        Element root = document.getDocumentElement();
        
        Element newSlide = document.createElement(this.type);
        for (HashMap.Entry<String, String> entry : attrs.entrySet()) {
        	String key = entry.getKey();
        	String val = entry.getValue();
            Attr attr = document.createAttribute(key);
            attr.setValue(val);
            newSlide.setAttributeNode(attr);
        }
        root.appendChild(newSlide);

        DOMSource source = new DOMSource(document);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("db.xml");
        transformer.transform(source, result);
        
	}
	
}
