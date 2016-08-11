package com.pdf.util;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


public class XMLHandler extends XMLLeafTraverse{

	//��XML��Ҷ�ӽڵ��Լ�ֵ�Ե���ʽ������hashmap��
	private	HashMap<String, String> map=new HashMap<String,String>();

	
	private	String attributeName=null;
	
	public XMLHandler(String fileName){
		super.fileName=fileName;
	}
	
	public XMLHandler(){
		
	}

	public HashMap<String,String> parseToMap(){
		super.traverse(fileName);//�ص�handle
		return map;
	}
	
	//��Ҷ�ӽڵ�����ΪattributeName��ֵ��ֵ�Լ�ֵ�Ե���ʽ�����map��
	@Override
	void handle(Element node) {
		/*	Ĭ�ϰ��սڵ�����ȡ
		 *	����<property name="xxx">yyy</property> ��ȡ�� <property-,yyy>
		 * 	��������attributeNameʱ������attributeName��ȡ
		 *	����<property attributeName="xxx">yyy</property> ��ȡ��<xxx,yyy>
		*/
		String key;
		if(attributeName!=null){
			key=node.attributeValue(attributeName);
		}else{
			key=node.getName();
		}
		
		String value=node.getText();
		if(key!=null&&!key.equals("")){
			map.put(key,value);
			System.out.println(key + ":" + value);
		}
	}


	public String getValue(String key){
		return map.get(key);
	}
	//���µļ�ֵ��updateMap���µ�XML�����ļ���
	public boolean updateXmlFile(HashMap<String,String> updateMap) {
		
		Iterator<String> keySet = updateMap.keySet().iterator();
		while (keySet.hasNext()) {
			String key=keySet.next();
			//���û�и���������
			String updataValue=updateMap.get(key);
			String value=map.get(key);
			if(updateMap.get(key).equals(map.get(key))){
				continue;
			}else{//��������ļ�ĳһ������޸������
				new XMLUpdater(key,updateMap.get(key)).traverse(super.document);
			}
		}
		try {
			write();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void write() throws IOException {
	   
		FileOutputStream fos = new FileOutputStream(fileName);  
		OutputStreamWriter osw = new OutputStreamWriter(fos,"utf-8");  
		OutputFormat format = OutputFormat.createPrettyPrint();  
		format.setEncoding("utf-8");  
		XMLWriter writer = new XMLWriter(osw, format);  
		writer.write(super.document); 
		writer.close();
	
	}
	

	public HashMap<String, String> getMap() {
		return map;
	}

	public void setMap(HashMap<String, String> map) {
		this.map = map;
	}
	
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}


	class XMLUpdater extends XMLLeafTraverse{

		private String key;
		private String value;
		
		public XMLUpdater(String key,String value){
			this.key=key;
			this.value=value;
		}
		
		@Override
		void handle(Element node) {
			
			if(attributeName!=null&&node.attributeValue(attributeName)!=null){
				if(node.attributeValue(attributeName).equals(key)){
					node.setText(value);
				}
			}else{//û������������ֱ�Ӱ���ǩ��������ȡ
				if(node.getName().equals(key)){
					node.setText(value);
				}
			}
			
		}
	}
	
}
