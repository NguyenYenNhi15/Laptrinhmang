import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServerFindUserControl {
	private ServerView serverView;
	private Connection con;
	private DatagramSocket serverSocket;
	private DatagramPacket receivePacket;
	private int serverPort = 5555;
	
	public ServerFindUserControl(ServerView view) {
		this.serverView = view;
		getDBConnection("user", "root", "123456789");
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
	
	private void openServer(int serverPort) {
		try {
			serverSocket = new DatagramSocket(serverPort);
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
	}
	
	private void listening() {
		try {
			String receiveData = receiveData();
			
			User user = findUser(receiveData);
			
			sendData(user);
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
		
		
	}
	
	private String receiveData() {
		String data = "";
		try {
			byte[] receiveData = new byte[1024];
			
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
			serverSocket.receive(receivePacket);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);
			
			ObjectInputStream ois = new ObjectInputStream(bais);
			
			data = (String) ois.readObject();
			
			
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
		
		return data;
		
	}
	
	private void sendData(User user) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			
			oos.writeObject(user);
			
			oos.flush();
			
			InetAddress iPAddress = receivePacket.getAddress();
			int clientPort = receivePacket.getPort();
			byte[] sendData = baos.toByteArray();
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, iPAddress, clientPort);
			
			serverSocket.send(sendPacket);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private User findUser(String username) {
		String query = "Select username,password,address,birthday,sex,description FROM user WHERE username ='"
				+ username + "'" ;
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				User user = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
				return user;
			}
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
		return new User("null","null","null","null","null","null");
	}
}
