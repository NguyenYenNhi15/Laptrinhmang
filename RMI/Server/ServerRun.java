import java.rmi.RemoteException;

public class ServerRun {
	public static void main(String[] args) {
		ServerView view = new ServerView();
		try {
			ServerControl serverControl = new ServerControl(view);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
