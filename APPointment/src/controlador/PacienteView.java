package controlador;

import java.util.Date;
import java.util.List;

public class PacienteView 
{
	private int id;
	private String apellido;
	private String nombre;
	private int DNI;
	private Date fechaNacimiento;
	private String telefono;
	private String email;
	private Boolean activo;
	private List<CoberturaView> coberturas;
	
	public PacienteView(int id, String apellido, String nombre, int dNI, Date fechaNacimiento, String telefono,
			String email, Boolean activo, List<CoberturaView> coberturas) {
		this.id = id;
		this.apellido = apellido;
		this.nombre = nombre;
		DNI = dNI;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.email = email;
		this.activo = activo;
		this.coberturas = coberturas;
	}

	public int getId() {
		return id;
	}

	public String getApellido() {
		return apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public int getDNI() {
		return DNI;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getActivo() {
		return activo;
	}

	public List<CoberturaView> getCoberturas() {
		return coberturas;
	}
}
