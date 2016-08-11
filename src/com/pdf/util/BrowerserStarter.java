package com.pdf.util;

import java.io.IOException;

public class BrowerserStarter implements Starter {

	private String urlPath;
	
	public BrowerserStarter(String urlPath) {
		this.urlPath=urlPath;
	}
	
	@Override
	public void start() {
		if(java.awt.Desktop.isDesktopSupported()){
            
            //创建一个URI实例,注意不是URL
            java.net.URI uri=java.net.URI.create(urlPath);
            //获取当前系统桌面扩展
            java.awt.Desktop dp=java.awt.Desktop.getDesktop();
            //判断系统桌面是否支持要执行的功能
            if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                //获取系统默认浏览器打开链接
                try {
					dp.browse(uri);
					onSuccess("启动成功");
				} catch (IOException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
					onFail("启动失败！");
				}
            }
            
        }
	}

	@Override
	public void onSuccess(String message) {
		System.out.println(message);
	}

	@Override
	public void onFail(String message) {
		System.out.println();
	}

}
