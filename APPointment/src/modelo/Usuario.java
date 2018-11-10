package modelo;

import controlador.IdNombreView;

public class Usuario 
{
	private int idUsuario;
	private String apellido;
	private String nombre;

	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public IdNombreView getIdNombreView()
	{
		IdNombreView idNombre = new IdNombreView();
		idNombre.setId(getIdUsuario());
		idNombre.setNombre(getApellido() + ", " + getNombre());
		return idNombre;
	}
}
