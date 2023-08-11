import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServerControl {
	private ServerView serverView;
	private Connection con;
	private DatagramSocket serverSocket;
	private DatagramPacket receivePacket;
	private int serverPort = 8080;
	
	
	public ServerControl(ServerView view ) {
		this.serverView = view;
		getDBConnection("user", "root", "1qaz@123");
		openServer(serverPort);
		view.showMessage("UDP server is running...");
		while(true) {
			listening();
		}
	}
	
	private void getDBConnection(String dbName, String userName,String password) {
		String dbUrl = "jdbc:mysql://localhost:3306/" + dbName;
		String dbClass = "com.mysql.cj.jdbc.Driver";
		try {
			Class.forName(dbClass);
			con = DriverManager.getConnection(dbUrl,userName,password);
			
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
		
	}
	
	private void openServer(int portNumber) {
		try {
			serverSocket = new DatagramSocket(portNumber);
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
	}
	
	private void listening() {
		try {
			User user = receiveData();
			String result = "false";
			
			if(registerUser(user)) {
				result = "ok";
			}
			
			sendData(result);
			
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
	}
	
	private User receiveData() {
		User user = null;
		try {
			byte [] receiveData = new byte[1024];
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			serverSocket.receive(receivePacket);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
			
			ObjectInputStream ois = new ObjectInputStream(bais);
			
			user = (User) ois.readObject();
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
		return user;
	}
	
	private void sendData(String result) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			
			oos.writeObject(result);
			oos.flush();
			
			InetAddress IPAddress = receivePacket.getAddress();
			int clientPort = receivePacket.getPort();
			
			byte[] sendData = baos.toByteArray();
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,IPAddress,clientPort);
			
			serverSocket.send(sendPacket);
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
	}
	

	private boolean registerUser(User user) {
		String query = "Select * FROM user WHERE username ='"
				+ user.getUserName()
				+ "'" ;
		String insert = "INSERT INTO user (username, password, address, birthday, sex, description) VALUES (?, ?, ?, ?, ?, ?)" ;
				
		try {
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
					if(rs.next() == false ) {
						PreparedStatement pst = con.prepareStatement(insert);
						pst.setString(1, user.getUserName());
						pst.setString(2, user.getPassword());
						pst.setString(3, user.getAddress());
						pst.setString(4, user.getBirthday());
						pst.setString(5, user.getBirthday());
						pst.setString(6, user.getDescription());
						
						int rowAffect = pst.executeUpdate();
						
						if(rowAffect > 0) {
							return true;
						}
					}
				} catch (Exception e) {
					serverView.showMessage(e.toString());
				}
		
		return false;
	}
	
	
	
}
