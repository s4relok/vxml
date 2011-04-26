package com.example.vxml;

import java.io.File;
import java.util.Map;

public class Runner {

    public final static String COFIGURATION_FILE_PATH = "./configuration.xml";

    /**
     * Examples of usage via CLI:
     * -c element1,element2,element3 file.xml   Count number of specified elements in file.xml
     * -v file.xml file.xsd                     Validate file.xml by file.xsd schema
     * -v file.xml file.dtd                     Validate file.xml by file.dtd
     * <p/>
     * Configuration although is stored in configuration.xml
     *
     * @param args CL arguments
     */
    public static void main(String[] args) {

        Configuration.init(args);
        Configuration config = Configuration.getInst();
        if (config.getSelectedOperation() == null) {
            System.out.println("Please choose the operation");
            return;
        }
        if (config.getXmlFilePath() == null || config.getXmlFilePath().equals("")) {
            System.out.println("Please specify path to XML file");
            return;
        }
        switch (config.getSelectedOperation()) {
            case Count:
                if (config.getElement() == null || config.getElement().length == 0) {
                    System.out.println("Please specify elements to count");
                    return;
                }
                NodeCounter counter = new NodeCounter(new File(config.getXmlFilePath()));
                Map<String, Integer> result = counter.countNodes(config.getElement());
                if (result.size() == 0) {
                    System.out.format("Nothing is found");
                    break;
                }
                System.out.format("Element name:            Number in XML:\n");
                for (Map.Entry<String, Integer> entry : result.entrySet()) {
                    System.out.format("%13s %24d\n", entry.getKey(), entry.getValue());
                }
                break;
            case Validate:

                IValidator validator;
                if (config.getXsdFilePath() != null) {
                    validator = new XSDValidator();
                } else if (config.getDtdFilePath() != null) {
                    validator = new DTDValidator();
                } else {
                    System.out.println("Please specify path to DTD or XSD schema");
                    return;
                }
                validator.validate(new File(config.getXmlFilePath()));
        }
    }
}
