package com.example.vxml;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DTDValidator implements IValidator {

    public void validate(File file) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new org.xml.sax.ErrorHandler() {
                public void fatalError(SAXParseException e) throws SAXException {
                    System.out.println("Fattal error: ");
                    printInfo(e);
                }

                //Validation errors
                public void error(SAXParseException e) throws SAXParseException {
                    System.out.println("Error: ");
                    printInfo(e);
                }

                //Show warnings
                public void warning(SAXParseException e) throws SAXParseException {
                    System.out.println("Warning: ");
                    printInfo(e);
                }

                private void printInfo(SAXParseException e) {
                    System.out.println("   Line number: " + e.getLineNumber());
                    System.out.println("   Column number: " + e.getColumnNumber());
                    System.out.println("   Message: " + e.getMessage());
                }
            });
            Document xmlDocument = builder.parse(new FileInputStream(Configuration.getInst().getXmlFilePath()));
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                }
            });
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, Configuration.getInst().getDtdFilePath());
            transformer.transform(source, result);

            System.out.println("DTD validation successfully done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
