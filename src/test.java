import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;

public class test extends JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtPassword;
	private JTextField txtIp;
	private JLabel lbDriverUrl;
	private JTextField txtDatabaseName;
	private JTextField txtPort;
	private JButton button;
	private JLabel label_2;
	private JLabel label_3;
	private JButton button_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test frame = new test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public test() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JLabel label_1 = new JLabel("数据库地址：");
		label_1.setBounds(10, 113, 75, 15);
		contentPane.add(label_1);
		
		txtIp = new JTextField();
		txtIp.setToolTipText("ip");
		txtIp.setText("123.223.233.233");
		txtIp.setColumns(10);
		txtIp.setBounds(198, 110, 95, 21);
		contentPane.add(txtIp);
		
		lbDriverUrl = new JLabel("jdbc:driver:oracle@");
		lbDriverUrl.setBounds(82, 113, 131, 15);
		contentPane.add(lbDriverUrl);
		
		txtDatabaseName = new JTextField();
		txtDatabaseName.setToolTipText("databaseName");
		txtDatabaseName.setText("数据库名称");
		txtDatabaseName.setColumns(10);
		txtDatabaseName.setBounds(356, 110, 68, 21);
		contentPane.add(txtDatabaseName);
		
		txtPort = new JTextField();
		txtPort.setToolTipText("port");
		txtPort.setText("端口号");
		txtPort.setColumns(10);
		txtPort.setBounds(303, 110, 45, 21);
		contentPane.add(txtPort);
		
		button = new JButton("保存");
		button.setBounds(69, 141, 131, 30);
		contentPane.add(button);
		
		label_2 = new JLabel("：");
		label_2.setBounds(295, 113, 12, 15);
		contentPane.add(label_2);
		
		label_3 = new JLabel("：");
		label_3.setBounds(349, 113, 12, 15);
		contentPane.add(label_3);
		
		button_1 = new JButton("\u6062\u590D\u4E0A\u6B21\u8BBE\u7F6E");
		button_1.setBounds(247, 141, 131, 30);
		contentPane.add(button_1);
	}
}
