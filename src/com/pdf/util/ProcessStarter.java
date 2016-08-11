package com.pdf.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//用户启动一个jar程序
public abstract class ProcessStarter implements Starter{

	private String filePath;
	private String progressName;
	
	public ProcessStarter(String filePath){
		this.filePath=filePath;
		this.progressName=filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
		System.out.println(progressName);
	}
	
	public ProcessStarter() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void start() {
		

		Runtime rn = Runtime.getRuntime();
		Process p = null;
		
//		if(isStarted(progressName)){
//			onFail("启动失败：该进程已经被启动");
//			return;
//		}
		
		if(filePath.endsWith("jar")){//启动jar文件
//			try {
//				//cmd /k start 该指令打开命令行窗口
//				p = rn.exec("cmd /k start D: && cd D:\\app && dir /b");
//				System.out.println("cmd /k start java -jar "+filePath);
//			} catch (Exception e) {
//				onFail("启动失败");
//			}
		}else if(filePath.contains("startup.bat")){//启动tomcat的startup.bat文件
			try {
				p = rn.exec("cmd /k start "+filePath);
				System.out.println("cmd /k start "+filePath);
				
				//Thread.currentThread();
				Thread.sleep(10000);
			
				if (!isTomcatStarted()) {

					onFail("tomcat启动失败");

				} else {
					onSuccess("tomcat启动成功");
				}
			} catch (Exception e) {
				onFail("tomcat启动失败");
			}
		} else {
			try {
				p = rn.exec("cmd /k start "+filePath);
				System.out.println("cmd /k start "+filePath);
//				System.out.println(filePath);
//				String disk=filePath.substring(0, filePath.indexOf(":"));
//				System.out.println("cmd /k start && "+disk+": && cd "+ filePath.substring(0,filePath.lastIndexOf("\\")));
//				p = rn.exec("cmd /k start && "+disk+": && cd "+ filePath.substring(0,filePath.lastIndexOf("\\")));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				onFail("启动失败");
				e.printStackTrace();
			}
			
		}
		
//		//进程中存在后即启动成功
//		if(isStarted(progressName)){
//			onSuccess("启动成功");
//		}
	}

	

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		this.progressName=filePath.substring(filePath.lastIndexOf("/")+1, filePath.length());
	}

	@Override
	public abstract void onSuccess(String message) ;
	
	@Override
	public abstract void onFail(String message);

	//检查某个进程是否已经运行
//	private boolean isStarted(String processName){
//		BufferedReader br=null;   
//        try{   
//            Process proc=Runtime.getRuntime().exec("tasklist -fi " + '"' + "imagename eq " + processName +'"');   
//            br=new BufferedReader(new InputStreamReader(proc.getInputStream()));   
//            String line=null;   
//            while((line=br.readLine())!=null){   
//                //判断指定的进程是否在运行   
//                if(line.contains(processName)){   
//                    return true;   
//                }   
//            }   
//                
//            return false;   
//        }catch(Exception e){   
//            e.printStackTrace();   
//           return false;   
//        }finally{   
//            if(br!=null){   
//                try{   
//                    br.close();   
//                }catch(Exception ex){   
//                }   
//            }   
//                
//        }
//	}
	

	private boolean isTomcatStarted(){
		URL url;
		try {
			url = new URL("http://localhost:8080");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setConnectTimeout(300000);
			httpUrlConnection.setReadTimeout(300000);
			httpUrlConnection.connect();
			String code = new Integer(httpUrlConnection.getResponseCode()).toString();
			if (!code.startsWith("2")) {

				return false;

			} else {
				return true;
			}
		} catch (MalformedURLException e) {
			//e.printStackTrace();
			return false;
		} catch (IOException e) {
	
			//e.printStackTrace();
			return false;
		}
		
	}
}
