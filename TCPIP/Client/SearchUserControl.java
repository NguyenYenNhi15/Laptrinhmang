import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SearchUserControl {
	private SearchUserFrame searchUserFrame;
	private String serverHost = "localhost";
	private int serverPort = 8080;

	public SearchUserControl(SearchUserFrame view) {
		this.searchUserFrame = view;
		this.searchUserFrame.addSearchListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					String searchQuery = searchUserFrame.getSearchQuery();

					Socket clientSocket = new Socket(serverHost, serverPort);

					ObjectOutputStream ous = new ObjectOutputStream(clientSocket.getOutputStream());

					ous.writeObject(searchQuery);

					ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
					Object o = ois.readObject();
					if (o instanceof User) {
						User user = (User) o;
						if (user.getUserName().equals("null"))
							searchUserFrame.showUser("Khong ton tai User can tim");
						else {
							String userSearch = "Username : " + user.getUserName() + " Password : " + user.getPassword()
									+ " Address : " + user.getAddress() + " Birthday : " + user.getBirthday()
									+ " Sex : " + user.getSex() + " Description : " + user.getDescription();
							searchUserFrame.showUser(userSearch);
						}
					}
					
					clientSocket.close();

				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});
	}
}
