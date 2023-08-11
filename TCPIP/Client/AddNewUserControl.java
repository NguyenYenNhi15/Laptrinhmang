import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AddNewUserControl {
	private AddNewUserFrame addNewUserFrame;
	private String serverHost = "localhost";
	private int serverPort = 8080;
	
	public AddNewUserControl(AddNewUserFrame view) {
		this.addNewUserFrame = view;
		this.addNewUserFrame.addAddUserListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					User user = addNewUserFrame.getUser();
					
					Socket socket = new Socket(serverHost,serverPort);
					
					
					ObjectOutputStream ous = new ObjectOutputStream(socket.getOutputStream());
					
					ous.writeObject(user);
					
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					
					Object o = ois.readObject();
					if(o instanceof String) {
						String result = (String) o;
						if(result.equals("ok"))
								addNewUserFrame.showMessage("success");
						else addNewUserFrame.showMessage("Already has username");
					}
					
					socket.close();
					
				} catch (Exception e2) {
					System.out.println(e.toString());
				}
				
			}
		});
	}
}
