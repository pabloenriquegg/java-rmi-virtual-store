package tienda;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfaceProductos extends Remote {
	public boolean insertProductInShop (String name, float price) throws RemoteException, IOException;
	public List<Producto> showProductsInShop () throws RemoteException;
	public boolean deleteProductoInShop (int index) throws RemoteException ;
	
}
