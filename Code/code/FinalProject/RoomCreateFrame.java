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
//
//public class RoomCreateFrame extends JFrame implements ActionListener {
//	private JPanel BackGround;
//	
//	private ImageIcon ImageLogo  = new ImageIcon("RoomCreateScreen.png");
//	private ImageIcon ImageCreate = new ImageIcon("RoomCreate.png");
//	private ImageIcon ImageJoin = new ImageIcon("RoomJoin.png");
//	
//	private JButton Create;
//	private JButton Join;
//	
//	public boolean CreateSelect = false;
//
//	public static boolean select;
//	
//	public RoomCreateFrame(){
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setSize(380,280);
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
//				ImageLogo.setImage(ImageLogo.getImage().getScaledInstance(380,280,java.awt.Image.SCALE_SMOOTH));
//				g.drawImage(ImageLogo.getImage(), 0, 0, null);
//				setOpaque(false);
//			}
//		};
//		
//		BackGround.setLayout(null);
//		// 배경과 절대좌표를 나타내기 위한 코드
//		
//		ImageCreate.setImage(ImageCreate.getImage().getScaledInstance(120,80,java.awt.Image.SCALE_SMOOTH));
//		Create = new JButton(ImageCreate);
//		Create.setBackground(new Color(Color.TRANSLUCENT));
//		Create.setOpaque(false);
//		Create.setContentAreaFilled(false);
//		Create.setBorderPainted(false);
//		Create.setBounds(60,105,120,80);
//		
//		ImageJoin.setImage(ImageJoin.getImage().getScaledInstance(120,80,java.awt.Image.SCALE_SMOOTH));
//		Join = new JButton(ImageJoin);
//		Join.setBackground(new Color(Color.TRANSLUCENT));
//		Join.setOpaque(false);
//		Join.setContentAreaFilled(false);
//		Join.setBorderPainted(false);
//		Join.setBounds(215,105,120,80);
//		
//		BackGround.add(Create);
//		BackGround.add(Join);
//		
//		
//		add(BackGround);
//	}
//	
//	public static void main(String args[]){
//		RoomCreateFrame test = new RoomCreateFrame();
//		test.setVisible(true);
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == Create){
//			CreateSelect = true;
//			select = false;
//			setVisible(false);
//		}
//		
//		if(e.getSource() == Join){
//			CreateSelect = false;
//			select = false;
//			setVisible(false);
//		}
//	}
//}
