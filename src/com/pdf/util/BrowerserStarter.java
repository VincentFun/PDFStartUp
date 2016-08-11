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
            
            //����һ��URIʵ��,ע�ⲻ��URL
            java.net.URI uri=java.net.URI.create(urlPath);
            //��ȡ��ǰϵͳ������չ
            java.awt.Desktop dp=java.awt.Desktop.getDesktop();
            //�ж�ϵͳ�����Ƿ�֧��Ҫִ�еĹ���
            if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                //��ȡϵͳĬ�������������
                try {
					dp.browse(uri);
					onSuccess("�����ɹ�");
				} catch (IOException exc) {
					// TODO Auto-generated catch block
					exc.printStackTrace();
					onFail("����ʧ�ܣ�");
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
