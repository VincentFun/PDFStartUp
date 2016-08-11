package com.pdf.frame;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.pdf.config.Global;
import com.pdf.util.BrowerserStarter;
import com.pdf.util.ProcessStarter;
import com.pdf.util.PropertiesHandler;
import com.pdf.util.XMLHandler;

public class MainFrame extends JFrame implements ActionListener, FocusListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private JPanel contentPane;
	
	//���������ַ��Ĭ������
	HashMap<String,String> saveMap=new HashMap<>();
	
	//�������������ؼ�
	private JRadioButton rbLocalServer,rbServer;
	private JTextField txtTomcatAddress,txtServerAddress,txtPdfAddress;
	private JButton btnTomcatAddress,btnPdfAddress,btnStartServer;
	private JPanel panelLocal,panelServer;
	private JButton btnWebCp;
	
	
	//pdfResolver�������ؼ�
	private JTextField txtResolverAddress;
	private JButton btnResolverAddress;
	private JButton btnResolverCp;
	private JButton btnResolverGb;
	private JButton btnResolverLg;
	private JButton btnStartResolver;
	
	
	//XML2Intertable�������ؼ�
	private JTextField txtXmlAddress;

	private JButton btnXmlCp;

	private JButton btnXmlGb;

	private JButton btnXmlAddress;

	private JButton btnStartXml;
	
	//���ڼ�¼tomcat�Ƿ�ɹ����������û�������������������
	private boolean flag=false;
	
	//jar������
	ProcessStarter starter=new ProcessStarter(){
		@Override
		public void onSuccess(String message) {
			JOptionPane.showMessageDialog(contentPane,  message,"�ɹ�",JOptionPane.WARNING_MESSAGE);  
			flag=true;
		}

		@Override
		public void onFail(String message) {
			JOptionPane.showMessageDialog(contentPane,  message,"ʧ��",JOptionPane.WARNING_MESSAGE);  
			flag=false;
		}
	};

	private PropertiesHandler propHandler=new PropertiesHandler("save.properties");
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					frame.setResizable(false);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Pdf\u8868\u683C\u62BD\u53D6\u542F\u52A8\u7A0B\u5E8F");  
		//***********�ⲿ��������gridBagLayout����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{424, 0};
		gbl_contentPane.rowHeights = new int[]{140, 130, 130, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setLocation(new Point((int) (lx / 2) -450/2, (int) (ly / 2) - 500/2));// �趨���ڳ���λ��
		
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				save();
			}
			
		});
		//**************
		
		//***********�������������
		JPanel serverConfigPanel = new JPanel();
		serverConfigPanel.setBorder(new TitledBorder(null, "\u670D\u52A1\u5668\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		GridBagConstraints gbc_serverConfigPanel = new GridBagConstraints();
		gbc_serverConfigPanel.fill = GridBagConstraints.BOTH;
		gbc_serverConfigPanel.insets = new Insets(0, 0, 5, 0);
		gbc_serverConfigPanel.gridx = 0;
		gbc_serverConfigPanel.gridy = 0;
		contentPane.add(serverConfigPanel, gbc_serverConfigPanel);
		serverConfigPanel.setLayout(null);
		
		
		panelLocal = new JPanel();
		panelLocal.setBounds(5, 18, 400, 81);
		serverConfigPanel.add(panelLocal);
		panelLocal.setLayout(null);
		
		rbLocalServer = new JRadioButton("\u672C\u673A");
		rbLocalServer.setBounds(0, 0, 58, 23);
		panelLocal.add(rbLocalServer);
		
		
		txtTomcatAddress = new JTextField();
		txtTomcatAddress.setToolTipText("TomcatAddress");
		txtTomcatAddress.setBounds(88, 1, 229, 21);
		panelLocal.add(txtTomcatAddress);
		txtTomcatAddress.setText("\u8BF7\u9009\u62E9\u672C\u5730tomcat\u5730\u5740");
		txtTomcatAddress.setColumns(10);
		
		btnTomcatAddress = new JButton("...");
		btnTomcatAddress.setBounds(327, 0, 70, 23);
		panelLocal.add(btnTomcatAddress);
		
		txtPdfAddress = new JTextField();
		txtPdfAddress.setToolTipText("PdfAddress");
		txtPdfAddress.setText("\u8BF7\u9009\u62E9pdf\u5B58\u653E\u8DEF\u5F84");
		txtPdfAddress.setBounds(88, 29, 229, 21);
		panelLocal.add(txtPdfAddress);
		txtPdfAddress.setColumns(10);
		
		btnPdfAddress = new JButton("...");
		btnPdfAddress.setBounds(327, 28, 70, 23);
		panelLocal.add(btnPdfAddress);
		
		panelServer = new JPanel();
		panelServer.setBounds(5, 109, 400, 25);
		serverConfigPanel.add(panelServer);
		panelServer.setLayout(null);
		
		txtServerAddress = new JTextField();
		txtServerAddress.setToolTipText("ServerAddress");
		txtServerAddress.setBounds(87, 0, 307, 21);
		panelServer.add(txtServerAddress);
		txtServerAddress.setText("\u8BF7\u8F93\u5165\u670D\u52A1\u5668\u5730\u5740");
		txtServerAddress.setColumns(10);
		
		rbServer = new JRadioButton("\u670D\u52A1\u5668");
		rbServer.setBounds(0, -1, 70, 23);
		panelServer.add(rbServer);
		
		btnStartServer = new JButton("\u542F\u52A8\u6D4F\u89C8\u5668");
		btnStartServer.setBounds(142, 144, 156, 29);
		serverConfigPanel.add(btnStartServer);
		
		//�������������ѡ��ť�����ButtonGroup,Ĭ��ѡ�񱾵ط�����
		ButtonGroup serverRadioGroup=new ButtonGroup();
		serverRadioGroup.add(rbServer);
		serverRadioGroup.add(rbLocalServer);
		rbLocalServer.setSelected(true);
		toggleServer();
		
		btnWebCp = new JButton("c3p0-config");
		btnWebCp.setBounds(231, 56, 166, 23);
		panelLocal.add(btnWebCp);
		
		JLabel lblWeb = new JLabel("Web\u7A0B\u5E8F\u914D\u7F6E\u6587\u6863\u7F16\u8F91");
		lblWeb.setBounds(88, 60, 133, 15);
		panelLocal.add(lblWeb);
		

		//�󶨼����¼�
		rbLocalServer.addActionListener(this);
		rbServer.addActionListener(this);
		btnTomcatAddress.addActionListener(this);
		btnPdfAddress.addActionListener(this);
		
		btnStartServer.addActionListener(this);
		btnWebCp.addActionListener(this);
		txtServerAddress.addFocusListener(this);
		txtPdfAddress.addFocusListener(this);
		txtTomcatAddress.addFocusListener(this);
		//********************
		
		
		//pdfResolver�������***********
		JPanel pdfResolvePanel = new JPanel();
		pdfResolvePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "PdfResolve\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_pdfResolvePanel = new GridBagConstraints();
		gbc_pdfResolvePanel.fill = GridBagConstraints.BOTH;
		gbc_pdfResolvePanel.insets = new Insets(0, 0, 5, 0);
		gbc_pdfResolvePanel.gridx = 0;
		gbc_pdfResolvePanel.gridy = 1;
		contentPane.add(pdfResolvePanel, gbc_pdfResolvePanel);
		pdfResolvePanel.setLayout(null);
		
		JLabel lblpdfresolve = new JLabel("pdfResolve\u8DEF\u5F84");
		lblpdfresolve.setBounds(10, 25, 91, 15);
		pdfResolvePanel.add(lblpdfresolve);
		
		txtResolverAddress = new JTextField();
		txtResolverAddress.setToolTipText("PdfResolveAddress");
		txtResolverAddress.setBounds(105, 20, 218, 21);
		pdfResolvePanel.add(txtResolverAddress);
		txtResolverAddress.setColumns(10);
		
		btnResolverAddress = new JButton("...");
		btnResolverAddress.setBounds(333, 19, 70, 23);
		pdfResolvePanel.add(btnResolverAddress);
		
		JLabel lblPdfresolve = new JLabel("\u914D\u7F6E\u6587\u6863\u7F16\u8F91");
		lblPdfresolve.setBounds(10, 54, 98, 15);
		pdfResolvePanel.add(lblPdfresolve);
		
		btnResolverCp = new JButton("c3po-config");
		btnResolverCp.setBounds(105, 51, 105, 23);
		pdfResolvePanel.add(btnResolverCp);
		
		btnResolverGb = new JButton("global-config");
		btnResolverGb.setBounds(215, 51, 114, 23);
		pdfResolvePanel.add(btnResolverGb);
		
		btnResolverLg = new JButton("log4j");
		btnResolverLg.setBounds(333, 51, 70, 23);
		pdfResolvePanel.add(btnResolverLg);
		
		btnStartResolver = new JButton("\u542F\u52A8pdfResolve\u7A0B\u5E8F");
		btnStartResolver.setBounds(141, 83, 156, 29);
		pdfResolvePanel.add(btnStartResolver);
		
		
		btnResolverAddress.addActionListener(this);
		btnResolverCp.addActionListener(this);
		btnResolverGb.addActionListener(this);
		btnResolverLg.addActionListener(this);
		btnStartResolver.addActionListener(this);
		//**********************
		
		
		//XML2Intertable�������
		JPanel XML2InterPanel = new JPanel();
		XML2InterPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "XML2Intertable\u914D\u7F6E", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_XML2InterPanel = new GridBagConstraints();
		gbc_XML2InterPanel.fill = GridBagConstraints.BOTH;
		gbc_XML2InterPanel.gridx = 0;
		gbc_XML2InterPanel.gridy = 2;
		contentPane.add(XML2InterPanel, gbc_XML2InterPanel);
		XML2InterPanel.setLayout(null);
		
		JLabel lblXmlintertable_1 = new JLabel("\u914D\u7F6E\u6587\u6863\u7F16\u8F91");
		lblXmlintertable_1.setBounds(10, 56, 135, 15);
		XML2InterPanel.add(lblXmlintertable_1);
		
		btnXmlCp = new JButton("c3po-config");
		btnXmlCp.setBounds(125, 52, 135, 23);
		XML2InterPanel.add(btnXmlCp);
		
		btnXmlGb = new JButton("global-config");
		btnXmlGb.setBounds(268, 52, 135, 23);
		XML2InterPanel.add(btnXmlGb);
		
		JLabel lblXmlintertable = new JLabel("XML2Intertable\u8DEF\u5F84");
		lblXmlintertable.setBounds(10, 27, 162, 15);
		XML2InterPanel.add(lblXmlintertable);
		
		txtXmlAddress = new JTextField();
		txtXmlAddress.setToolTipText("Xml2IntertableAddress");
		txtXmlAddress.setBounds(125, 24, 198, 21);
		XML2InterPanel.add(txtXmlAddress);
		txtXmlAddress.setColumns(10);
		
		btnXmlAddress = new JButton("...");
		btnXmlAddress.setBounds(333, 23, 70, 23);
		XML2InterPanel.add(btnXmlAddress);
		
		btnStartXml = new JButton("\u542F\u52A8XML2Intertable");
		btnStartXml.setBounds(141, 85, 156, 29);
		XML2InterPanel.add(btnStartXml);
		
		btnXmlAddress.addActionListener(this);
		btnXmlCp.addActionListener(this);
		btnXmlGb.addActionListener(this);
		btnStartXml.addActionListener(this);
		//***************
		
		//����Ĭ�������ļ�
		
		load();

		

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//����ťΪ������ѡ���еĵ�ѡ��ťʱ
		if(e.getSource()==rbLocalServer||e.getSource()==rbServer){
			toggleServer();//���ݵ�ѡ��ť��ǰѡ��״̬���ö�Ӧ�Ŀؼ���ѡ״̬
		}else if(e.getSource()==btnTomcatAddress){
			openFileChooser(txtTomcatAddress,JFileChooser.DIRECTORIES_ONLY);
		}else if(e.getSource()==btnPdfAddress){
			openFileChooser(txtPdfAddress,JFileChooser.DIRECTORIES_ONLY);
		}else if(e.getSource()==btnResolverAddress){
			openFileChooser(txtResolverAddress, JFileChooser.DIRECTORIES_ONLY);
		}else if(e.getSource()==btnXmlAddress){
			openFileChooser(txtXmlAddress, JFileChooser.DIRECTORIES_ONLY);
		}else if(e.getSource()==btnWebCp){
			//String filePath=txtWebAddress.getText().trim()+"/";
			String filePath=txtTomcatAddress.getText()+"/"+"webapps/"+Global.PROJECT_PATH+"/WEB-INF/classes/"+Global.C3P0_FILE_NAME;
			openEditWindow(filePath);
		}else if(e.getSource()==btnResolverCp){
			String filePath=txtResolverAddress.getText().trim()+"/"+Global.C3P0_FILE_NAME;
			//String filePath="d:/test.xml";
			openEditWindow(filePath);
		}else if(e.getSource()==btnResolverGb){
			String filePath=txtResolverAddress.getText().trim()+"/"+Global.GLOBAL_FILE_NAME;
			openEditWindow(filePath);
		}else if(e.getSource()==btnResolverLg){
			String filePath=txtResolverAddress.getText().trim()+"/"+Global.LOG_FILE_NAME;
			openEditWindow(filePath);
		}else if(e.getSource()==btnXmlCp){
			String filePath=txtXmlAddress.getText().trim()+"/"+Global.C3P0_FILE_NAME;
			openEditWindow(filePath);
		}else if(e.getSource()==btnXmlGb){
			String filePath=txtXmlAddress.getText().trim()+"/"+Global.GLOBAL_FILE_NAME;
			openEditWindow(filePath);
		}else if(e.getSource()==btnStartResolver){
			//String filePath=txtResolverAddress.getText()+"/"+"pdfResolver.jar";
			//���web���ڷ������ϲ��ԣ���pdfResolve�����ļ���tomcatHost�޸�Ϊ��������ַ
			String host;
			if(rbServer.isSelected()){
				host=txtServerAddress.getText().trim();
			}else{
				host="127.0.0.1";
			}
			updateGlobalConfig(txtResolverAddress.getText().trim()+"/"+Global.GLOBAL_FILE_NAME,host);
			String filePath=txtResolverAddress.getText()+"\\"+Global.PDF_RESOLVE_NAME+".bat";
			starter.setFilePath(filePath);
			starter.start();
		}else if(e.getSource()==btnStartServer){
			if(rbLocalServer.isSelected()){//������ñ��ط�����
				//1.����tomcat
				String tomcatPath=txtTomcatAddress.getText()+"/bin/startup.bat";
				starter.setFilePath(tomcatPath);
				starter.start();
				//2.���������
				if(flag){
					String urlPath="http://localhost:8080/"+Global.PROJECT_PATH;
					BrowerserStarter bs=new BrowerserStarter(urlPath);
					bs.start();
				}
			}else if(rbServer.isSelected()){//���ʹ�÷�����
				String urlPath="http://"+txtServerAddress.getText()+":8080/"+Global.PROJECT_PATH;
				System.out.println(urlPath);
				BrowerserStarter bs=new BrowerserStarter(urlPath);
				bs.start();
			}
			
		}else if(e.getSource()==btnStartXml){
			String host;
			if(rbServer.isSelected()){
				host=txtServerAddress.getText().trim();
			}else{
				host="127.0.0.1";
			}
			updateGlobalConfig(txtXmlAddress.getText().trim()+"/"+Global.GLOBAL_FILE_NAME,host);
			String filePath=txtXmlAddress.getText()+"\\"+Global.XML_TO_INTERTABLE_NAME+".bat";
			starter.setFilePath(filePath);
			starter.start();
		}
	}
	

	@Override
	public void focusGained(FocusEvent e) {
		if(e.getSource()==txtServerAddress){
			if(txtServerAddress.getText().trim().equals("�������������ַ")){
				txtServerAddress.setText("");
			}
		}
//		}else if(e.getSource()==txtPdfAddress){
//			if(txtServerAddress.getText().trim().equals("��ѡ��pdf���·��")){
//				txtServerAddress.setText("");
//			}
//		}else if(e.getSource()==txtTomcatAddress){
//			if(txtServerAddress.getText().trim().equals("��ѡ�񱾵�tomcat��ַ")){
//				txtServerAddress.setText("");
//			}
//		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if(e.getSource()==txtServerAddress){
			if(txtServerAddress.getText().equals("")){
				txtServerAddress.setText("�������������ַ");
			}
		}else if(e.getSource()==txtPdfAddress){
			if(txtPdfAddress.getText().equals("")){
				txtPdfAddress.setText("��ѡ��pdf���·��");
			}
		}else if(e.getSource()==txtTomcatAddress){
			if(txtTomcatAddress.getText().equals("")){
				txtTomcatAddress.setText("��ѡ�񱾵�tomcat��ַ");
			}
		}
	}

	//�������������л��ؼ��Ƿ��ѡ״̬
	private void toggleServer(){
		if(rbLocalServer.isSelected()){
			setActive(panelLocal);
			setUnabled(panelServer);
		}else if(rbServer.isSelected()){
			setUnabled(panelLocal);
			setActive(panelServer);	
		}
	}

	//����panel�����пؼ�����ѡ
	private void setUnabled(JPanel panel) {
		for(int i=0;i<panel.getComponentCount();i++){
			Component comp=panel.getComponent(i);
			if(comp instanceof JRadioButton){
				continue;
			}
			comp.setEnabled(false);
		}
	}

	//����panel�����пؼ���ѡ
	private void setActive(JPanel panel) {
		for(int i=0;i<panel.getComponentCount();i++){
			Component comp=panel.getComponent(i);
			comp.setEnabled(true);
		}
	}
	
	//���ļ�ѡ�����������ļ�·����ʾ��textField�ؼ��Լ��ļ���ʾ��mode
	private void openFileChooser(JTextField textField, int fileChooserMode) {
		JFileChooser jfc = new JFileChooser();// �ļ�ѡ����
		jfc.setCurrentDirectory(new File("d://"));// �ļ�ѡ�����ĳ�ʼĿ¼��Ϊd��
		jfc.setFileSelectionMode(fileChooserMode);// �趨�ļ�ѡ��ģʽ
		int state = jfc.showOpenDialog(null);// �˾��Ǵ��ļ�ѡ��������Ĵ������
		if (state == 1) {
			return;// �����򷵻�
		} else {
			File f = jfc.getSelectedFile();// fΪѡ�񵽵��ļ�
			textField.setText(f.getAbsolutePath());
		}
	}

	//���µı༭����
	private void openEditWindow(String filePath) {
		File file=new File(filePath);
		if(file.exists()){
			new ConfigureEditFrame(filePath);
			//new C3P0Config(filePath).setVisible(true);
			
		}else{
			JOptionPane.showMessageDialog(contentPane,  "�޷��ҵ������ļ��������ļ�·��","��ʧ��",JOptionPane.WARNING_MESSAGE);  
		}
		
	}
	
	//�رմ���ʱ���浱ǰ����
	private void save(){
		
		saveText(txtTomcatAddress);
		saveText(txtPdfAddress);
		saveText(txtServerAddress);
		saveText(txtResolverAddress);
		saveText(txtXmlAddress);
		
	}

	//�򿪴��ڵ��� ֮ǰ����
	private void load(){
		loadText(txtTomcatAddress);
		loadText(txtPdfAddress);
		loadText(txtServerAddress);
		loadText(txtResolverAddress);
		loadText(txtXmlAddress);
	}
	
	
	private void loadText(JTextField textField) {
		String key=textField.getToolTipText();
		
		if(key!=null&&!key.equals("")){
			String value=propHandler.getProperty(key);
			textField.setText(value);
		}
	}

	private void saveText(JTextField textField) {
		if(!textField.getText().equals("")){
			saveMap.put(textField.getToolTipText(), textField.getText());
			//System.out.println(textField.getToolTipText()+":"+textField.getText());
			
			propHandler.save(saveMap);
		}
	}
	
	
	//���web���ǲ��𵽷������ϵģ�������pdfResolve��XML2intertable����ʱ�Զ�������Global-config
	//�ļ��е�tomcatHost��ַ
	private void updateGlobalConfig(String filePath,String host){
		
		XMLHandler handler=new XMLHandler(filePath);
		
		//pdfResolve��XML2Intertable��XML��ʽ��һ��.
		//pdfResolve�ĸ�ʽ�� <entry key="tomcatHost">192.168.51.251</entry>
		//XML2Intertabl�ĸ�ʽ�� <tomcatHost>192.168.51.251</tomcatHost>  
		
		if(filePath.contains(Global.PDF_RESOLVE_NAME)){
			handler.setAttributeName("key");
		}
		handler.parseToMap();
		
		HashMap<String,String> updateMap=new HashMap<>();
		updateMap.put("tomcatHost", host);
		handler.updateXmlFile(updateMap);
		
	}
}
