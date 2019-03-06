package FinalProject;

import java.util.Vector;
//import java.util.logging.Handler;
//import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import ServerDB.DB;

public class HouseFrame extends JFrame implements Serializable, ActionListener, MouseListener, MouseMotionListener{
	public String HouseID; // House PrimaryKey
	
	private ImageIcon mainLogo = new ImageIcon("MainLogoTest.png");
	private ImageIcon plusLogo = new ImageIcon("AddRoom.png");
	private ImageIcon delLogo = new ImageIcon("MinusRoom.png");
	private ImageIcon exitLogo = new ImageIcon("Exit.png");
	private ImageIcon houseLogo = new ImageIcon("House.png");
	// 버튼 배경에 대한 ImageIcon
	
	private JButton roomPlus;
	private JButton roomDelete;
	private JButton Exit;
	// 하단에 배치할 버튼
	
	private JPanel Screen;
	// 배경을 위한 JPanel
	
	private Vector <JButton> Room = new Vector <JButton>();
	private Vector <RoomFrame> roomFrame = new Vector <RoomFrame>();
	private Vector <String> RoomID = new Vector <String>();
	private Vector <String> RoomPW = new Vector <String>();
	private Vector <String> RoomName = new Vector<String>();
	// Room에 대한 Vector
	
	private boolean isDragged = false;
	private int offX;
	private int offY;
	// Room 버튼들을 드래그하기 위한 절대좌표
	
	DB db; // DB를 사용하기 위한 변수
	
