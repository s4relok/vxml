package com.example.vxml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NodeCounter {

    private Document document;
    private String[] names;
    private Map<String, Integer> result = new HashMap<String, Integer>();

    public NodeCounter(File file) {
        try {
            DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.document = parser.parse(file);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> countNodes(String[] names) {
        this.names = names;
        traversal(document.getChildNodes().item(0));
        return result;
    }

    private void traversal(Node node) {
        for (String name : names) {
            if (node.getNodeName().equals(name)) {
                result.put(name, result.containsKey(name) ? result.get(name) + 1 : 1);
            }
        }
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            traversal(children.item(i));
        }
    }
}