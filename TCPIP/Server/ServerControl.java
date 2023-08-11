package Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private int serverPort = 8080;
	
	
	public ServerControl(ServerView view ) {
		this.serverView = view;
		getDBConnection("user", "root", "1qaz@123");
		openServer(serverPort);
		view.showMessage("TCP server is running...");
		while(true) {
			listening();
		}
	}
	
	private void getDBConnection(String dbName, String userName,String password) {
		String dbUrl = "jdbc:mysql://localhost:3306/user" ;
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
			serverSocket = new ServerSocket(8080);
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
	}
	
	private void listening() {
		try {
			clientSocket = serverSocket.accept();
			ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream ous = new ObjectOutputStream(clientSocket.getOutputStream());
			Object o = ois.readObject();
			if(o instanceof User) {
				User user = (User) o;
				if(registerUser(user)) {
					ous.writeObject("ok");
				}
				else {
					ous.writeObject("false");
				}
			}
			else {
				String username = (String) o;
				ous.writeObject(findUser(username));
			}
		} catch (Exception e) {
			serverView.showMessage(e.toString());
		}
	}

	private User findUser(String username) {
		String query = "Select username,password,address,birthday,sex,description FROM user WHERE username ='" + username + "'" ;
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

	private boolean registerUser(User user) {
		String query = "Select * FROM user WHERE username ='" + user.getUserName() + "'" ;
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
