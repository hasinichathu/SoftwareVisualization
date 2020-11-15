/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello3D.test;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Hasini
 */
public class XMLGenerator {

    public static void main(String[] args) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("src");
            doc.appendChild(rootElement);

            // staff elements
            Element packageName = doc.createElement("Package");
            rootElement.appendChild(packageName);

            // set attribute to staff element
            Attr attr = doc.createAttribute("name");
            attr.setValue("1");
            packageName.setAttributeNode(attr);

            // shorten way
            // staff.setAttribute("id", "1");
            // firstname elements
            Element firstname = doc.createElement("firstname");
            firstname.appendChild(doc.createTextNode("yong"));
            packageName.appendChild(firstname);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("F:\\jmonkey\\file.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

}

//output looks like this
//<?xml version="1.0" encoding="UTF-8" standalone="no" ?> 
//<company>
//    <staff id="1">
//        <firstname>yong</firstname>
//        <lastname>mook kim</lastname>
//        <nickname>mkyong</nickname>
//        <salary>100000</salary>
//    </staff>
//</company>
