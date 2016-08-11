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
	//视图每行的行高
	private int lineHeight=40;
	//存放XML配置文件中原有的键值对
	private HashMap<String,String> map=new LinkedHashMap<>();
	//存放用户修改后的键值对
	private HashMap<String,String> updateMap;
	
	//保存按钮
	private JButton btnSave;
	//恢复默认设置
	private JButton btnReset;
	
	//XML处理器，用于解析XML文档以及更新XML文档
	private XMLHandler xmlHandler;
	
	private PropertiesHandler propHandler;
	
	private String filePath;
	/**
	 * Create the frame.
	 */
	public ConfigureEditFrame(String filePath) {
		this.filePath=filePath;
		
		//文档为XML文件
		if(filePath.endsWith("xml")){
			xmlHandler=new XMLHandler(filePath);
			//文档节点的结构不一样，需设置不同的提取方法，主要是Key值的提取差异
			if(filePath.contains(Global.GLOBAL_FILE_NAME)
					&&filePath.contains(Global.PDF_RESOLVE_NAME)){
				xmlHandler.setAttributeName("key");
				HashMap<String,String> tmpMap=xmlHandler.parseToMap();
				
				//只配置tomcatHost
				map.put("tomcatHost", tmpMap.get("tomcatHost"));
				
			}else if(filePath.contains(Global.C3P0_FILE_NAME)){
				xmlHandler.setAttributeName("name");
				HashMap<String,String> tmpMap=xmlHandler.parseToMap();
				
				//C3P0配置文档只需配置user、password、jdbcUrl几项，其余不显示
				map.put("user", tmpMap.get("user"));
				map.put("password", tmpMap.get("password"));
				map.put("jdbcUrl", tmpMap.get("jdbcUrl"));
				
				
				
				new C3P0ConfigEditFrame(xmlHandler, map).setVisible(true);
				this.dispose();
				return;
				
			}else{
				HashMap<String,String> tmpMap=xmlHandler.parseToMap();
				
				//只显示tomcatHost
				map.put("tomcatHost", tmpMap.get("tomcatHost"));
			}
			
		}else if(filePath.endsWith("properties")){//文档为properties文件
			propHandler=new PropertiesHandler(filePath);
			HashMap<String,String> tmpMap=propHandler.properToMap();
			
			//显示
			map.put("log4j.appender.database.user", tmpMap.get("log4j.appender.database.user"));
			map.put("log4j.appender.database.password", tmpMap.get("log4j.appender.database.password"));
			map.put("log4j.appender.database.URL", tmpMap.get("log4j.appender.database.URL"));
			map.put("log4j.appender.database.driver", tmpMap.get("log4j.appender.database.driver"));
		
			
		}
//		
		//初始化编辑窗口
		init();
	}
	
	private void init(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300, 200, 450, map.size()*lineHeight+80);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		this.setTitle("配置文件修改");
		this.setVisible(true);
		generateView();
	}
	
	//根据map中的键值对生成编辑视图
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

		btnSave = new JButton("保存");
		btnReset = new JButton("恢复默认设置");
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

	

	//获取当前新的键值对
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

	//将所有的文本输入框的至还原成默认设置
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
		JOptionPane.showMessageDialog(contentPane, "配置文件修改成功", "成功",JOptionPane.WARNING_MESSAGE);  
		dispose();

	}
	
	private void onFail(){
		JOptionPane.showMessageDialog(contentPane,  "配置文件修改失败","失败",JOptionPane.WARNING_MESSAGE);  
	}
}
