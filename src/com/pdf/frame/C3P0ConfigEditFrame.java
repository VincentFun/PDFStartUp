package com.pdf.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.pdf.util.XMLHandler;

public class C3P0ConfigEditFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	//存放XML配置文件中原有的键值对
	private HashMap<String,String> map=new LinkedHashMap<>();
	//存放用户修改后的键值对
	private HashMap<String,String> updateMap=new HashMap<String,String>();
	
	
	
	//XML处理器，用于解析XML文档以及更新XML文档
	private XMLHandler xmlHandler;
	
	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtPassword;
	private JTextField txtIp;
	private JLabel lbDriverUrl;
	private JTextField txtDatabaseName;
	private JTextField txtPort;
	//保存按钮
	private JButton btnSave;
	
	private JButton btnReset;
	
	public C3P0ConfigEditFrame(XMLHandler xmlHandler,HashMap<String,String> map) {
		//super(filePath);
		
		this.xmlHandler=xmlHandler;
		this.map=map;
		init();
		
	}


	private void init() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 440, 220);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Global-config.xml\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("数据库用户名:");
		lblNewLabel.setBounds(69, 38, 84, 15);
		contentPane.add(lblNewLabel);
		
		txtUser = new JTextField();
		txtUser.setToolTipText("user");
		txtUser.setBounds(170, 37, 187, 21);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		JLabel label = new JLabel("数据库密码:");
		label.setBounds(69, 75, 72, 15);
		contentPane.add(label);
		
		txtPassword = new JTextField();
		txtPassword.setToolTipText("password");
		txtPassword.setColumns(10);
		txtPassword.setBounds(170, 71, 187, 21);
		contentPane.add(txtPassword);
		
		JLabel label_1 = new JLabel("数据库地址:");
		label_1.setBounds(10, 113, 75, 15);
		contentPane.add(label_1);
		
		txtIp = new JTextField();
		txtIp.setToolTipText("ip地址");
		txtIp.setText("123.223.233.233");
		txtIp.setColumns(10);
		txtIp.setBounds(198, 110, 95, 21);
		contentPane.add(txtIp);
		
		lbDriverUrl = new JLabel("jdbc:driver:oracle@");
		lbDriverUrl.setBounds(82, 113, 131, 15);
		contentPane.add(lbDriverUrl);
		
		txtDatabaseName = new JTextField();
		txtDatabaseName.setToolTipText("数据库名称");
		txtDatabaseName.setText("数据库名称");
		txtDatabaseName.setColumns(10);
		txtDatabaseName.setBounds(356, 110, 68, 21);
		contentPane.add(txtDatabaseName);
		
		txtPort = new JTextField();
		txtPort.setToolTipText("端口号");
		txtPort.setText("端口号");
		txtPort.setColumns(10);
		txtPort.setBounds(303, 110, 45, 21);
		contentPane.add(txtPort);
		
		btnSave = new JButton("保存当前设置");
		btnSave.setBounds(69, 141, 131, 30);
		contentPane.add(btnSave);
		
		btnReset=new JButton("恢复上次设置");
		btnReset.setBounds(247, 141, 131, 30);
		contentPane.add(btnReset);
		
		JLabel label_2 = new JLabel(":");
		label_2.setBounds(295, 113, 5, 15);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel(":");
		label_3.setBounds(349, 113, 5, 15);
		contentPane.add(label_3);
		
		
		setValue();
		btnSave.addActionListener(this);
		btnReset.addActionListener(this);
	}


	//解析的配置文件键值对的值存放在map中，将各个值显示在相应的控件上
	private void setValue() {
		txtUser.setText(map.get("user"));
		txtPassword.setText(map.get("password"));
		
		String jdbcUrl=map.get("jdbcUrl");
		
		String[] arr=jdbcUrl.split('@'+"");
		String driverUrl=arr[0]+"@";
		String databaseUrl=arr[1];
		String ip=databaseUrl.split(":")[0];
		String port=databaseUrl.split(":")[1];
		String databaseName=databaseUrl.split(":")[2];
		
		lbDriverUrl.setText(driverUrl);
		txtIp.setText(ip);
		txtPort.setText(port);
		txtDatabaseName.setText(databaseName);
		

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btnSave){
			getUpdateMap();
			
			if(xmlHandler.updateXmlFile(updateMap)){
				onSuccess();
			}else{
				onFail();
			}
		}else if(e.getSource()==btnReset){
			setValue();
		}
	}
	
	private void getUpdateMap(){
		updateMap.put("user", txtUser.getText());
		updateMap.put("password", txtPassword.getText());
		
		String jdbcUrl=lbDriverUrl.getText()+txtIp.getText()+":"+
		txtPort.getText()+":"+txtDatabaseName.getText();
		updateMap.put("jdbcUrl",jdbcUrl );
		
		System.out.println(jdbcUrl);
	}
	
	private void onSuccess(){
		JOptionPane.showMessageDialog(contentPane, "配置文件修改成功", "成功",JOptionPane.WARNING_MESSAGE);  
		
		dispose();

	}
	
	private  void onFail(){
		JOptionPane.showMessageDialog(contentPane,  "配置文件修改失败","失败",JOptionPane.WARNING_MESSAGE);  
	}
}
