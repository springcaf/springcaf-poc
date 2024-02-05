package com.springcaf.core.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class XmlDocumentUtils {
	
	private static DocumentBuilder docBuilder = null;
	
	/**
	 * Initialize
	 */
	static
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try
		{
			docBuilder = docFactory.newDocumentBuilder();
		}
		catch(ParserConfigurationException pce)
		{
			throw new RuntimeException(pce);
		}
	}
	
	/**
	 * Parse a file into XML document
	 * @param fileName
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document parseXmlFile(String fileName) throws SAXException, IOException
	{
		if(StringUtils.isNullOrEmpty(fileName))
		{
			throw new IOException("File does not exist");
		}
		
		File xmlFile = new File(fileName);
		Document doc = docBuilder.parse(xmlFile);
		
		// normalize
		doc.getDocumentElement().normalize();
		
		return doc;
	}

	/**
	 * Convert XML string into XML document
	 * @param xmlStr
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public static Document parseXmlString(String xmlStr) throws SAXException, IOException
	{
		if(StringUtils.isNullOrEmpty(xmlStr))
		{
			throw new IOException("Invalid XML String");
		}
		
		InputSource xmlSource = new InputSource(new StringReader(xmlStr));
		Document doc = docBuilder.parse(xmlSource);
		
		// normalize
		doc.getDocumentElement().normalize();
		
		return doc;
	}

	/**
	 * Get node list by name
	 * @param doc
	 * @param tabName
	 * @return
	 */
	public static NodeList getNodeListByName(Document doc, String tabName)
	{
		return doc.getElementsByTagName(tabName);
	}
	
	/**
	 * Convert a Document into a String
	 * @param doc
	 * @return
	 * @throws TransformerException
	 */
	public static String convertXmlToString(Document doc) throws TransformerException
	{
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
		
		return output;
	}
	
	/**
	 * Create a blank document
	 * @return
	 */
	public static Document createNewDocument()
	{
		Document doc = docBuilder.newDocument();
		
		return doc;
	}
	
    /**
     * Create root element
     * @param doc
     * @param rootElementName
     * @return
     */
    public static Node createRootElement(Document doc, String rootElementName)
    {
        Element mainRootElement = doc.createElement(rootElementName);
        doc.appendChild(mainRootElement);
        
        return mainRootElement;
    }
 
    /**
     * Create root element with a namespace
     * @param doc
     * @param namespace
     * @param rootElementName
     * @return
     */
    public static Node createRootElementNS(Document doc, String namespaceName, String rootElementName)
    {
        Element mainRootElement = doc.createElementNS(namespaceName, rootElementName);
        doc.appendChild(mainRootElement);
        
        return mainRootElement;
    }

    /**
     * Create a non-leaf node. attrNames and attrValues should have same size
     * @param doc
     * @param parentNode
     * @param nodeName
     * @param attrNames
     * @param attrValues
     * @return
     */
    public static Node createNodeWithAttributes(Document doc, Node parentNode, String nodeName, String[] attrNames, String[] attrValues) {
        Element element = doc.createElement(nodeName);
        
        if(attrNames != null)
        {
        	for(int i=0; i<attrNames.length; i++)
        	{
        		element.setAttribute(attrNames[i], attrValues[i]);
        	}
        }
        
        parentNode.appendChild(element);

        return element;
    }
 
    /**
     * Create a TextNode
     * @param doc
     * @param parentNode
     * @param name
     * @param value
     * @return
     */
    public static Node createTextNode(Document doc, Node parentNode, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        parentNode.appendChild(node);
        
        return node;
    }
    
    /**
     * Get the text value from XML document
     * @param doc
     * @param nodeName
     * @return
     */
    public static String getXmlTextValueByNodeName(Document doc, String nodeName)
    {
    	NodeList list = doc.getElementsByTagName(nodeName);
    	
    	if(list != null && list.getLength() > 0)
    	{
    		Node node = list.item(0);
    		Element element = (Element)node;
    		String value = element.getNodeValue();
    		value = element.getTextContent();
    		
    		return value;
    	}
    	
    	return "";
    }
}
