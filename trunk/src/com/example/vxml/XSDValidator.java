package com.example.vxml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XSDValidator implements IValidator {

    public void validate(File file) {
        try {
            DocumentBuilder parser =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = parser.parse(file);
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaFile = new StreamSource(new File(Configuration.getInst().getXsdFilePath()));
            Schema schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));
            System.out.println("XSD validation successfully done");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXParseException e) {
            System.out.println("Error: ");
            printInfo(e);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void printInfo(SAXParseException e) {
        System.out.println("   Line number: " + e.getLineNumber());
        System.out.println("   Column number: " + e.getColumnNumber());
        System.out.println("   Message: " + e.getMessage());
    }
}
