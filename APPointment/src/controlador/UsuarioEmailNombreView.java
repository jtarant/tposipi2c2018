package controlador;

public class UsuarioEmailNombreView {
	private String email;
	private String nombre;
	private String apellido;
	
	public UsuarioEmailNombreView(String email, String nombre, String apellido)
	{
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public String getEmail()
	{
		return email;
	}
	public String getNombre()
	{
		return nombre;
	}
	public String getApellido()
	{
		return apellido;
	}
}
