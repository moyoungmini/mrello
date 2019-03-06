package FinalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutput;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;

import ServerDB.DB;

public class LoginPlusFrame extends JFrame implements ActionListener{

	private ImageIcon mainLogo = new ImageIcon("LoginPlus.png");
	private ImageIcon createLogo = new ImageIcon("LoginCreate.png");
	// ���� ���̵� ���鶧 ���� â
	
	private JButton nextButton;
	// ID�� ���鶧 ���� ��ư
	
	private JTextField idField = new JTextField();
	private JTextField pwField = new JTextField();
	private JTextField repwField = new JTextField();
	// ID, PW1,PW2 �� ����Ҷ� ���� TextField
	
	private DB db;
	
	private JPanel Screen;
	
	public LoginPlusFrame() throws IOException, ClassNotFoundException, SQLException{
		db = new DB();
		
		Screen = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(mainLogo.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		Screen.setLayout(null);
		//��� �� ������ǥ�� ���� Screen ����
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550,410);
		setLocation(490,140);
		setTitle("Login");
		setResizable(false);
		setUndecorated(true);
		//�⺻���� ȭ�� ����
		
	
		
		createLogo.setImage(createLogo.getImage().getScaledInstance(165,70,java.awt.Image.SCALE_SMOOTH));
		nextButton = new JButton(createLogo);
		nextButton.setBackground(new Color(Color.TRANSLUCENT));
		nextButton.setOpaque(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setBorderPainted(false);
		nextButton.setBounds(345,300,165,70);
		
		idField.setBounds(140, 145, 150, 25);
		idField.setBackground(Color.CYAN);
		pwField.setBounds(140, 205, 150, 25);
		pwField.setBackground(Color.CYAN);
		repwField.setBounds(140, 270, 150, 25);
		repwField.setBackground(Color.CYAN);
		
		idField.setOpaque(false);
		idField.setBorder(null);
		pwField.setOpaque(false);
		pwField.setBorder(null);
		repwField.setOpaque(false);
		repwField.setBorder(null);
		//Button�� TextField�� ���ؼ� ������ �� ������ǥ ����
		
		nextButton.addActionListener(this);	// Button Event ó��
		
		Screen.add(idField);
		Screen.add(pwField);
		Screen.add(repwField);
		Screen.add(nextButton);
		add(Screen);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == nextButton){
			try {
				if(db.LoginPlus(idField.getText(), pwField.getText(), repwField.getText())){
					JOptionPane.showMessageDialog(null, "Successful");
					setVisible(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}//Next Button Event ó��
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException{
		LoginPlusFrame test = new LoginPlusFrame();
	}
}
