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

	//将XML的叶子节点以键值对的形式保存至hashmap中
	private	HashMap<String, String> map=new HashMap<String,String>();

	
	private	String attributeName=null;
	
	public XMLHandler(String fileName){
		super.fileName=fileName;
	}
	
	public XMLHandler(){
		
	}

	public HashMap<String,String> parseToMap(){
		super.traverse(fileName);//回调handle
		return map;
	}
	
	//将叶子节点属性为attributeName的值与值以键值对的形式添加至map中
	@Override
	void handle(Element node) {
		/*	默认按照节点名提取
		 *	例如<property name="xxx">yyy</property> 提取成 <property-,yyy>
		 * 	当设置了attributeName时，按照attributeName提取
		 *	例如<property attributeName="xxx">yyy</property> 提取成<xxx,yyy>
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
	//将新的键值对updateMap更新到XML配置文件中
	public boolean updateXmlFile(HashMap<String,String> updateMap) {
		
		Iterator<String> keySet = updateMap.keySet().iterator();
		while (keySet.hasNext()) {
			String key=keySet.next();
			//如果没有更新则跳过
			String updataValue=updateMap.get(key);
			String value=map.get(key);
			if(updateMap.get(key).equals(map.get(key))){
				continue;
			}else{//如果配置文件某一项发生了修改则更新
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
			}else{//没有设置属性则直接按标签名进行提取
				if(node.getName().equals(key)){
					node.setText(value);
				}
			}
			
		}
	}
	
}
