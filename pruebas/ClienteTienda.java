package pruebas;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import tienda.*;

public class ClienteTienda {
	private static Scanner entradaUsuario = new Scanner(System.in);
	private static String menu = "principal";
	
	private static void clearConsole()
	{
		System.out.print("\033\143");
	}
	
	private static void menuPrincipal() {
		System.out.println("#######################################################");
		System.out.println("###     ENTORNO DE PRUEBAS DE LA TIENDA ONLINE      ###");
		System.out.println("#######################################################");
		System.out.println("");
		System.out.println("1 - Comprobar un login de usuario");
		System.out.println("2 - Registrar un nuevo usuario");
		System.out.println("3 - Listar los productos de la tienda");
		System.out.println("4 - Añadir un producto");
		System.out.println("5 - Borrar un producto");
		System.out.println("6 - Salir");
		try {
			int opcion = entradaUsuario.nextInt();
			switch (opcion) {
			case 1:
				menu="login";
				break;
			case 2:
				menu="registrarUsuario";
				break;
			case 3:
				menu="listarProductos";
				break;
			case 4:
				menu="anadirProducto";
				break;
			case 5:
				menu="borrarProducto";
				break;
			case 6:
				System.exit(0);
				break;
			default:
				System.out.println("");
				System.out.println("Opcion invalida, pulsa ENTER para continuar");
				entradaUsuario.nextLine();
				clearConsole();
				break;
			}

		}
		catch (Exception e) {
			entradaUsuario.nextLine(); // En caso de que no se introduzca un int, el sistema lee el cambio de linea por el Enter
			System.out.println("Opcion invalida, pulsa ENTER para continuar");
			entradaUsuario.nextLine();
			clearConsole();
		}
	}
	
	private static void menuLogin(InterfaceUsuarios usuariosRMI) throws RemoteException {
		entradaUsuario.nextLine(); // Como el menu principal toma como entrada enteros y siempre hay un cambio de linea al final (al pulsar enter) se hace una lectura para compensar
		System.out.println("#######################################################");
		System.out.println("###        COMPROBACION DE LOGIN DE USUARIO         ###");
		System.out.println("#######################################################");
		System.out.println("Usuario:");
		String username = entradaUsuario.nextLine();
		System.out.println("Contraseña:");
		String password = entradaUsuario.nextLine();
		boolean exito = usuariosRMI.doUserLogin(username, password);
		System.out.println("");
		if (exito) {
			System.out.println("Login exitoso");
		}
		else {
			System.out.println("Login fallido");
		}
		System.out.println("Pulsa ENTER para volver al menu principal");
		menu = "principal";
		entradaUsuario.nextLine();
		clearConsole();
	}
	
	private static void menuRegistrar(InterfaceUsuarios usuariosRMI) throws IOException {
		entradaUsuario.nextLine(); // Como el menu principal toma como entrada enteros y siempre hay un cambio de linea al final (al pulsar enter) se hace una lectura para compensar
		System.out.println("#######################################################");
		System.out.println("###           CREACION DE UN USUARIO NUEVO          ###");
		System.out.println("#######################################################");
		System.out.println("Usuario:");
		String username = entradaUsuario.nextLine();
		System.out.println("Contraseña:");
		String password = entradaUsuario.nextLine();
		System.out.println("E-mail:");
		String email = entradaUsuario.nextLine();
		System.out.println("Repite el E-mail:");
		String email2 = entradaUsuario.nextLine();
		System.out.println("Teléfono:");
		String phone = entradaUsuario.nextLine();
		
		String error = usuariosRMI.registerNewUser(username, password, email, email2, phone);
		System.out.println("");
		if (error.equals("")) {
			System.out.println("Usuario creado con éxito");
		}
		else {
			System.out.println("ERROR!");
			System.out.println(error);
		}
		System.out.println("Pulsa ENTER para volver al menu principal");
		menu = "principal";
		entradaUsuario.nextLine();
		clearConsole();
	}
	
