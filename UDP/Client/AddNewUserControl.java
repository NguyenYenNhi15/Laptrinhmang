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

public class AddNewUserControl {
	private AddNewUserFrame addNewUserFrame;
	private String serverHost = "localhost";
	private int serverPort = 8080;
	private int clientPort = 6666;
	private DatagramSocket myClient;

	public AddNewUserControl(AddNewUserFrame view) {
		this.addNewUserFrame = view;
		this.addNewUserFrame.addAddUserListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					openConnection();
					User user = addNewUserFrame.getUser();
					sendData(user);

					String result = receiveData();
					if (result.equals("ok"))
						addNewUserFrame.showMessage("success");
					else
						addNewUserFrame.showMessage("Already has username");
					
					closeConnection();
				} catch (Exception e2) {
					System.out.println(e.toString());
				}

			}
		});
	}

	private void openConnection() {
		try {
			myClient = new DatagramSocket(clientPort);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void closeConnection() {
		try {
			myClient.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private String receiveData() {

		String result = "";
		try {
			byte[] receiveData = new byte[1024];

			DatagramPacket recivePacket = new DatagramPacket(receiveData, receiveData.length);

			myClient.receive(recivePacket);

			ByteArrayInputStream bais = new ByteArrayInputStream(receiveData);

			ObjectInputStream ois = new ObjectInputStream(bais);

			result = (String) ois.readObject();

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

	private void sendData(User user) {

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ObjectOutputStream oos = new ObjectOutputStream(baos);

			oos.writeObject(user);

			oos.flush();

			InetAddress IPAddress = InetAddress.getByName(serverHost);

			byte[] sendData = baos.toByteArray();

			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);

			myClient.send(sendPacket);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
