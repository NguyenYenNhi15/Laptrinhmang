import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends Remote {
	public String registerUser(User user) throws RemoteException;
	
	public User findUserByName(String userName) throws RemoteException;
}