	private static void menuListarProductos(InterfaceProductos productosRMI) throws RemoteException {
		entradaUsuario.nextLine(); // Como el menu principal toma como entrada enteros y siempre hay un cambio de linea al final (al pulsar enter) se hace una lectura para compensar
		System.out.println("#######################################################");
		System.out.println("###                 LISTA DE PRODUCTOS              ###");
		System.out.println("#######################################################");
		List<Producto> listaProductos = productosRMI.showProductsInShop();
		for (Producto producto : listaProductos) {
			System.out.println(producto.nombre+" - "+producto.precio+"€");
		}
		System.out.println("");
		System.out.println("Pulsa ENTER para volver al menu principal");
		menu = "principal";
		entradaUsuario.nextLine();
		clearConsole();
	}
	
	private static void menuBorrarProducto(InterfaceProductos productosRMI) throws RemoteException {
		entradaUsuario.nextLine(); // Como el menu principal toma como entrada enteros y siempre hay un cambio de linea al final (al pulsar enter) se hace una lectura para compensar
		System.out.println("#######################################################");
		System.out.println("###                 BORRAR UN PRODUCTO              ###");
		System.out.println("#######################################################");
		List<Producto> listaProductos = productosRMI.showProductsInShop();
		int index = 0;
		for (Producto producto : listaProductos) {
			System.out.println(index+1+" - "+producto.nombre+" - "+producto.precio+"€");
			index ++;
		}
		System.out.println("");
		System.out.println("Elige el producto a borrar o para volver al menu principal");	
		try {
			int productoElegido = entradaUsuario.nextInt();
			if (listaProductos.size()>= productoElegido) {
				boolean exito = productosRMI.deleteProductoInShop(productoElegido -1);
				System.out.println("");
				if (exito) {
					System.out.println("Producto borrado con exito");
				}
				else {
					System.out.println("Error al borrar el producto");
				}
			}
			else {
				System.out.println ("Producto invalido");
			}
			entradaUsuario.nextLine();
			System.out.println("");
			System.out.println("Pulsa ENTER para volver al menu principal");
			menu = "principal";
			entradaUsuario.nextLine();
			clearConsole();
		}
		catch (Exception e) {
			menu = "principal";
			entradaUsuario.nextLine();
		}

	}
	
	private static void menuAnadirProducto(InterfaceProductos productosRMI) throws RemoteException {
		entradaUsuario.nextLine(); // Como el menu principal toma como entrada enteros y siempre hay un cambio de linea al final (al pulsar enter) se hace una lectura para compensar
		System.out.println("#######################################################");
		System.out.println("###                 AÑADIR UN PRODUCTO              ###");
		System.out.println("#######################################################");
		System.out.println("Nombre:");
		String nombre = entradaUsuario.nextLine();
		System.out.println("Precio (float):");
		try {
			float precio = entradaUsuario.nextFloat();
			System.out.println(precio);
			entradaUsuario.nextLine();
			boolean exito = productosRMI.insertProductInShop(nombre, precio);
			System.out.println("");
			if (exito) {
				System.out.println("Producto insertado");
			}
			else {
				System.out.println("Error al insertar");
			}
		}
		catch (Exception e) {
			System.out.println("");
			entradaUsuario.nextLine(); // En caso de que no se introduzca un float, el sistema lee el cambio de linea por el Enter
			System.out.println("Precio invalido");
		}

		
		System.out.println("");
		System.out.println("Pulsa ENTER para volver al menu principal");
		menu = "principal";
		entradaUsuario.nextLine();
		clearConsole();
	}

	public static void main(String[] args) throws NotBoundException, IOException {
		InterfaceUsuarios usuariosRMI = (InterfaceUsuarios)Naming.lookup("rmi://"+args[0]+":"+args[1]+"/usuarios");
		InterfaceProductos productosRMI = (InterfaceProductos)Naming.lookup("rmi://"+args[0]+":"+args[1]+"/productos");

		clearConsole();
		while (true) {
			clearConsole();
			switch (menu) {
			case "principal":
				menuPrincipal();
				break;
			case "login":
				menuLogin(usuariosRMI);
				break;
			case "registrarUsuario":
				menuRegistrar(usuariosRMI);
				break;
			case "listarProductos":
				menuListarProductos(productosRMI);
				break;
			case "anadirProducto":
				menuAnadirProducto(productosRMI);
				break;
			case "borrarProducto":
				menuBorrarProducto(productosRMI);
				break;
			default:
				System.out.println("Menu no definido");
				System.exit(0);
			}
		}
	}

}
