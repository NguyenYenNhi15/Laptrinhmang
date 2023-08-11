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

public class SearchUserControl {
	private SearchUserFrame searchUserFrame;
	private String serverHost = "localhost";
	private int serverPort = 5555;
	private int clientPort = 6666;
	private DatagramSocket myClient;

	public SearchUserControl(SearchUserFrame view) {
		this.searchUserFrame = view;
		this.searchUserFrame.addSearchListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					String searchQuery = searchUserFrame.getSearchQuery();

					openConnection();
					
					sendDat(searchQuery);
					
					User user = receiveData();

					if (user.getUserName().equals("null"))
						searchUserFrame.showUser("Khong ton tai User can tim");
					else {
						String userSearch = "Username : " + user.getUserName() + " Password : " + user.getPassword()
								+ " Address : " + user.getAddress() + " Birthday : " + user.getBirthday() + " Sex : "
								+ user.getSex() + " Description : " + user.getDescription();
						searchUserFrame.showUser(userSearch);
					}

					closeConnection();

				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});
	}

	private void openConnection() {
		try {
			myClient = new DatagramSocket(clientPort);
		} catch (Exception e) {

		}
	}

	private void closeConnection() {
		try {
			myClient.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void sendDat(String query) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ObjectOutputStream oos = new ObjectOutputStream(baos);

			oos.writeObject(query);

			oos.flush();

			InetAddress IPAddress = InetAddress.getByName(serverHost);

			byte[] sendData = baos.toByteArray();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);

			myClient.send(sendPacket);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private User receiveData() {
		User user = null;
		try {
			byte[] receiveData = new byte[1024];

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			myClient.receive(receivePacket);

			ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);

			ObjectInputStream ois = new ObjectInputStream(bais);

			user = (User) ois.readObject();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return user;
	}
}
