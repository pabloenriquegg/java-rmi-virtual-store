package tienda;

public class Usuario {
	private String username;
	private String password;
	private String phone;
	private String email;
	
	public Usuario() { //Necesita definirse de cara a usar el ObjectMapper y que no de un error al leer el JSON
		
	}

	public Usuario(String username, String password, String phone, String email) {
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}
	
	public boolean checkPassword (String password) {
		if (this.password.equals(password)) {
			return true;
		}
		return false;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getEmail() {
		return email;
	}

	
	
	
	
}
