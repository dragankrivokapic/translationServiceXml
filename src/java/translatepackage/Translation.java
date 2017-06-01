package translatepackage;

import java.io.File;
import java.io.IOException;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Zoran
 */
@WebService(serviceName = "Translate")
public class Translation {

    private final Document doc;

    public Translation() throws ParserConfigurationException, SAXException, IOException {
        File file = new File("C:/reci.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(file);
        doc.getDocumentElement().normalize();
    }

    @WebMethod(operationName = "translate")
    public String getWord(String word, String sourceLanguage, String translationLanguage) {
        if (doc == null) {
            return "XML file not loaded!";
        }
        NodeList words = doc.getElementsByTagName(sourceLanguage);

        for (int temp = 0; temp < words.getLength(); temp++) {
            Node node = words.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NodeList prevodi = element.getElementsByTagName(word);
                for (int k = 0; k < prevodi.getLength(); k++) {
                    Node prevod = prevodi.item(k);
                    Element elem = (Element) prevod;
                    return elem.getElementsByTagName(translationLanguage).item(0).getTextContent();
                }
            }
        }
        return "translation failed";
    }
}
