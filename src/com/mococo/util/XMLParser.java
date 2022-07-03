package com.mococo.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLParser {
    private Document document;

    public XMLParser(){}

    public void loadFile(String path) throws Exception {
        InputStream inputStream = null;
        Reader reader = null;
        InputSource is = null;

        try {
            File file = new File(path);
            inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream, "UTF-8");
            is = new InputSource(reader);
            is.setEncoding("UTF-8");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.parse(is);
        } catch (Exception var11) {
            throw var11;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }

            if (reader != null) {
                reader.close();
            }

        }

    }

    public void loadString(String contents) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.parse(new ByteArrayInputStream(contents.getBytes()));
        } catch (Exception var4) {
            throw var4;
        }
    }

    public void createXmlDocument() throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.newDocument();
            this.document.setXmlStandalone(true);
        } catch (Exception var3) {
            throw var3;
        }
    }

    public void addElementProperty(Element acquireElement, String key, String value) throws Exception {
        try {
            acquireElement.setAttribute(key, value);
        } catch (Exception var5) {
            throw var5;
        }
    }

    public String getDocumentString() throws Exception {
        StringWriter stringWriter = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty("omit-xml-declaration", "no");
        transformer.setOutputProperty("method", "xml");
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.transform(new DOMSource(this.document), new StreamResult(stringWriter));
        return stringWriter.toString();
    }

    public Element createElement(Element acquireElement, String nodeName, String nodeText) {
        Element newElement = null;

        try {
            newElement = this.document.createElement(nodeName);
            if (nodeText != null && nodeText.length() > 0) {
                newElement.appendChild(this.document.createTextNode(nodeText));
            }

            if (acquireElement == null) {
                this.document.appendChild(newElement);
            } else {
                acquireElement.appendChild(newElement);
            }

            return newElement;
        } catch (Exception var6) {
            throw var6;
        }
    }

    public Node getNode(String path) throws Exception {
        Node node = null;

        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression xPathExpr = xpath.compile(path);
            NodeList nodeList = (NodeList)xPathExpr.evaluate(this.document, XPathConstants.NODESET);
            if (nodeList.getLength() > 0) {
                node = nodeList.item(0);
            }

            return node;
        } catch (Exception var7) {
            throw var7;
        }
    }

    public ArrayList<Node> getNodeList(String path) throws Exception {
        NodeList nodeList = null;
        ArrayList arrayNodeList = new ArrayList();

        try {
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression xPathExpr = xpath.compile(path);
            nodeList = (NodeList)xPathExpr.evaluate(this.document, XPathConstants.NODESET);

            for(int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                if (node.getNodeType() != 3) {
                    arrayNodeList.add(node);
                }
            }

            return arrayNodeList;
        } catch (Exception var9) {
            throw var9;
        }
    }

    public ArrayList<Node> getNodeChildListFromNode(Node acquireNode) throws Exception {
        NodeList childNodeList = null;
        ArrayList arrayNodeList = new ArrayList();

        try {
            childNodeList = acquireNode.getChildNodes();

            for(int i = 0; i < childNodeList.getLength(); ++i) {
                Node node = childNodeList.item(i);
                if (node.getNodeType() != 3) {
                    arrayNodeList.add(node);
                }
            }

            return arrayNodeList;
        } catch (Exception var6) {
            throw var6;
        }
    }

    public String getNodeText(String path) throws Exception {
        String returnString = "";

        try {
            returnString = this.getNode(path).getTextContent();
        } catch (Exception var4) {
            throw var4;
        }

        if (returnString == null) {
            returnString = "";
        }

        return returnString;
    }

    public ConcurrentHashMap<String, String> getNodeAttributes(String path) throws Exception {
        String returnString = "";
        ConcurrentHashMap attrMap = new ConcurrentHashMap();

        try {
            Node node = this.getNode(path);
            NamedNodeMap namedNodeMap = node.getAttributes();

            for(int i = 0; i < namedNodeMap.getLength(); ++i) {
                Attr attr = (Attr)namedNodeMap.item(i);
                String attrName = attr.getNodeName();
                String attrValue = attr.getNodeValue();
                attrMap.put(attrName, attrValue);
            }

            return attrMap;
        } catch (Exception var10) {
            throw var10;
        }
    }

    public ConcurrentHashMap<String, String> getNodeAttributesFromNode(Node node) throws Exception {
        String returnString = "";
        ConcurrentHashMap attrMap = new ConcurrentHashMap();

        try {
            NamedNodeMap namedNodeMap = node.getAttributes();

            for(int i = 0; i < namedNodeMap.getLength(); ++i) {
                Attr attr = (Attr)namedNodeMap.item(i);
                String attrName = attr.getNodeName();
                String attrValue = attr.getNodeValue();
                attrMap.put(attrName, attrValue);
            }

            return attrMap;
        } catch (Exception var9) {
            throw var9;
        }
    }

    public String getNodeAttr(String path, String attrName) throws Exception {
        String returnString = "";

        try {
            Node node = this.getNode(path + "/@" + attrName);
            returnString = node.getNodeValue();
        } catch (Exception var5) {
            throw var5;
        }

        if (returnString == null) {
            returnString = "";
        }

        return returnString;
    }

    public String getNodeTextFromNode(Node node) throws Exception {
        String returnString = "";

        try {
            returnString = node.getTextContent();
        } catch (Exception var4) {
            throw var4;
        }

        if (returnString == null) {
            returnString = "";
        }

        return returnString;
    }

    public String getNodeAttrFromNode(Node node, String attrName) throws Exception {
        String returnString = "";

        try {
            Node attr = node.getAttributes().getNamedItem(attrName);
            if (attr != null) {
                returnString = attr.getNodeValue();
            }
        } catch (Exception var5) {
            throw var5;
        }

        if (returnString == null) {
            returnString = "";
        }

        return returnString;
    }
}