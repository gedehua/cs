package com.gedehua.utils;

import com.gedehua.POJO.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@inisys
public class Init {
    public List<Student> init() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("D:\\idea_prj\\cs\\src\\main\\resources\\student.xml");
        NodeList names = doc.getElementsByTagName("name");
        NodeList nos = doc.getElementsByTagName("no");
        NodeList scores = doc.getElementsByTagName("score");
        List<Student> resList = new ArrayList<>();
        for(int i = 0;i<names.getLength();i++){
            Element item = (Element) names.item(i);
            Element item1 = (Element) nos.item(i);
            Element item2 = (Element) scores.item(i);
            String name = item.getFirstChild().getNodeValue();
            String no = item1.getFirstChild().getNodeValue();
            String score = item2.getFirstChild().getNodeValue();
            resList.add(new Student(name,no,Float.parseFloat(score)));
        }
        return resList;
    }
}
