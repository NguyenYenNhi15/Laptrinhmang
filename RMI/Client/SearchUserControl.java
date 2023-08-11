import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class SearchUserControl {
	private SearchUserFrame searchUserFrame;
	private String serverHost = "localhost";
	private int serverPort = 8080;
	private String rmiService = "rmiServer";
	private Registry registry;
	private RMIServerInterface rmiServer;

	public SearchUserControl(SearchUserFrame view) {
		this.searchUserFrame = view;
		try {
			registry = LocateRegistry.getRegistry(serverHost, serverPort);
			rmiServer = (RMIServerInterface)registry.lookup(rmiService);
		} catch (Exception e) {
			
		}
		this.searchUserFrame.addSearchListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					String searchQuery = searchUserFrame.getSearchQuery();

					
					User user = findUser(searchQuery);

					if (user.getUserName().equals("null"))
						searchUserFrame.showUser("Khong ton tai User can tim");
					else {
						String userSearch = "Username : " + user.getUserName() + " Password : " + user.getPassword()
								+ " Address : " + user.getAddress() + " Birthday : " + user.getBirthday() + " Sex : "
								+ user.getSex() + " Description : " + user.getDescription();
						searchUserFrame.showUser(userSearch);
					}

				

				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});
	}
	
	private User findUser(String userName) {
		User user = new User("null","null","null","null","null","null");
		try {
			 user = rmiServer.findUserByName(userName);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
}
