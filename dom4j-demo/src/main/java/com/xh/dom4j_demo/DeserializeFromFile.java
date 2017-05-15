package com.xh.dom4j_demo;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;
import org.dom4j.io.SAXReader;

public class DeserializeFromFile {

	private String filePath;
	
	public DeserializeFromFile(String filePath) {
		this.filePath = filePath;
	}
	
	public void deserialize() throws Exception {
		
		SAXReader saxReader = new SAXReader();
		
		Document document = saxReader.read(new File(filePath));
		
		// get root element
		Element root = document.getRootElement();
		
		// get all sub elements
		List<Element> childs = root.elements();
		System.out.println("root has " + childs.size() + " child elements");
		
		// get sub element by element name
		Element properties = root.element("properties");
		
		// get all elements with a name
		List<Element> propertyList = properties.elements("property");
		for (Element property : propertyList) {
			for (int i = 0; i < property.attributeCount(); i++) {
				System.out.println(property.attribute(i).getName() + " = " + property.attribute(i).getValue());
			}
		}

		// iterator
		for (Iterator<Element> iter = properties.elementIterator(); iter.hasNext();) {
			Element e = (Element)iter.next();
			System.out.println(e.attributeValue("name") + " = " + e.attributeValue("value"));
		}
		
		
		// another way to get Document
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbFactory.newDocumentBuilder();
		
		org.w3c.dom.Document jaxpDocument = db.parse(new File(filePath));
		DOMReader domReader = new DOMReader();
		
		// transfer JAXP Document to dom4j Document
		Document dom4jDocument = domReader.read(jaxpDocument);
		
		Element rootElem = dom4jDocument.getRootElement();
		System.out.println(rootElem.getName());
	}
}
