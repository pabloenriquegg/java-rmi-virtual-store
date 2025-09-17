package tienda;

import java.io.Serializable;

public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;
	public String nombre;
	public float precio;
	
	public Producto() {
		//Necesita definirse de cara a usar el ObjectMapper y que no de un error al leer el JSON
		
	}

	public Producto(String nombre, float precio) {
		this.nombre = nombre;
		this.precio = precio;
	}

}
