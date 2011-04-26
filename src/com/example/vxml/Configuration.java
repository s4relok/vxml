package com.example.vxml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.example.vxml.Runner.COFIGURATION_FILE_PATH;

/**
 * Class that represents configuration state.
 * Can be loaded from xml.
 *
 * @author Andrey Kolesin
 */

@XmlRootElement
public class Configuration {

    private String xmlFilePath;
    private String xsdFilePath;
    private String dtdFilePath;
    private Operation selectedOperation;
    private String[] element;

    private static Configuration inst;

    public static Configuration getInst() {
        return inst;
    }

    public static void init(String[] args) {
        try {
            JAXBContext jc = JAXBContext.newInstance(Configuration.class);
            Unmarshaller u = jc.createUnmarshaller();
            inst = (Configuration) u.unmarshal(new FileInputStream(COFIGURATION_FILE_PATH));
            if (args.length == 0) {
                return;
            }
            inst.setSelectedOperation(args[0].startsWith("-v") ?
                    Operation.Validate : args[0].startsWith("-c") ? Operation.Count : null);
            if (inst.getSelectedOperation() == Operation.Validate && args.length > 1) {
                inst.setXmlFilePath(args[1]);
                if (args.length < 3) {
                    return;
                }
                if (args[2].endsWith(".xsd")) {
                    inst.setXsdFilePath(args[2]);
                } else if (args[2].endsWith(".dtd")) {
                    inst.setDtdFilePath(args[2]);
                }
            } else if (inst.getSelectedOperation() == Operation.Count && args.length > 2) {
                inst.setElement(args[1].split(","));
                inst.setXmlFilePath(args[2]);
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }

    public String getXsdFilePath() {
        return xsdFilePath;
    }

    public String getDtdFilePath() {
        return dtdFilePath;
    }

    public Operation getSelectedOperation() {
        return selectedOperation;
    }

    public String[] getElement() {
        return element;
    }

    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public void setXsdFilePath(String xsdFilePath) {
        this.xsdFilePath = xsdFilePath;
    }

    public void setDtdFilePath(String dtdFilePath) {
        this.dtdFilePath = dtdFilePath;
    }

    public void setSelectedOperation(Operation selectedOperation) {
        this.selectedOperation = selectedOperation;
    }

    public void setElement(String[] element) {
        this.element = element;
    }
}

