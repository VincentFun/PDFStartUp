package com.pdf.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesHandler {
	
	private String filePath;
	private HashMap<String,String> map;
	private Properties properties;
	
	public PropertiesHandler(String filePath){
		this.filePath=filePath;
		
		
		
	}
	
	
	public HashMap<String,String> properToMap(){
		Properties pps = new Properties();
		map=new HashMap<String,String>();
		 try {
			pps.load(new FileInputStream(filePath));
			Enumeration enumeration = pps.propertyNames();//得到配置文件的名字
	        while(enumeration.hasMoreElements()) {
	            String key = (String) enumeration.nextElement();
	            String value = pps.getProperty(key);
	            System.out.println(key + "=" + value);
	            map.put(key, value);
	        }

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	public boolean updateProperties(HashMap<String,String> updateMap){
		if(save(updateMap)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean save(HashMap<String,String> map){
		try {
			Properties pps = new Properties();
			
	
			OutputStream out =new FileOutputStream(filePath);  
			Iterator<String> keySet = map.keySet().iterator();
			while (keySet.hasNext()) {
				String key=keySet.next();  
				String value=map.get(key);
				pps.setProperty(key, value);
			}
			
			pps.store(out, "update");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getProperty(String key){
		InputStream in;
		try {
			if(properties==null){
				//in = new BufferedInputStream( new FileInputStream(filePath));
				in=new FileInputStream(filePath);
				
				properties = new Properties();
				properties.load(in);
				return properties.getProperty(key);
			}else{
				return properties.getProperty(key);
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
	}
}
