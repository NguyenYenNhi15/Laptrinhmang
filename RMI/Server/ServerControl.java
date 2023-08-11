import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServerControl extends UnicastRemoteObject implements RMIServerInterface {
	private ServerView serverView;
	private Connection con;
	private int serverPort = 8080;
	private Registry registry;
	private String rmiService = "rmiServer";
	
	
	public ServerControl(ServerView view ) throws RemoteException {
		this.serverView = view;
		getDBConnection("user", "root", "trungnh@123");
		view.showMessage("RMI server is running...");
		
		try {
			registry = LocateRegistry.createRegistry(serverPort);
			registry.rebind(rmiService, (Remote) this);
		} catch (RemoteException e) {
			view.showMessage(e.toString());
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
	

	private boolean regisUser(User user) {
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

	@Override
	public String registerUser(User user) throws RemoteException {
		String result = "false";
		if(regisUser(user))
			result = "ok";
		return result;
	}

	@Override
	public User findUserByName(String userName) throws RemoteException {
		return findUser(userName);
	}
	
	
	
}
