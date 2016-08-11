package com.pdf.frame;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.pdf.config.Global;
import com.pdf.util.PropertiesHandler;
import com.pdf.util.XMLHandler;

public class ConfigureEditFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	//��ͼÿ�е��и�
	private int lineHeight=40;
	//���XML�����ļ���ԭ�еļ�ֵ��
	private HashMap<String,String> map=new LinkedHashMap<>();
	//����û��޸ĺ�ļ�ֵ��
	private HashMap<String,String> updateMap;
	
	//���水ť
	private JButton btnSave;
	//�ָ�Ĭ������
	private JButton btnReset;
	
	//XML�����������ڽ���XML�ĵ��Լ�����XML�ĵ�
	private XMLHandler xmlHandler;
	
	private PropertiesHandler propHandler;
	
	private String filePath;
	/**
	 * Create the frame.
	 */
	public ConfigureEditFrame(String filePath) {
		this.filePath=filePath;
		
		//�ĵ�ΪXML�ļ�
		if(filePath.endsWith("xml")){
			xmlHandler=new XMLHandler(filePath);
			//�ĵ��ڵ�Ľṹ��һ���������ò�ͬ����ȡ��������Ҫ��Keyֵ����ȡ����
			if(filePath.contains(Global.GLOBAL_FILE_NAME)
					&&filePath.contains(Global.PDF_RESOLVE_NAME)){
				xmlHandler.setAttributeName("key");
				HashMap<String,String> tmpMap=xmlHandler.parseToMap();
				
				//ֻ����tomcatHost
				map.put("tomcatHost", tmpMap.get("tomcatHost"));
				
			}else if(filePath.contains(Global.C3P0_FILE_NAME)){
				xmlHandler.setAttributeName("name");
				HashMap<String,String> tmpMap=xmlHandler.parseToMap();
				
				//C3P0�����ĵ�ֻ������user��password��jdbcUrl������಻��ʾ
				map.put("user", tmpMap.get("user"));
				map.put("password", tmpMap.get("password"));
				map.put("jdbcUrl", tmpMap.get("jdbcUrl"));
				
				
				
				new C3P0ConfigEditFrame(xmlHandler, map).setVisible(true);
				this.dispose();
				return;
				
			}else{
				HashMap<String,String> tmpMap=xmlHandler.parseToMap();
				
				//ֻ��ʾtomcatHost
				map.put("tomcatHost", tmpMap.get("tomcatHost"));
			}
			
		}else if(filePath.endsWith("properties")){//�ĵ�Ϊproperties�ļ�
			propHandler=new PropertiesHandler(filePath);
			HashMap<String,String> tmpMap=propHandler.properToMap();
			
			//��ʾ
			map.put("log4j.appender.database.user", tmpMap.get("log4j.appender.database.user"));
			map.put("log4j.appender.database.password", tmpMap.get("log4j.appender.database.password"));
			map.put("log4j.appender.database.URL", tmpMap.get("log4j.appender.database.URL"));
			map.put("log4j.appender.database.driver", tmpMap.get("log4j.appender.database.driver"));
		
			
		}
//		
		//��ʼ���༭����
		init();
	}
	
	private void init(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 200, 450, map.size()*lineHeight+80);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		this.setTitle("�����ļ��޸�");
		this.setVisible(true);
		generateView();
	}
	
	//����map�еļ�ֵ�����ɱ༭��ͼ
	private void generateView() {
		contentPane.setLayout(new GridLayout(map.size() + 1, 2, 0, 0));

		Iterator<String> keySet = map.keySet().iterator();
		while (keySet.hasNext()) {

			String key = keySet.next();
			String value = map.get(key);
			JLabel label = new JLabel(Global.getTranslateKey(key));
			contentPane.add(label);

			JTextField text = new JTextField();
			text.setText(value);
			text.setToolTipText(key);
			contentPane.add(text);
			text.setColumns(10);
		}

		btnSave = new JButton("����");
		btnReset = new JButton("�ָ�Ĭ������");
		contentPane.add(btnSave);
		contentPane.add(btnReset);
		
		btnSave.addActionListener(this);
		btnReset.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSave){	
			getUpdateMap();
			if(filePath.endsWith("xml")){
				if(xmlHandler.updateXmlFile(updateMap)){
					onSuccess();
				}else{
					onFail();
				}
			}else if(filePath.endsWith("properties")){
				if(propHandler.updateProperties(updateMap)){
					onSuccess();
				}else{
					onFail();
				}
			}
			
		}else if(e.getSource()==btnReset){
			resetTextField();
		}
	}

	

	//��ȡ��ǰ�µļ�ֵ��
	private void getUpdateMap() {
		updateMap=new HashMap<String,String>();
		for(int i=0;i<contentPane.getComponentCount();i++){
			Component text=contentPane.getComponent(i);
			
			if(text instanceof JTextField){
				String key=((JTextField) text).getToolTipText();
				String value=((JTextField) text).getText();
				updateMap.put(key, value);
				System.out.print(key+":");
				System.out.println(value);
			}
		}
	}

	//�����е��ı�����������ԭ��Ĭ������
	private void resetTextField(){
		for(int i=0;i<contentPane.getComponentCount();i++){
			Component text=contentPane.getComponent(i);
			
			if(text instanceof JTextField){
				String key=((JTextField) text).getToolTipText();
				((JTextField) text).setText(map.get(key));
				
			}
		}
	}
	
	private void onSuccess(){
		JOptionPane.showMessageDialog(contentPane, "�����ļ��޸ĳɹ�", "�ɹ�",JOptionPane.WARNING_MESSAGE);  
		dispose();

	}
	
	private void onFail(){
		JOptionPane.showMessageDialog(contentPane,  "�����ļ��޸�ʧ��","ʧ��",JOptionPane.WARNING_MESSAGE);  
	}
}
