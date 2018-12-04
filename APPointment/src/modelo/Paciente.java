package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import controlador.AdminObrasSociales;
import controlador.CoberturaView;
import controlador.IdNombreView;
import controlador.ItemCoberturaView;
import controlador.PacienteView;
import persistencia.AdmPersistenciaPacientes;

public class Paciente 
{
	private int id;
	private String apellido;
	private String nombre;
	private Integer DNI;
	private Date fechaNacimiento;
	private String telefono;
	private String email;
	private Boolean activo;
	private List<Cobertura> coberturas;
	private List<Cobertura> nuevos;
	private List<Cobertura> eliminados;

	public Paciente(String apellido, String nombre, Integer DNI, Date fechaNacimiento, String telefono, String email, Boolean activo, List<Cobertura> coberturas) throws Exception
	{
		this.setApellido(apellido);
		this.setNombre(nombre);
		this.setDNI(DNI);
		this.setFechaNacimiento(fechaNacimiento);
		this.setTelefono(telefono);
		this.setEmail(email);
		this.setActivo(activo);
		this.setCoberturas(coberturas);
		AdmPersistenciaPacientes.getInstancia().insertar(this);
	}
	
	public Paciente(int id, String apellido, String nombre, Integer DNI, Date fechaNacimiento, String telefono, String email, Boolean activo, List<Cobertura> coberturas)
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

	public Integer getDNI() {
		return DNI;
	}

	public void setDNI(Integer dNI) {
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
		if (telefono != null)
			this.telefono = telefono.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email != null)
			this.email = email.trim().toLowerCase();
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
	
	public void setCambiosCoberturas(List<ItemCoberturaView> items) throws Exception
	{
		this.nuevos = new ArrayList<Cobertura>();
		this.eliminados = new ArrayList<Cobertura>();
		
		// Eliminados: los que tenia que ahora no estan
		for	(Cobertura c : getCoberturas())
		{
			Boolean encontrado = false;
			int i = 0;
			while (i < items.size() && !encontrado) 
			{
				ItemCoberturaView item = items.get(i);
				if ((item.getNroCredencial().equals(c.getNumeroCredencial())) && (item.getIdPlan() == c.getPlan().getId()))
					encontrado = true;
				i++;
			}
			if (!encontrado)
			{
				this.eliminados.add(c);
			}
		}

		// Nuevos: los que no tienen id asignado
		for (ItemCoberturaView item : items) 
		{
			if (item.getId() <= 0)
			{
				Plan plan = AdminObrasSociales.getInstancia().obtenerPlan(item.getIdPlan());
				Cobertura cobertura = new Cobertura(item.getNroCredencial(), item.getPrimaria(), plan);
				this.nuevos.add(cobertura);
			}
		}
	}
	
	public void actualizar() throws Exception
	{
		AdmPersistenciaPacientes.getInstancia().modificar(this);
		this.coberturas.addAll(nuevos);
		this.coberturas.removeAll(eliminados);
		this.nuevos = null;
		this.eliminados = null;
	}
	
	public void eliminar() throws Exception
	{
		AdmPersistenciaPacientes.getInstancia().eliminar(this);
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

	public List<Cobertura> getNuevos() {
		return nuevos;
	}

	public List<Cobertura> getEliminados() {
		return eliminados;
	}
}
