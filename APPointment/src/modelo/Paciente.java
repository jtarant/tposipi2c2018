package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import controlador.CoberturaView;
import controlador.IdNombreView;
import controlador.PacienteView;

public class Paciente 
{
	private int id;
	private String apellido;
	private String nombre;
	private int DNI;
	private Date fechaNacimiento;
	private String telefono;
	private String email;
	private Boolean activo;
	private List<Cobertura> coberturas;

	public Paciente(String apellido, String nombre, int DNI, Date fechaNacimiento, String telefono, String email, Boolean activo, List<Cobertura> coberturas)
	{
		// TODO: implementar. hace el insert
	}
	
	public Paciente(int id, String apellido, String nombre, int DNI, Date fechaNacimiento, String telefono, String email, Boolean activo, List<Cobertura> coberturas)
	{
		this.setId(id);
		this.setApellido(apellido);
		this.setNombre(nombre);
		this.setDNI(DNI);
		this.setFechaNacimiento(fechaNacimiento);
		this.setTelefono(telefono);
		this.setEmail(email);
		this.setActivo(activo);
		this.setCoberturas(coberturas);
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
		if (apellido != null)
			this.apellido = apellido.trim().toUpperCase();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre != null)
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
		if (email != null)
			this.email = email.toLowerCase();
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public List<Cobertura> getCoberturas() {
		return coberturas;
	}

	public Cobertura obtenerCobertura(int id)
	{
		Iterator<Cobertura> iterador = getCoberturas().iterator();
		while (iterador.hasNext())
		{
			Cobertura c = iterador.next();
			if (c.getId() == id) return c;
		}
		return null;
	}
	
	public void setCoberturas(List<Cobertura> coberturas) {
		this.coberturas = coberturas;
	}
	
	public void actualizar()
	{
		// TODO: implementar
	}
	
	public void eliminar()
	{
		// TODO: implementar
	}

	public IdNombreView getIdNombreView()
	{
		IdNombreView idNombre = new IdNombreView();
		idNombre.setId(getId());
		idNombre.setNombre(getApellido() + ", " + getNombre());
		return idNombre;
	}
	
	public PacienteView getView() throws Exception
	{
		List<CoberturaView> cv = new ArrayList<CoberturaView>();
		for (Cobertura c : getCoberturas())
			cv.add(c.getView());
		return new PacienteView(getId(),getApellido(),getNombre(),getDNI(),getFechaNacimiento(),getTelefono(),getEmail(),getActivo(),cv);
	}
}
