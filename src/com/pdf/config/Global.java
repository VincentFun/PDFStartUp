package com.pdf.config;

import java.util.HashMap;
import java.util.Map;

public class Global {
	public static final String PROJECT_PATH="PdfTableDemo_v1";
	public static final String C3P0_FILE_NAME="c3p0-config.xml";
	public static final String GLOBAL_FILE_NAME="global_config.xml";
	public static final String LOG_FILE_NAME="log4j.properties";
	
	public static final String PDF_RESOLVE_NAME="PdfResolve";
	public static final String XML_TO_INTERTABLE_NAME="Xml2Intertable";
	
	private static Map<String,String> translateMap=new HashMap<String,String>();
	
	
	static{
		translateMap.put("user", "用户名");
		translateMap.put("password", "密码");
		translateMap.put("jdbcUrl", "数据库地址");
		translateMap.put("tomcatHost", "web端所在服务器地址");
		translateMap.put("log4j.appender.database.user", "用户名");
		translateMap.put("log4j.appender.database.password", "密码");
		translateMap.put("log4j.appender.database.driver", "数据库驱动");
		translateMap.put("log4j.appender.database.URL", "数据库地址");
	}
	
	public static String getTranslateKey(String key){
		if(translateMap.get(key)!=null){
			return translateMap.get(key);
		}else{
			return key;
		}
	}
}
