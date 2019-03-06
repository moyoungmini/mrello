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
	// ��ư ��濡 ���� ImageIcon
	
	private JButton roomPlus;
	private JButton roomDelete;
	private JButton Exit;
	// �ϴܿ� ��ġ�� ��ư
	
	private JPanel Screen;
	// ����� ���� JPanel
	
	private Vector <JButton> Room = new Vector <JButton>();
	private Vector <RoomFrame> roomFrame = new Vector <RoomFrame>();
	private Vector <String> RoomID = new Vector <String>();
	private Vector <String> RoomPW = new Vector <String>();
	private Vector <String> RoomName = new Vector<String>();
	// Room�� ���� Vector
	
	private boolean isDragged = false;
	private int offX;
	private int offY;
	// Room ��ư���� �巡���ϱ� ���� ������ǥ
	
	DB db; // DB�� ����ϱ� ���� ����
	
	public HouseFrame() throws Exception{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	      Image img = toolkit.getImage("MImage.png");
	      setIconImage(img);
		// Toolkit ����
	      
		Screen = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(mainLogo.getImage(), 0, 0, null);
				setOpaque(false);
			}
		};
		Screen.setLayout(null);
		// ���� ������ǥ�� ��Ÿ���� ���� �ڵ�
		
		db = new DB();
		// DB ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(570,450);
		setLocation(490,140);
		setTitle("Mrello");
		setResizable(false);
		//�������� �⺻ ����
		
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
		// ��ư�� �����ΰ� ������ǥ�� ���� ���� 
		
		roomPlus.addActionListener(this);
		roomDelete.addActionListener(this);
		Exit.addActionListener(this);
		// �ϴ� 3���� Event�� ���� Listener
		
		Screen.add(roomPlus);
		Screen.add(roomDelete);
		Screen.add(Exit);
		// Screen Panel�� ��ư 3�� �߰�
		
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
		// DB���� ������ �ҷ� �ö� ��ǥ ���� �� ��ư�� ���� �⺻���� ����
		
		Room.addElement(newButton);
		Room.elementAt(Room.size()-1).addMouseListener(this);
		Room.elementAt(Room.size()-1).addMouseMotionListener(this);
		// Room ��ư�� Vector�� �߰�
		
		RoomFrame newRoomFrame = new RoomFrame();
		newRoomFrame.setTitle(name);
		roomFrame.addElement(newRoomFrame);
		RoomID.addElement(id);
		RoomPW.addElement(pw);
		RoomName.addElement(name);
		//DB���� ������ �ҷ��ö� ����ϱ� ���� ������ ID,PW, Name, Frame���� Vector�� �߰�
		
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
					// Room Object�� �߰� ��ų�� ������ ������ �ʿ��� ������ ��´�.
					
					if(select != 0){
						try {
							if(db.LoginRoom(roomID, roomPW,HouseID)){
								roomName = JOptionPane.showInputDialog("Room name input");
							}
							else{
								JOptionPane.showMessageDialog(null, "Room ID,PW didn't exist ");
								return;
								// ID/PW�� ��Ȯ�� �Է� X
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					//Join ��ư�� ������ ���
					
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
					//Create ��ư ������ ���
			
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
				//��ư�� ���� �⺻���� ������ Screen Panel�� �߰�
				
				Room.addElement(newButton);
				RoomID.addElement(roomID);
				RoomPW.addElement(roomPW);
				RoomName.addElement(roomName);
				Screen.add(Room.get(Room.size()-1));
				//�߰���Ų ��ư������ Vector ó��
				
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
				//��ư�� �߰��������� �翬�� Frame��  Vector �߰�
				
				Room.elementAt(Room.size()-1).addMouseListener(this);
				Room.elementAt(Room.size()-1).addMouseMotionListener(this);
				//�߰� ��Ų ��ư����  Mouse Event�� ���� Listener ���
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
						
						//Vector�� �߰���Ų ������ DB�� ������ ����
						try {
							String sql;
							ResultSet rs = db.select("select count(*) from Room where RoomID = '"+id+"';");// Room Table���� �ش� ������ Select �ؿ´�.
							rs.first();
							int a =rs.getInt("count(*)");
							if(a ==0){
								db.insertQuery("insert into room values('"+id+"','"+pw+"','"+(double) x+"','"+(double)y+"','"+Name+"');");//���� ���� ��� Room Table�� ���� �߰�
								db.insertQuery("insert into HRconnection values ('"+HouseID+"','"+RoomID.get(i)+"');");// ���� ���� ��� HRconnection Table�� �ش� ���� �߰�
							}
							else{
								db.updateQuery("update room set roomX = '"+x+"', roomY = '"+y+"' where RoomID = '"+id+"';");// ���� �ִ� ��� Room���� �ش� ���� Update
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
		// Mouse�� ������ offX,offY ���� ��� Drag���¸� true ���·� �ٲ۴�. offX ���� ���콺�� ���� Point - ó�� ��ü�� ��ǥ�� ���Ѵ�.
		
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
			//Room Object�� ���� �Ҷ� ������ ��ư�� ��ǥ �ȿ� ���� ��� �ش� ���� ����
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if(isDragged){
				e.getComponent().setBounds(e.getX()-offX, e.getY()-offY, 50, 50);
			}
		}
		// Mouse�� Drag�Ҷ� �� �������� setBoundsó�����Ͽ� �ٲ�� ��ǥ���� ȭ�鿡 �����ش�. ������ setBounds�� �Ķ���Ͱ��� ������ int���̾ ���۸��� ������ �ִ�.
		
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
		// Ŭ�� 2��������� �ش� Room ��ü�� Frame�� ����.

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