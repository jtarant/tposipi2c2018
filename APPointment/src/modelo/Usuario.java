package modelo;

import controlador.IdNombreView;
import controlador.UsuarioEmailNombreView;
import persistencia.AdmPersistenciaUsuarios;

public class Usuario {	
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String contrasena;
	private String email;
	private String rol;
	private Boolean activo;
	
	public Usuario(int id, String nombre, String apellido, String contrasena, String email, String rol, Boolean activo) 
	{
		this.idUsuario = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasena = contrasena;
		this.email = email;
		this.rol = rol;
		this.activo = activo;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		if (contrasena != null)
			this.contrasena = Seguridad.ofuscarPassword(contrasena);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public IdNombreView getIdNombreView()
	{
		IdNombreView idNombre = new IdNombreView();
		idNombre.setId(getIdUsuario());
		idNombre.setNombre(getApellido() + ", " + getNombre());
		return idNombre;
	}
		
	public UsuarioEmailNombreView getEmailNombreView()
	{
		return new UsuarioEmailNombreView(getEmail(),getNombre(),getApellido());
	}
	
	public void actualizar() throws Exception 
	{
		AdmPersistenciaUsuarios.getInstancia().modificar(this);
	}
}
