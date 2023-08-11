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
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AddNewUserControl {
	private AddNewUserFrame addNewUserFrame;
	private String serverHost = "localhost";
	private int serverPort = 8080;
	private Registry registry;
	private String rmiService = "rmiServer";
	private RMIServerInterface rmiServer;

	

	public AddNewUserControl(AddNewUserFrame view) {
		this.addNewUserFrame = view;
		
		try {
			registry = LocateRegistry.getRegistry(serverHost,serverPort);
			
			rmiServer =(RMIServerInterface) registry.lookup(rmiService);
		} catch (Exception e) {
		}
		this.addNewUserFrame.addAddUserListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					
					User user = addNewUserFrame.getUser();

					String result = resgisterUser(user);
					if (result.equals("ok"))
						addNewUserFrame.showMessage("success");
					else
						addNewUserFrame.showMessage("Already has username");
					
					
				} catch (Exception e2) {
					System.out.println(e.toString());
				}

			}
		});
	}
	
	private String resgisterUser(User user) {
		String result = "false";
		try {
			result = rmiServer.registerUser(user);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
			
	}

}