	public HouseFrame() throws Exception{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	      Image img = toolkit.getImage("MImage.png");
	      setIconImage(img);
		// Toolkit 설정
	      
		Screen = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(mainLogo.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		Screen.setLayout(null);
		// 배경과 절대좌표를 나타내기 위한 코드
		
		db = new DB();
		// DB 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(570,450);
		setLocation(490,140);
		setTitle("Mrello");
		setResizable(false);
		//프레임의 기본 설정
		
		plusLogo.setImage(plusLogo.getImage().getScaledInstance(70,70,java.awt.Image.SCALE_SMOOTH));
		roomPlus = new JButton(plusLogo);
		roomPlus.setBackground(new Color(Color.TRANSLUCENT));
		roomPlus.setOpaque(false);
		roomPlus.setContentAreaFilled(false);
		roomPlus.setBorderPainted(false);
		roomPlus.setBounds(25,345,70,70);
		
		delLogo.setImage(delLogo.getImage().getScaledInstance(70,70,java.awt.Image.SCALE_SMOOTH));
		roomDelete = new JButton(delLogo);
		roomDelete.setBackground(new Color(Color.TRANSLUCENT));
		roomDelete.setOpaque(false);
		roomDelete.setContentAreaFilled(false);
		roomDelete.setBorderPainted(false);
		roomDelete.setBounds(245,345,70,70);
		
		exitLogo.setImage(exitLogo.getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
		Exit = new JButton(exitLogo);
		Exit.setBackground(new Color(Color.TRANSLUCENT));
		Exit.setOpaque(false);
		Exit.setContentAreaFilled(false);
		Exit.setBorderPainted(false);
		Exit.setBounds(465,345,70,70);
		// 버튼의 디자인과 절대좌표를 위한 설정 
		
		roomPlus.addActionListener(this);
		roomDelete.addActionListener(this);
		Exit.addActionListener(this);
		// 하단 3개의 Event를 위한 Listener
		
		Screen.add(roomPlus);
		Screen.add(roomDelete);
		Screen.add(Exit);
		// Screen Panel에 버튼 3개 추가
		
		add(Screen);
		setVisible(true);
	}
	
	public void addRoom(String id,String pw, double x, double y, String name) throws ClassNotFoundException, SQLException{
		houseLogo.setImage(houseLogo.getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
		JButton newButton = new JButton(houseLogo);
		newButton.setBackground(new Color(Color.TRANSLUCENT));
		newButton.setOpaque(false);
		newButton.setContentAreaFilled(false);
		newButton.setBorderPainted(false);
		newButton.setToolTipText(id);
		newButton.setBounds((int) x,(int) y,50,50);
		// DB에서 정보를 불러 올때 좌표 설정 및 버튼에 대한 기본적인 설정
		
		Room.addElement(newButton);
		Room.elementAt(Room.size()-1).addMouseListener(this);
		Room.elementAt(Room.size()-1).addMouseMotionListener(this);
		// Room 버튼을 Vector에 추가
		
		RoomFrame newRoomFrame = new RoomFrame();
		newRoomFrame.setTitle(name);
		roomFrame.addElement(newRoomFrame);
		RoomID.addElement(id);
		RoomPW.addElement(pw);
		RoomName.addElement(name);
		//DB에서 정보를 불러올때 사용하기 위해 각각의 ID,PW, Name, Frame들을 Vector에 추가
		
		this.Screen.add(newButton);
		this.repaint();
	}
	
		@Override
		public void actionPerformed(ActionEvent Event) {
			
			if(Event.getSource()==roomPlus){

				Object[] options= {"Create","Join"};
					int select = JOptionPane.showOptionDialog(null, "Select","RoomSelect",
							  JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,
							  options,options[0]);
					
					String roomID = JOptionPane.showInputDialog("Room ID input");
					String roomPW = JOptionPane.showInputDialog("Room Password input");
					String roomName = null;
					// Room Object를 추가 시킬때 각각의 변수에 필요한 정보를 담는다.
					
					if(select != 0){
						try {
							if(db.LoginRoom(roomID, roomPW,HouseID)){
								roomName = JOptionPane.showInputDialog("Room name input");
							}
							else{
								JOptionPane.showMessageDialog(null, "Room ID,PW didn't exist ");
								return;
								// ID/PW가 정확히 입력 X
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//Join 버튼을 눌렀을 경우
					
					else{
						roomName = JOptionPane.showInputDialog("Room name input");
						try {
							if(db.RoomOverlap(roomID)){
								JOptionPane.showMessageDialog(null, "Room name Overlap");
								return;
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//Create 버튼 눌렀을 경우
			
				if(roomName!=null){
				houseLogo.setImage(houseLogo.getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
				JButton newButton = new JButton(houseLogo);
				newButton.setBackground(new Color(Color.TRANSLUCENT));
				newButton.setOpaque(false);
				newButton.setContentAreaFilled(false);
				newButton.setBorderPainted(false);
				newButton.setToolTipText(roomName);
				newButton.setBounds(0,0,50,50);
				Screen.repaint();
				//버튼에 대한 기본적인 설정과 Screen Panel에 추가
				
				Room.addElement(newButton);
				RoomID.addElement(roomID);
				RoomPW.addElement(roomPW);
				RoomName.addElement(roomName);
				Screen.add(Room.get(Room.size()-1));
				//추가시킨 버튼에대한 Vector 처리
				
				RoomFrame newRoomFrame;
				try {
					newRoomFrame = new RoomFrame();
					newRoomFrame.setTitle(roomName);
					newRoomFrame.RoomID = roomID;
					roomFrame.addElement(newRoomFrame);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//버튼을 추가시켰을때 당연히 Frame도  Vector 추가
				
				Room.elementAt(Room.size()-1).addMouseListener(this);
				Room.elementAt(Room.size()-1).addMouseMotionListener(this);
				//추가 시킨 버튼에게  Mouse Event를 위한 Listener 등록
				}
			}
			
			if(Event.getSource()==Exit){
				int select = JOptionPane.showConfirmDialog(null, "Do you really want to quit? ","Termination",
						  JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(select == 0){
					
					for(int i=0 ;i< Room.size();i++){
						int x = Room.get(i).getX();
						int y = Room.get(i).getY();
						String name = Room.get(i).getText();
						String id = RoomID.get(i);
						String pw = RoomPW.get(i);
						String Name = RoomName.get(i);
						
						//Vector에 추가시킨 정보를 DB에 저장할 변수
						try {
							String sql;
							ResultSet rs = db.select("select count(*) from Room where RoomID = '"+id+"';");// Room Table에서 해당 정보를 Select 해온다.
							rs.first();
							int a =rs.getInt("count(*)");
							if(a ==0){
								db.insertQuery("insert into room values('"+id+"','"+pw+"','"+(double) x+"','"+(double)y+"','"+Name+"');");//값이 없을 경우 Room Table에 정보 추가
								db.insertQuery("insert into HRconnection values ('"+HouseID+"','"+RoomID.get(i)+"');");// 값이 없을 경우 HRconnection Table에 해당 정보 추가
							}
							else{
								db.updateQuery("update room set roomX = '"+x+"', roomY = '"+y+"' where RoomID = '"+id+"';");// 값이 있는 경우 Room에서 해당 정보 Update
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					
					System.exit(0);
				}
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getComponent().contains(new Point(e.getX(),e.getY()))){
				offX = e.getX() - e.getComponent().getX();
				offY = e.getY() - e.getComponent().getY();
			}
				isDragged = true;
		}
		// Mouse가 눌렀을 offX,offY 값을 얻고 Drag상태를 true 상태로 바꾼다. offX 값은 마우스가 누른 Point - 처음 객체의 좌표를 말한다.
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			isDragged = false;
			
			if(((e.getComponent().getX()+50>=roomDelete.getX())&&(e.getComponent().getX()<=roomDelete.getX()))
			   &&((e.getComponent().getY()+50>=roomDelete.getY())&&(e.getComponent().getY()<=roomDelete.getY()))){
				int select = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?","Dlete",
											  JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(select == 0){
					for(int i = 0; i<Room.size();i++){
						if(Room.get(i)==e.getComponent()){
							String sql ="Delete from Room where roomID = '"+RoomID.get(i)+"';";
							db.deleteQuery(sql);
							sql = "Delete from HRconnection where roomID = '"+RoomID.get(i)+"'and HouseID = '"+HouseID+"';";//room house
							db.deleteQuery(sql);
							
							Room.get(i).setVisible(false);
							Room.remove(i);
							roomFrame.remove(i);
							RoomID.remove(i);
							RoomPW.remove(i);
							RoomName.remove(i);
							break;
						}
					}
				}
			}
			//Room Object를 삭제 할때 휴지통 버튼에 좌표 안에 들어갔을 경우 해당 정보 삭제
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if(isDragged){
				e.getComponent().setBounds(e.getX()-offX, e.getY()-offY, 50, 50);
			}
		}
		// Mouse를 Drag할때 그 순간마다 setBounds처리를하여 바뀌는 좌표값을 화면에 보여준다. 하지만 setBounds의 파라미터값의 변수가 int형이어서 버퍼링의 문제가 있다.
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getClickCount()==2){
				for(int i=0; i<Room.size(); i++){
					if(Room.get(i)== e.getComponent()){
						roomFrame.get(i).RoomID = RoomID.elementAt(i);
						try {
							db.readRoom(RoomID.elementAt(i));
						} catch (SQLException | ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		}
		// 클릭 2번했을경우 해당 Room 객체의 Frame을 연다.

		@Override   
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		public static void main(String args[]) throws Exception{
			HouseFrame test = new HouseFrame();
		}
}