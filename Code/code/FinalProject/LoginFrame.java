package FinalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
	
import javax.imageio.ImageIO;
import javax.swing.*;

import ServerDB.DB;

public class LoginFrame extends JFrame implements ActionListener{

	private ImageIcon mainLogo = new ImageIcon("Login.png");
	private ImageIcon loginLogo = new ImageIcon("LoginMenu.png");
	private ImageIcon newLogo = new ImageIcon("LoginNew.png");
	// 배경, 로그인, 회원가입 버튼에 대한 배경 설정
	
	private JButton plusButton = new JButton();
	private JButton nextButton = new JButton();
	// 로그인, 외원가입에 대한 버튼 처리
	
	private JTextField idField = new JTextField();
	private JTextField pwField = new JTextField();
	// ID, PW 입력 하는 곳
	
	private JPanel Screen; //배경설정
	
	private DB db;
	
	public LoginFrame() throws IOException, ClassNotFoundException, SQLException{
		db = new DB();
		
		Screen = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(mainLogo.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		Screen.setLayout(null);
		//배경을 위한 Panel
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550,420);
		setLocation(490,140);
		setTitle("Login");
		setResizable(false);
		setUndecorated(true);
		//기본적인 화면 설정
		
		newLogo.setImage(newLogo.getImage().getScaledInstance(165,70,java.awt.Image.SCALE_SMOOTH));
		plusButton = new JButton(newLogo);
		plusButton.setBackground(new Color(Color.TRANSLUCENT));
		plusButton.setOpaque(false);
		plusButton.setContentAreaFilled(false);
		plusButton.setBorderPainted(false);
		plusButton.setBounds(90, 285, 165, 50);
		
		loginLogo.setImage(loginLogo.getImage().getScaledInstance(165,70,java.awt.Image.SCALE_SMOOTH));
		nextButton = new JButton(loginLogo);
		nextButton.setBackground(new Color(Color.TRANSLUCENT));
		nextButton.setOpaque(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setBorderPainted(false);
		nextButton.setBounds(320, 285, 165, 50);
		
		idField.setBounds(145, 140, 150, 25);
		idField.setOpaque(false);
		idField.setBorder(null);

		pwField.setBounds(145, 205, 150, 25);
		pwField.setOpaque(false);
		pwField.setBorder(null);
		//Button textField에 대한 디자인, 절대 좌표 설정
		
		plusButton.addActionListener(this);
		nextButton.addActionListener(this);
		// 회원가입 로그인데 대한 Event 함수 추가
		
		Screen.add(plusButton);
		Screen.add(nextButton);
		Screen.add(idField);
		Screen.add(pwField);
		add(Screen);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == plusButton){
			try {
				try {
					LoginPlusFrame plus = new LoginPlusFrame(); // LoingPlusFrame 실행
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// plus Button 을 눌렀을 경우 회원가입 창을 띄운다.
		
		if(event.getSource() == nextButton){
			
			try {
				if(db.Login(idField.getText(), pwField.getText())){
					dispose();
					setVisible(false);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 로그인에 대한 처리 이벤트
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException{
		LoginFrame test = new LoginFrame();
	}
}
