package modelo;

import java.util.Date;

public class Paciente {
	private int id;
	private String apellido;
	private String nombre;
	private int DNI;
	private Date fechaNacimiento;
	private String telefono;
	private String email;
	private Boolean activo;

	public Paciente(String apellido, String nombre, int DNI, Date fechaNacimiento, String telefono, String email)
	{
		
	}
	
	public Paciente(int id, String apellido, String nombre, int DNI, Date fechaNacimiento, String telefono, String email, Boolean activo)
	{
		this.setId(id);
		this.setApellido(apellido);
		this.setNombre(nombre);
		this.setDNI(DNI);
		this.setFechaNacimiento(fechaNacimiento);
		this.setTelefono(telefono);
		this.setEmail(email);
		this.setActivo(activo);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido.trim().toUpperCase();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim().toUpperCase();
	}

	public int getDNI() {
		return DNI;
	}

	public void setDNI(int dNI) {
		DNI = dNI;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
		
}
