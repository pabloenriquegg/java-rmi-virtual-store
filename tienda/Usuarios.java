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


public class Usuarios extends UnicastRemoteObject implements InterfaceUsuarios {
	private static final long serialVersionUID = 1L;
	List<Usuario> listaUsuarios = new ArrayList<>();
	private String archivoJSON;
	
	public void escribirJson() throws IOException {
		// Esta funcion toma la lista de usuarios y la guarda en el archivo. 
	    ObjectMapper mapper = new ObjectMapper();  
	    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY); // Para que el ObjectMapper sea capaz de leer atributos privados del objeto.
	    String json = mapper.writeValueAsString(listaUsuarios); 
	    BufferedWriter salidaArchivo = new BufferedWriter(new FileWriter(archivoJSON));
	    salidaArchivo.write(json);
	    salidaArchivo.close();
	}

	public Usuarios(String archivo) throws IOException{
		this.archivoJSON = archivo;
				
		try { // Se intenta abrir y cargar el archivo JSON especificado. Fallara si no existe, el formato no es correcto....
			BufferedReader entrada = new BufferedReader(new FileReader(archivoJSON));
			String contenidoJSON = entrada.readLine();
			entrada.close();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			this.listaUsuarios = mapper.readValue(contenidoJSON, new TypeReference<List<Usuario>>(){});
			System.out.println("Cargados "+listaUsuarios.size()+" usuarios");
		} 
		catch (Exception e) { // En caso de fallo, se empieza con un listado de usuarios vacio
		  System.out.println("No se han podido cargar usuarios. Se comienza con un listado vacio"); 
		  escribirJson();	
		}
	}

	@Override
	public boolean doUserLogin(String username, String password) throws RemoteException{
		for (Usuario usuario : this.listaUsuarios) {
			if (usuario.getUsername().equals(username)) {
				if (usuario.checkPassword(password)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String registerNewUser(String username, String password, String email, String email2, String phone) throws IOException, RemoteException{
		String error ="";
		// #### Se validan los parametros #### 
		// Se valida el formato del usuario, solo letras y numeros:
		String regex = "[a-zA-Z0-9]+";
		if (!username.matches(regex)) {
			error += "El nombre de usuario incluye caracteres no permitidos\n";
		}
		// Se valida que el usuario no este en uso:
		for (Usuario usuario : this.listaUsuarios) {
			if (usuario.getUsername().equals(username)) {
				error += "Ese nombre de usuario ya existe\n";
			}
		}
		// Se valida el formato de la contraseña
		regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&\\*%])\\S{8,}";
		if (!password.matches(regex)) {
			error+= "La contraseña no es suficientemente compleja\n";
		}
		// Se valida que ambos emails sean iguales
		if (!email.equals(email2)) {
			error+= "Los emails no coinciden\n";
		}
		// Se valida el formato del telefono 
		regex = "\\d{9,}";
		if (!phone.matches(regex)) {
			error+= "Formato del telefono incorrecto\n";
		}
		// Llegados a este punto, si no hay errores, se guarda el usuario.]
		if (error.equals("")) {
			Usuario usuario = new Usuario (username,password,email, phone);
			this.listaUsuarios.add(usuario);
			escribirJson();	
		}
		return error;
	}

	
	
}
