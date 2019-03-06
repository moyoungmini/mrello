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

public class TopicFrame extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
	
	private ImageIcon mainLogo = new ImageIcon("MainLogoTest.png");
	private ImageIcon plusLogo = new ImageIcon("AddTopic.png");
	private ImageIcon delLogo = new ImageIcon("MinusTopic.png");
	private ImageIcon exitLogo = new ImageIcon("Exit.png");
	private ImageIcon workLogo = new ImageIcon("Work.png");
	private ImageIcon barLogo = new ImageIcon("Bar.png");
	
	private JButton workPlus;
	private JButton workDelete;
	private JButton Exit;
	
	private JPanel Screen;
	
	private Vector <JLabel> workImage = new Vector <JLabel> () ;
	private Vector <JLabel> workName = new Vector <JLabel> () ;
	private Vector <JButton> workPercentage = new Vector<JButton> () ;
	private Vector <JProgressBar> workPt = new Vector <JProgressBar>();
	
	private int workCount = 0;
	
	private DB db;
	
	public String TopicID = null;
	
	public TopicFrame() throws ClassNotFoundException, SQLException{
		db = new DB();
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	      Image img = toolkit.getImage("MImage.png");
	      setIconImage(img);
		
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
		
		workLogo.setImage(workLogo.getImage().getScaledInstance(30,30,java.awt.Image.SCALE_SMOOTH));
		barLogo.setImage(barLogo.getImage().getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH));
		
		plusLogo.setImage(plusLogo.getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
		workPlus = new JButton(plusLogo);
		workPlus.setBackground(new Color(Color.TRANSLUCENT));
		workPlus.setOpaque(false);
		workPlus.setContentAreaFilled(false);
		workPlus.setBorderPainted(false);
		workPlus.setBounds(25,345,50,50);
		
		delLogo.setImage(delLogo.getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
		workDelete = new JButton(delLogo);
		workDelete.setBackground(new Color(Color.TRANSLUCENT));
		workDelete.setOpaque(false);
		workDelete.setContentAreaFilled(false);
		workDelete.setBorderPainted(false);
		workDelete.setBounds(245,345,50,50);
		
		exitLogo.setImage(exitLogo.getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
		Exit = new JButton(exitLogo);
		Exit.setBackground(new Color(Color.TRANSLUCENT));
		Exit.setOpaque(false);
		Exit.setContentAreaFilled(false);
		Exit.setBorderPainted(false);
		Exit.setBounds(465,345,50,50);
		//Button fandamental set
		
		
		workPlus.addActionListener(this);
		workDelete.addActionListener(this);
		Exit.addActionListener(this);
		//Button Handler
		
		Screen.add(workPlus);
		Screen.add(workDelete);
		Screen.add(Exit);
		add(Screen);
		setVisible(false);
	}
	
	public void addImformation(String name, int value){
		JLabel newLabel = new JLabel(name);
		newLabel.setBackground(new Color(Color.TRANSLUCENT));
		newLabel.setOpaque(false);
		newLabel.setBounds(75,15 + workCount*50,100,40);
		
		JProgressBar newBar = new JProgressBar();
		newBar.setValue(value);
		newBar.setMinimum(0);
		newBar.setMaximum(100);
		newBar.setStringPainted(true);
		newBar.setBounds(280, 15 + workCount * 50, 270, 25);
		newBar.setValue(value);
		
		workName.addElement(newLabel);
		workPt.addElement(newBar);
		Screen.add(workName.get(workName.size()-1));
		Screen.add(workPt.get(workPt.size()-1));
		repaint();
		Addim();
		setVisible(true);
	}
	//DB���� ����ϱ� ���� �Լ� �ۼ�
	
	public void Addim(){
		JLabel newImage = new JLabel(workLogo);
		JButton newButton = new JButton(barLogo);
		workImage.addElement(newImage);
		workPercentage.addElement(newButton);
		
		newImage.setBackground(new Color(Color.TRANSLUCENT));
		newImage.setOpaque(false);
		newImage.setBounds(15,15 + workCount*50,40,40);
		
		newButton.setBackground(new Color(Color.TRANSLUCENT));
		newButton.setOpaque(false);
		newButton.setContentAreaFilled(false);
		newButton.setBorderPainted(false);
		newButton.setBounds(200,15 + workCount * 50,50,40);
		newButton.addMouseListener(this);
		newButton.addMouseMotionListener(this);
		
		workPercentage.get(workPercentage.size()-1).addActionListener(this);
		Screen.add(newImage);
		Screen.add(newButton);
		Screen.add(workName.get(workName.size()-1));
		Screen.add(workPt.get(workPt.size()-1));
		workCount++;
		repaint();
	}
	// �ڵ带 �� ���� �Ⱦ �Լ� ��� �ǹ̴� ���� ����.

		@Override
		public void actionPerformed(ActionEvent Event) {
			if(Event.getSource() == workPlus){
				String Name = JOptionPane.showInputDialog("Work Name Input");
				if(Name!= null){
					JLabel newName = new JLabel(Name);
					JProgressBar newPB = new JProgressBar();
					
					workName.addElement(newName);
					workPt.addElement(newPB);
					
					newName.setBackground(new Color(Color.TRANSLUCENT));
					newName.setOpaque(false);
					newName.setBounds(75,15 + workCount*50,100,40);
					
					newPB.setMinimum(0);
					newPB.setMaximum(100);
					newPB.setStringPainted(true);
					newPB.setBounds(280, 15 + workCount * 50, 270, 25);
					newPB.setValue(0);
					
					JLabel newImage = new JLabel(workLogo);
					JButton newButton = new JButton(barLogo);
					workImage.addElement(newImage);
					workPercentage.addElement(newButton);
					
					newImage.setBackground(new Color(Color.TRANSLUCENT));
					newImage.setOpaque(false);
					newImage.setBounds(15,15 + workCount*50,40,40);
					
					newButton.setBackground(new Color(Color.TRANSLUCENT));
					newButton.setOpaque(false);
					newButton.setContentAreaFilled(false);
					newButton.setBorderPainted(false);
					newButton.setBounds(200,15 + workCount * 50,50,40);
					//�߰��Ͽ��� ��� GUI�� ���̱����� ó�� �ڵ�
					
					newButton.addMouseListener(this);
					newButton.addMouseMotionListener(this);
					
					workPercentage.get(workPercentage.size()-1).addActionListener(this); // �߰��� ��ư�����ؼ� ActionListener ó��
					Screen.add(newImage);
					Screen.add(newButton);
					
					Screen.add(newName);
					Screen.add(newPB);
					Screen.setVisible(true);
					
					repaint();
					workCount++;
				}
			}
			
			if(Event.getSource() == workDelete){
				int index = Integer.parseInt(JOptionPane.showInputDialog("Tell us the number you want to delete"));
				index--;
				workImage.get(index).setVisible(false);
				workName.get(index).setVisible(false);
				workPercentage.get(index).setVisible(false);
				workPt.get(index).setVisible(false);
				
				workImage.remove(index);
				workName.remove(index);
				workPercentage.remove(index);
				workPt.remove(index);
				
				for(int i=index;i<workCount -1;i++){
					workImage.get(i).setVisible(false);
					workName.get(i).setVisible(false);
					workPercentage.get(i).setVisible(false);
					workPt.get(i).setVisible(false);
				}
				// �����Ҷ� �ش��ϴ� ��� ������ ȭ�鿡�� ����� ���Ϳ� �ִ� ���� ��θ� �����.
				
				for(int i=index;i<workCount -1;i++){
					workImage.get(i).setBounds(15,15 + i*50,40,40);
					workName.get(i).setBounds(75,15 + i*50,100,40);
					workPercentage.get(i).setBounds(200,15 + i * 50,50,40);
					workPt.get(i).setBounds(280, 15 + i * 50, 270, 40);
					
					workImage.get(i).setVisible(true);
					workName.get(i).setVisible(true);
					workPercentage.get(i).setVisible(true);
					workPt.get(i).setVisible(true);
					
					Screen.add(workImage.get(i));
					Screen.add(workName.get(i));
					Screen.add(workPercentage.get(i));
					Screen.add(workPt.get(i));
				}
				//���� �� �����ִ� ������ ȭ�鿡 �ٽ� ����� ȭ�鿡�� ������ ���� ��ĭ�� ���ش�.
				workCount--;
				String sql = "delete from topicdata where topicid = '"+TopicID+"' and TopicName = '"+workName.get(index)+"';";
				db.updateQuery(sql);
			}
			
			if(Event.getSource() == Exit){
				int select = JOptionPane.showConfirmDialog(null, "Do you really want to quit? ","Termination",
						  JOptionPane.OK_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
				if(select == 0){
					//����
					for(int i=0 ;i< workPt.size();i++){
						String name = workName.get(i).getText();
						int pc = workPt.get(i).getValue();
						//Topic Screen���� Name, Percentage ���� ������ ����
						
						try {
							String sql;
							sql = "select * from TopicData where TopicID = '"+TopicID+"' and TopicName = '"+name+"';";
							//TopicData Table���� TopicID�� �´� Table Select �ؿ���.
							ResultSet rs = db.select(sql);
							
							if(!rs.next()){
								//�� ����
								db.insertQuery("insert into TopicData values('"+TopicID+"','"+name+"','"+pc+"');");//TopicData�� �� �ֱ�
							}
							else{
								db.updateQuery("update TopicData set Percentage = '"+pc+"' where TopicID = '"+TopicID+"' and TopicName = '"+name+"';");
								//TopicData �� �ִ� �� Update
								//�� �ִ�.
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					setVisible(false);
					dispose();
				}
			}
		}
		// TopicFrame â �����ϸ� ���ݱ��� �� ���� ����
		
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("test");
			// TODO Auto-generated method stub
			for(int i =0; i<workPercentage.size(); i++){
				if(workPercentage.get(i)== e.getSource()){
					int Percentage = Integer.parseInt(JOptionPane.showInputDialog("input percentage"));
					workPt.get(i).setValue(Percentage);
					repaint();
					setVisible(true);
				}
			}
		}
		//2�� Ŭ������ ��� �ش� ��ü�� Screen�� ����.
		
		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		TopicFrame test = new TopicFrame();
		test.setVisible(true);
	}
}
	