package com.pdf.util;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public abstract class XMLLeafTraverse {
	
		protected String fileName;
		protected Document document;
		
		//遍历XML
		public void traverse(String fileName) {
			File inputXml = new File(fileName);
			SAXReader saxReader = new SAXReader();
		
			saxReader.setValidation(false);
			
			try {
				document = saxReader.read(inputXml);
				
				Element node = document.getRootElement();
				parse(node);
				
			} catch (DocumentException e) {
				System.out.println(e.getMessage());
			}
		
		}
		
		public void traverse(Document document){
			Element node = document.getRootElement();
			parse(node);
		}
		
		//递归访问到叶子节点
		private void parse(Element node){
			
			for (Iterator<Element> i = node.elementIterator(); i.hasNext();) {
				Element subNode = i.next();
				parse(subNode);
			}
			if (node.elements().isEmpty()) {
				handle(node);
			}
			
			
		}
		abstract void handle(Element node);
}
