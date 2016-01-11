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
		XmlParser xmlParser = new XmlParser("C:\\Users\\dan\\workspace\\XML Parser 2\\src\\db1234.xml");
		xmlParser.parse();
	}
}
