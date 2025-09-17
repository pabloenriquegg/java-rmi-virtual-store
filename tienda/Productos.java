package tienda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Productos extends UnicastRemoteObject implements InterfaceProductos {
	private static final long serialVersionUID = 1L;
	List<Producto> listaProductos = new ArrayList<>();
	private String archivoJSON;

	public void escribirJson() throws IOException {
		// Esta funcion toma la lista de usuarios y la guarda en el archivo. 
	    ObjectMapper mapper = new ObjectMapper();  
	    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY); // Para que el ObjectMapper sea capaz de leer atributos privados del objeto.
	    String json = mapper.writeValueAsString(listaProductos); 
	    BufferedWriter salidaArchivo = new BufferedWriter(new FileWriter(archivoJSON));
	    salidaArchivo.write(json);
	    salidaArchivo.close();
	}
	
	
	public Productos(String archivo) throws IOException{
		this.archivoJSON = archivo;
				
		try { // Se intenta abrir y cargar el archivo JSON especificado. Fallara si no existe, el formato no es correcto....
			BufferedReader entrada = new BufferedReader(new FileReader(archivoJSON));
			String contenidoJSON = entrada.readLine();
			entrada.close();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			this.listaProductos = mapper.readValue(contenidoJSON, new TypeReference<List<Producto>>(){});
			System.out.println("Cargados "+listaProductos.size()+" productos");
		} 
		catch (Exception e) { // En caso de fallo, se empieza con un listado de usuarios vacio
			System.out.println("No se han podido cargar productos. Se comienza con un listado vacio"); 
			escribirJson();				
		}
	}
	
	@Override
	public boolean insertProductInShop(String name, float price) throws IOException{
		Producto producto = new Producto (name, price);
		listaProductos.add(producto);
		escribirJson();
;		return true;
	}

	@Override
	public List<Producto> showProductsInShop() throws RemoteException{
		return listaProductos;
	}

	@Override
	public boolean deleteProductoInShop(int index) throws RemoteException{
		try {
			listaProductos.remove(index);
			escribirJson();
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}
