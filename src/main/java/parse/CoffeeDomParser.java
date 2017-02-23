package parse;

import entity.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by User on 23.02.2017.
 */
public class CoffeeDomParser extends CoffeeParser {

    public ArrayList<AbstractCoffe> parserCoffe() {
        if (!validateXML()) {
            throw new RuntimeException("Some problem with XML");
        }
        ArrayList<AbstractCoffe> coffeList = new ArrayList<>();

        Document coffeeDocument = getCoffeDocument();

        NodeList nodeList = coffeeDocument.getElementsByTagName("coffee");
        for (int pos = 0; pos < nodeList.getLength(); pos++) {
            Node current = nodeList.item(pos);
            coffeList.add(parseElement((Element) current));
        }
        return coffeList;

    }

    private Document getCoffeDocument() {
        try {
            File file = new File("src/main/resources/coffee.xml");
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private AbstractCoffe parseElement(Element element) {
        AbstractCoffe abstr;
        switch (element.getAttribute("coffeekind")) {
            case "arabica":
                abstr = new ArabicaCoffee();
                getElements(element, abstr);
                return abstr;
            case "dewevrei":
                abstr = new DewevreiCoffe();
                getElements(element, abstr);
                return abstr;
            case "liberica":
                abstr = new CanephoraCoffe();
                getElements(element, abstr);
                return abstr;
            case "canephore":
                abstr = new CanephoraCoffe();
                getElements(element, abstr);
                return abstr;
        }
        return null;
    }

    public void getElements(Element element, AbstractCoffe abstr) {
        Element coffeeType = (Element) element.getElementsByTagName("coffeetype").item(0);
        abstr.setCoffeeSort(coffeeType.getElementsByTagName("sort").item(0).getTextContent());
        abstr.setCoffeeType(coffeeType.getElementsByTagName("type").item(0).getTextContent());
        Element coffeeValue = (Element) element.getElementsByTagName("coffeevalue").item(0);
        abstr.setPrice(Integer.parseInt(coffeeValue.getElementsByTagName("price").item(0).getTextContent()));
        abstr.setWeight(Integer.parseInt(coffeeValue.getElementsByTagName("weight").item(0).getTextContent()));
    }
}

