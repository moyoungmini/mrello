package FinalProject;

import java.util.Vector;
import java.util.logging.Handler;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

import ServerDB.DB;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RoomFrame extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
	public String RoomID;
	
	private ImageIcon mainLogo = new ImageIcon("MainLogoTest.png");
	private ImageIcon topicLogo = new ImageIcon("Share.png");
	private ImageIcon plusLogo = new ImageIcon("RoomPlus.png");
	private ImageIcon delLogo = new ImageIcon("RoomMinus.png");
	private ImageIcon upLoadLogo = new ImageIcon("upLoad.png");
	private ImageIcon downLoadLogo = new ImageIcon("download.png");
	private ImageIcon exitLogo = new ImageIcon("Exit.png");
	//ImageIcon about Button, MainLogo
	
	private JButton topicPlus;
	private JButton topicDelete;
	private JButton Exit;
	private JButton fileUpLoad;
	private JButton fileDownLoad;
	// JButton
	
	private JPanel Screen;
	//For BackGround
	
	private Vector <JButton> Topic = new Vector <JButton>();
	private Vector <TopicFrame> topicFrame = new Vector <TopicFrame>();
	//For TopicFrmae and Topic Button
	
	private Vector <String> TopicID = new Vector <String>();
	private Vector <String> TopicName = new Vector<String>();
	
	private boolean isDragged = false;
	private int offX;
	private int offY;
	//For Drag
	
	private DB db;
	public RoomFrame() throws ClassNotFoundException, SQLException{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	      Image img = toolkit.getImage("MImage.png");
	      setIconImage(img);
		//Toolkit 설정
	    
		db = new DB();
		Screen = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(mainLogo.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		Screen.setLayout(null);
		//Screen Jpanel set
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(570,450);
		setLocation(490,140);
		setTitle("Mrello");
		setResizable(false);
		//Frame establish fandamental set
	
		plusLogo.setImage(plusLogo.getImage().getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH));
		topicPlus = new JButton(plusLogo);
		topicPlus.setBackground(new Color(Color.TRANSLUCENT));
		topicPlus.setOpaque(false);
		topicPlus.setContentAreaFilled(false);
		topicPlus.setBorderPainted(false);
		topicPlus.setBounds(10,365,36,36);
		
		delLogo.setImage(delLogo.getImage().getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH));
		topicDelete = new JButton(delLogo);
		topicDelete.setBackground(new Color(Color.TRANSLUCENT));
		topicDelete.setOpaque(false);
		topicDelete.setContentAreaFilled(false);
		topicDelete.setBorderPainted(false);
		topicDelete.setBounds(120,365,36,36);
		
		exitLogo.setImage(exitLogo.getImage().getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH));
		Exit = new JButton(exitLogo);
		Exit.setBackground(new Color(Color.TRANSLUCENT));
		Exit.setOpaque(false);
		Exit.setContentAreaFilled(false);
		Exit.setBorderPainted(false);
		Exit.setBounds(480,365,36,36);
		
		upLoadLogo.setImage(upLoadLogo.getImage().getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH));
		fileUpLoad = new JButton(upLoadLogo);
		fileUpLoad.setBackground(new Color(Color.TRANSLUCENT));
		fileUpLoad.setOpaque(false);
		fileUpLoad.setContentAreaFilled(false);
		fileUpLoad.setBorderPainted(false);
		fileUpLoad.setBounds(230,365,36,36);
		
		downLoadLogo.setImage(downLoadLogo.getImage().getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH));
		fileDownLoad = new JButton(downLoadLogo);
		fileDownLoad.setBackground(new Color(Color.TRANSLUCENT));
		fileDownLoad.setOpaque(false);
		fileDownLoad.setContentAreaFilled(false);
		fileDownLoad.setBorderPainted(false);
		fileDownLoad.setBounds(340,365,36,36);
		//Fandamental JButton set
		
		topicPlus.addActionListener(this);
		topicDelete.addActionListener(this);
		Exit.addActionListener(this);
		fileUpLoad.addActionListener(this);
		fileDownLoad.addActionListener(this);
		// For Button Event
		
		Screen.add(topicPlus);
		Screen.add(topicDelete);
		Screen.add(Exit);
		Screen.add(fileDownLoad);
		Screen.add(fileUpLoad);
		// Each component add about screen(JPanel)
		
		add(Screen);
		setVisible(false);
	}
	
	public JFrame getFrame(){
		return this;
	}
	
	public void addTopic(String id, double x, double y, String name) throws ClassNotFoundException, SQLException{
		topicLogo.setImage(topicLogo.getImage().getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH));
		JButton newButton = new JButton(topicLogo);
		newButton.setBackground(new Color(Color.TRANSLUCENT));
		newButton.setOpaque(false);
		newButton.setContentAreaFilled(false);
		newButton.setBorderPainted(false);
		newButton.setToolTipText(id);
		newButton.setBounds((int) x,(int) y,36,36);
		
		Topic.addElement(newButton);
		Topic.elementAt(Topic.size()-1).addMouseListener(this);
		Topic.elementAt(Topic.size()-1).addMouseMotionListener(this);
		
		TopicFrame newRoomFrame = new TopicFrame();
		newRoomFrame.setTitle(name);
		topicFrame.addElement(newRoomFrame);
		
		TopicID.addElement(id);
		TopicName.addElement(name);
		this.Screen.add(newButton);
		this.repaint();
	}
	//for DB
	
		@Override
		public void actionPerformed(ActionEvent Event) {
			
			if(Event.getSource() == topicPlus){
				String topicName = JOptionPane.showInputDialog("topic name input");
				if(topicName!=null){
				topicLogo.setImage(topicLogo.getImage().getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH));
				JButton newButton = new JButton(topicLogo);
				newButton.setBackground(new Color(Color.TRANSLUCENT));
				newButton.setOpaque(false);
				newButton.setContentAreaFilled(false);
				newButton.setBorderPainted(false);
				newButton.setToolTipText(topicName);
				newButton.setBounds(0,0,36,36);
				Screen.repaint();
				//new Button set
				
				Topic.addElement(newButton);
				Screen.add(Topic.get(Topic.size()-1));
				//Screen Button plus
				
				TopicID.addElement(topicName);
				TopicName.addElement(topicName);
				
				TopicFrame newTopicFrame;
				try {
					newTopicFrame = new TopicFrame();
					newTopicFrame.setTitle(topicName);
					topicFrame.addElement(newTopicFrame);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//RoomFrame object make to use Vector
				
				Topic.elementAt(Topic.size()-1).addMouseListener(this);
				Topic.elementAt(Topic.size()-1).addMouseMotionListener(this);
				//Button Drag handler
				}
			}
			
			if(Event.getSource() == Exit){
				int select = JOptionPane.showConfirmDialog(null, "Do you really want to quit? ","Termination",
						  JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(select == 0){
					
					//저장
					for(int i=0 ;i< Topic.size();i++){
						int x = Topic.get(i).getX();
						int y = Topic.get(i).getY();
						String name = Topic.get(i).getText();
						String id = TopicID.get(i);
						String Name = TopicName.get(i);
						try {
							String sql;
							ResultSet rs = db.select("select count(*) from Topic where TopicID = '"+id+"';");
							rs.first();
							int a =rs.getInt("count(*)");
							if(a ==0){
								//값 없다
								db.insertQuery("insert into Topic values('"+id+"','"+(double) x+"','"+(double)y+"','"+Name+"');");
								db.insertQuery("insert into RTconnection values ('"+RoomID+"','"+TopicID.get(i)+"');");
							}
							else{
								db.updateQuery("update Topic set topicX = '"+x+"', topicY = '"+y+"' where TopicID = '"+id+"';");
								//값 있다.
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					dispose();
				}
			}
			//RoomFrame 종료할때  Topic과 RTconnection에 값 저장 및 업데이트 
			
			if(Event.getSource() == fileDownLoad){
				FileDialog Save = new FileDialog(getFrame(),"Save",FileDialog.SAVE);
				Save.setVisible(true);
			}
			if(Event.getSource() == fileUpLoad){
				FileDialog Open = new FileDialog(getFrame(),"Open", FileDialog.LOAD);
				Open.setVisible(true);
			}
			// 서버만 제대로 통신된다면 코드 작성 쉽게 가능
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getComponent().contains(new Point(e.getX(),e.getY()))){
				offX = e.getX() - e.getComponent().getX();
				offY = e.getY() - e.getComponent().getY();
			}
				isDragged = true;
		}
		//마우스를 눌렀을때
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			isDragged = false;
			
			if(((e.getComponent().getX()+50>=topicDelete.getX())&&(e.getComponent().getX()<=topicDelete.getX()))
			   &&((e.getComponent().getY()+50>=topicDelete.getY())&&(e.getComponent().getY()<=topicDelete.getY()))){
				int select = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?","Dlete",
											  JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(select == 0){
					for(int i = 0; i<Topic.size();i++){
						if(Topic.get(i)==e.getComponent()){
							Topic.get(i).setVisible(false);
							Topic.remove(i);
							topicFrame.remove(i);
							TopicID.remove(i);
							TopicName.remove(i);
							break;
						}
					}
				}
			}
			//Delete JButton, RoomFrame to use drag+ 좌표안에 들어올경우 삭제
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if(isDragged){
				e.getComponent().setBounds(e.getX()-offX, e.getY()-offY, 50, 50);
			}
		}
		//드래그를 위한 코드
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getClickCount()==2){
				for(int i=0; i<topicFrame.size(); i++){
					if(Topic.get(i)== e.getComponent()){
						db.TopicRead(TopicID.elementAt(i));
						
					}
				}
			}
		}
		//해당 객체를 더블 클릭 했을 경우 TopicFrame 창을 띄운다.
		
		@Override   
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	// MouseEvent about JButton
		
		public static void main(String args[]) throws ClassNotFoundException, SQLException{
			RoomFrame frame = new RoomFrame();
			frame.setVisible(true);
		}
}