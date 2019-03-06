package ServerDB;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import FinalProject.HouseFrame;
import FinalProject.RoomFrame;
import FinalProject.TopicFrame;

public class DB {
	
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	public DB() throws ClassNotFoundException, SQLException{
		String driverName ="com.mysql.jdbc.Driver";
		try {
			Class.forName(driverName).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/mrello?autoReconnect=true&useSSL=false";
		con = DriverManager.getConnection(url, "root", "930731");
		stmt = con.createStatement();
	}
	
	public Connection getCon(){
		return con;
	}
	
	public ResultSet select(String input) throws SQLException{
		stmt = con.createStatement();
		rs = stmt.executeQuery(input);
		return rs;
	}
	
	public boolean updateQuery(String input){
		try {
	         stmt.executeUpdate(input);
	         return true;
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	         return false;
	      }
	}
	
	public boolean insertQuery(String input){
	      try {
	         stmt.executeUpdate(input);
	         return true;
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	         return false;
	      }
	   }
	
	public void deleteQuery(String input){
	      try {
	         stmt.executeUpdate(input);
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	}
	
	public boolean RoomOverlap(String id) throws SQLException{
		String sql = "select RoomID from Room;";
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			if(id.equals(rs.getString("RoomID"))){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean LoginRoom(String id, String pw,String houseID) throws Exception{
		boolean loginID = false;
		String _Id;
		String sql = "select RoomID from Room;";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				_Id = rs.getString("Roomid");
				if(_Id.equals(id)){
					loginID = true;
					break;
				}
				
			}
			if(!loginID){
				JOptionPane.showMessageDialog(null, "Room ID didn't exist!");
				return false;
			}
			
			sql = "select RoomPW from Room where RoomID = '"+id+"';";
			rs = stmt.executeQuery(sql);
			rs.first();
			String _pw = rs.getString("Roompw");
			if(_pw.equals(pw)){
				JOptionPane.showMessageDialog(null, "Successful");
				sql = "insert into HRconnection values('"+houseID+"','"+id+"');";
				stmt.executeUpdate(sql);
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null, "Password disaccord");
				return false;
			}
	}
	
	public boolean Login(String id,String pw) throws Exception{
		boolean loginID = false;
		String _Id;
		String sql = "select loginid from user;";
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				_Id = rs.getString("loginid");
				if(_Id.equals(id)){
					loginID = true;
					break;
				}
				
			}
			if(!loginID){
				JOptionPane.showMessageDialog(null, "ID didn't exist!");
				return false;
			}
			
			sql = "select loginpw from user where loginid = '"+id+"';";
			rs = stmt.executeQuery(sql);
			rs.first();
			String _pw = rs.getString("loginpw");
			if(_pw.equals(pw)){
				JOptionPane.showMessageDialog(null, "Successful");
				
				//read DB houseFrame
				HouseFrame frame = new HouseFrame();
				frame.HouseID = id;
				sql = "select * from HRconnection where HouseID = '"+id+"';";
				rs = stmt.executeQuery(sql);
				//rs.first();
				while(rs.next()){
					Statement stmt_ = con.createStatement();
					String RoomID = rs.getString("RoomID");
					System.out.println(RoomID);
					sql = "select * from Room where RoomID = '"+RoomID+"';";
					ResultSet s = stmt_.executeQuery(sql);
					while(s.next()){
						double x = Integer.parseInt(s.getString("roomX"));
						double y = Integer.parseInt(s.getString("roomY"));
						String name = s.getString("Name");
						frame.addRoom(RoomID,s.getString("RoomPW"), x, y, name);
						frame.repaint();
					}
				}
				//전에 저장된것을 부른다.
				
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null, "Password disaccord");
				return false;
			}
	}
	
	public boolean LoginPlus(String id, String pw, String repw) throws SQLException{
		String _Id;
		boolean idOverlap = false;
		String sql;
		
		if(!pw.equals(repw)){
			JOptionPane.showMessageDialog(null, "Password disaccord");
			return false;
		}
		sql = "select loginid from user;";
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			_Id = rs.getString("loginid");
			if(_Id.equals(id)){
				idOverlap =true;
				break;
			}
			
		}
		if(idOverlap){
			JOptionPane.showMessageDialog(null, "ID Overlap");
			return false;
		}
		else{
			//id pw 삽입
			sql = "insert into user values('"+id+"','"+pw+"');";
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			return true;
		}
	}
	
	public void readRoom(String id) throws SQLException, ClassNotFoundException{
		String sql;
		RoomFrame frame = new RoomFrame();
		frame.RoomID = id;
		sql = "select * from RTconnection where RoomID = '"+id+"';";
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			Statement stmt_ = con.createStatement();
			String TopicID = rs.getString("TopicID");
			sql = "select * from Topic where TopicID = '"+TopicID+"';";
			ResultSet s = stmt_.executeQuery(sql);
			s.next();
			double x = Integer.parseInt(s.getString("TopicX"));
			double y = Integer.parseInt(s.getString("TopicY"));
			String name = s.getString("Name");
			frame.addTopic(TopicID, x, y, name);
			frame.repaint();
		}
		frame.setVisible(true);
		//전에 저장된것을 부른다.
	}
	
	public void TopicRead(String topicID){
		String sql;
		TopicFrame frame;
		try {
			frame = new  TopicFrame();
			frame.TopicID = topicID;
			sql = "select * from TopicData where TopicID = '"+topicID+"';";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				int value = Integer.parseInt(rs.getString("Percentage"));
				String Name = rs.getString("TopicName");
				frame.addImformation(Name,value);
				frame.repaint();
			}
			frame.setVisible(true);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{
		DB db = new DB();
		  
	}
}
