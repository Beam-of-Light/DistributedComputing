package parser;

import entities.Department;
import entities.Discipline;
import entities.Professor;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    DocumentBuilderFactory documentBuilderFactory;
    DocumentBuilder documentBuilder;

    public Parser() throws ParserConfigurationException {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setValidating(true);

        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        documentBuilder.setErrorHandler(new ParserErrorHandler());
    }

    public Department parse(String path) throws IOException, SAXException {
        Document document = documentBuilder.parse(new File(path));
        Element root = document.getDocumentElement();

        String name = root.getAttribute("name");
        String code = root.getAttribute("code");
        Department department = new Department(name, Integer.parseInt(code), new ArrayList<>());

        if (root.getTagName().equals("department")) {
            NodeList professorsList = root.getElementsByTagName("professor");

            for (int i = 0, length = professorsList.getLength(); i < length; ++i) {
                Element professor = (Element) professorsList.item(i);
                String professorName = professor.getAttribute("name");
                Professor professorEntity = new Professor(professorName, new ArrayList<>());

                NodeList disciplinesList = professor.getElementsByTagName("discipline");
                for (int j = 0, eventsListLength = disciplinesList.getLength(); j < eventsListLength; ++j) {
                    Element event = (Element) disciplinesList.item(j);
                    String disciplineName = event.getAttribute("name");
                    String course = event.getAttribute("course");
                    String specialtyCode = event.getAttribute("specialtyCode");

                    Discipline disciplineEntity = new Discipline(
                            disciplineName,
                            Integer.parseInt(course),
                            Integer.parseInt(specialtyCode));
                    professorEntity.addDiscipline(disciplineEntity);
                }
                department.addProfessor(professorEntity);
            }
            return department;
        }
        return null;
    }

    public void writeToXML(String filename, Department department) throws TransformerException {
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("department");

        Attr nameAttr = document.createAttribute("name");
        Attr codeAttr = document.createAttribute("code");

        nameAttr.setValue(department.getName());
        codeAttr.setValue(department.getCode().toString());

        root.setAttributeNode(nameAttr);
        root.setAttributeNode(codeAttr);

        document.appendChild(root);

        List<Professor> professors = department.getProfessors();
        for (Professor p : professors) {
            Element professor = document.createElement("professor");

            Attr pNameAttr = document.createAttribute("name");
            pNameAttr.setValue(p.getName());
            professor.setAttributeNode(pNameAttr);
            root.appendChild(professor);

            List<Discipline> disciplines = p.getDisciplines();

            for (Discipline d : disciplines) {
                Element discipline = document.createElement("discipline");

                Attr dNameAttr = document.createAttribute("name");
                Attr courseAttr = document.createAttribute("course");
                Attr specialtyCodeAttr = document.createAttribute("specialtyCode");

                dNameAttr.setValue(d.getName());
                courseAttr.setValue(d.getCourse().toString());
                specialtyCodeAttr.setValue(d.getSpecialtyCode().toString());

                discipline.setAttributeNode(dNameAttr);
                discipline.setAttributeNode(courseAttr);
                discipline.setAttributeNode(specialtyCodeAttr);

                professor.appendChild(discipline);
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(filename));
        transformer.transform(domSource, streamResult);
    }
}
