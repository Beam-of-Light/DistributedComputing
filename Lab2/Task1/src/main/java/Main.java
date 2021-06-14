import org.xml.sax.SAXException;
import entities.Department;
import parser.Parser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser();
            Department department = parser.parse("./objects/data.xml");
            if (department != null) {
                System.out.println(department);
                parser.writeToXML("./objects/result.xml", department);
            }
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            System.out.println("Something bad happened while parsing");
            e.printStackTrace();
        }
    }
}