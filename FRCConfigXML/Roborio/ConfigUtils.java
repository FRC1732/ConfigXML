import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/*
 * I got a lot of this code from here: http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 * 
 * Tutorial for xml notation: https://www.w3schools.com/xml/xml_usedfor.asp
 * 
 */
public class ConfigUtils {

	/*
	 * Makes a DOM document from the text of an XML file
	 * 
	 * This method would be used when receiving the XML file as a string through the
	 * network tables
	 * 
	 * The caller should handle exceptions if they happen and stop the robot program
	 * from dying.
	 * 
	 * Because this is only for receiving the xml files from the driverstation, if
	 * an exception happens it is probably a problem with the syntax of the xml file
	 * and should be fixed on the driverstation then tried again
	 */
	public static Document makeDOMdoc(String fileText) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		InputSource source = new InputSource(new StringReader(fileText));

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(source);
		doc.getDocumentElement().normalize();

		return doc;
	}

	/*
	 * This is just helpful to verify that stuff got sent correctly
	 */
	public static void printDOM(Document doc) {
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		if (doc.hasChildNodes()) {
			printNodes(doc.getChildNodes());
		}
	}

	private static void printNodes(NodeList nodeList) {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// get node name and value
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());
				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
					}
				}
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNodes(tempNode.getChildNodes());
				}
				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
			}
		}
	}

	public static String fileToString(String pathToFile) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(pathToFile));
		return new String(encoded, Charset.defaultCharset());
	}

	/*
	 * Makes a new xml file, or overwrites existing file
	 */
	public static void saveDom(Document doc, String pathToFile, String name)
			throws TransformerFactoryConfigurationError, TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		Result output = new StreamResult(new File("SavedConfig.xml"));
		Source input = new DOMSource(doc);

		transformer.transform(input, output);
	}
}