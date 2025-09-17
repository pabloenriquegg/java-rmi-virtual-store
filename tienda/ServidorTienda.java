package tienda;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;


public class ServidorTienda {

	public static void main(String[] args) throws IOException, AlreadyBoundException {
		// Argumentos:
		// args[0] - IP
		// args[1] - puerto
		// args[2] - archivo de usuarios
		// args[3] - archivo de productos
		Usuarios gestionUsuarios = new Usuarios (args[2]);
		Productos gestionProductos = new Productos (args[3]);
		// Se genera un registro en el puerto definido por args[1]
		LocateRegistry.createRegistry(Integer.valueOf(args[1]));
		// Se mapean los objetos en el registro RMI
		Naming.bind( "rmi://"+args[0]+":"+args[1]+"/usuarios", gestionUsuarios);
		Naming.bind( "rmi://"+args[0]+":"+args[1]+"/productos", gestionProductos);
		System.out.println ("Servidor listo");
	}

}
