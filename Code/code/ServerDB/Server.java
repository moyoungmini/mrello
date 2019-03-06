package ServerDB;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server implements Runnable{
	
	private ServerSocket server;
	private Socket client;
	private Thread th;
	private DataInputStream in;
	private DataOutputStream out;
	private DB db;
	
	public Server() throws IOException, ClassNotFoundException, SQLException{
		server = new ServerSocket(1234);
		db = new DB();
		
	}
	
	@Override
	public void run() {
		try {
			connection();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} // 프로그램 멈춤을 막기위해 thread로 구성하여 서버 생성 및 accept 메소드 호출
		try {
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(true){
			try {
				String Line = in.readUTF();
				db.insertQuery(Line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//for readUTF
	}
	public void connection() throws IOException{
		server= new ServerSocket(2345);
		client = server.accept();
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException{
		Server test = new  Server();
		
	}
}
