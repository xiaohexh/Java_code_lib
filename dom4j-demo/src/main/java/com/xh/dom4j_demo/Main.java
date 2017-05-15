package com.xh.dom4j_demo;

public class Main {
    public static void main( String[] args ) throws Exception {
    	
    	String filePath = "dom4j-demo-test.xml";
    	
    	SerializeToFile serializeToFile = new SerializeToFile(filePath);
    	serializeToFile.serialize();
    	
    	DeserializeFromFile deserializeFromFile = new DeserializeFromFile(filePath);
    	deserializeFromFile.deserialize();
    	
    }
}
