//package FinalProject;
//
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//
//public class RoomCreateOption extends JFrame implements ActionListener {
//	private JPanel BackGround;
//	
//	private ImageIcon ImageLogo  = new ImageIcon("RoomCreateGUI.png");
//	private ImageIcon ImageCreate = new ImageIcon("RoomCreate.png");
//	
//	private JButton Create;
//	
//	public boolean CreateSelect = false;
//	
//	private JTextField idField = new JTextField();
//	private JTextField pwField = new JTextField();
//	private JTextField guiField = new JTextField();
//	
//	public RoomCreateOption(){
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setSize(480,380);
//		setLocation(500,220);
//		setResizable(false);
//		setUndecorated(true);
//		
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//	      Image img = toolkit.getImage("MImage.png");
//	      setIconImage(img);
//		// Toolkit 설정
//	      
//		BackGround = new JPanel(){
//			public void paintComponent(Graphics g){
//				ImageLogo.setImage(ImageLogo.getImage().getScaledInstance(500,400,java.awt.Image.SCALE_SMOOTH));
//				g.drawImage(ImageLogo.getImage(), 0, 0, null);
//				setOpaque(false);
//			}
//		};
//		
//		BackGround.setLayout(null);
//		// 배경과 절대좌표를 나타내기 위한 코드
//		
//		ImageCreate.setImage(ImageCreate.getImage().getScaledInstance(60,40,java.awt.Image.SCALE_SMOOTH));
//		Create = new JButton(ImageCreate);
//		Create.setBackground(new Color(Color.TRANSLUCENT));
//		Create.setOpaque(false);
//		Create.setContentAreaFilled(false);
//		Create.setBorderPainted(false);
//		Create.setBounds(350,305,60,40);
//		
//		idField.setBounds(125, 128, 150, 45);
//		idField.setOpaque(false);
//		idField.setBorder(null);
//		pwField.setBounds(125,188, 150, 45);
//		pwField.setOpaque(false);
//		pwField.setBorder(null);
//		guiField.setBounds(125,250, 150, 45);
//		guiField.setOpaque(false);
//		guiField.setBorder(null);
//		
//		
//		BackGround.add(idField);
//		BackGround.add(pwField);
//		BackGround.add(guiField);
//		BackGround.add(Create);
//		
//		
//		add(BackGround);
//	}
//	
//	public String getID(){
//		return idField.getText();
//	}
//	
//	public String getPW(){
//		return pwField.getText();
//	}
//	
//	public String getGUIID(){
//		return guiField.getText();
//	}
//	
//	public static void main(String args[]){
//		RoomCreateOption test = new RoomCreateOption();
//		test.setVisible(true);
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == Create){
//			CreateSelect = true;
//			setVisible(false);
//		}
//	}
//}
