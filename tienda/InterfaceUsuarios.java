package tienda;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceUsuarios extends Remote {
	public boolean doUserLogin(String username, String password) throws RemoteException;
	public String registerNewUser (String username, String password, String email, String email2, String phone) throws RemoteException, IOException;
}
