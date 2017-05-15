package com.xh.dom4j_demo;

import java.io.FileOutputStream;
import java.io.FileWriter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

//<?xml version="1.0" encoding="UTF-8"?>
//<project>
//	<name>dom4j-demo</name>
//	<url>http://www.xiaohe.com</url>
//	
//	<properties>
//		<property name="service" value="encode-decode"/>
//		<property name="version" value="1.0.0-SNAPSHOT"/>
//	</properties>
//</project>

public class SerializeToFile {

	private String filePath;
	
	public SerializeToFile(String filePath) {
		this.filePath = filePath;
	}
	
	public void serialize() throws Exception {
		
		// create root element node - way1
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("project");
		document.setRootElement(root);
		
		// create root element node - way2
//		Element root = DocumentHelper.createElement("project");
//		Document document = DocumentHelper.createDocument(root);
		
		// add sub-element node
		Element nameElem = root.addElement("name");
		Element urlElem = root.addElement("url");
		
		// set sub-element node value
		nameElem.setText("dom4j-demo");
		urlElem.setText("http://www.xiaohe.com");
		
		Element propertiesElem = root.addElement("properties");
		Element propertyElem = propertiesElem.addElement("property");
		// set attribute
		propertyElem.addAttribute("name", "service");
		propertyElem.addAttribute("value", "encode-decode");
		
		propertyElem = propertiesElem.addElement("property");
		// set attribute
		propertyElem.addAttribute("name", "version");
		propertyElem.addAttribute("value", "1.0.0-SNAPSHOT");
		
		// output to console
//		XMLWriter consoleWriter = new XMLWriter();
//		consoleWriter.write(document);
		
		// output to file - way1
		OutputFormat format = new OutputFormat("    ", true);	// 设置缩进为4个空格，并且另起一行为true
		XMLWriter fileWriter1 = new XMLWriter(new FileOutputStream(filePath), format);
		fileWriter1.write(document);
		
		XMLWriter fileWriter2 = new XMLWriter(new FileWriter(filePath), format);
		fileWriter2.write(document);
		fileWriter2.flush();
	}
}
