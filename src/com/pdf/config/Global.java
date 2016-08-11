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
		translateMap.put("user", "�û���");
		translateMap.put("password", "����");
		translateMap.put("jdbcUrl", "���ݿ��ַ");
		translateMap.put("tomcatHost", "web�����ڷ�������ַ");
		translateMap.put("log4j.appender.database.user", "�û���");
		translateMap.put("log4j.appender.database.password", "����");
		translateMap.put("log4j.appender.database.driver", "���ݿ�����");
		translateMap.put("log4j.appender.database.URL", "���ݿ��ַ");
	}
	
	public static String getTranslateKey(String key){
		if(translateMap.get(key)!=null){
			return translateMap.get(key);
		}else{
			return key;
		}
	}
}
