package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import modelo.Cobertura;
import modelo.ExceptionDeNegocio;
import modelo.Paciente;
import modelo.Plan;
import persistencia.AdmPersistenciaPacientes;

public class AdminPacientes {
	private static AdminPacientes instancia;
	private Hashtable<Integer, Paciente> pacientes;
	
	private AdminPacientes()
	{
		pacientes = new Hashtable<Integer, Paciente>();
	}
	
	public static AdminPacientes getInstancia()
	{
		if (instancia == null)
			instancia = new AdminPacientes();
		return instancia;
	}
	
	public List<PacienteEncontradoView> buscarPorApellido(String apellido) throws Exception
	{
		return AdmPersistenciaPacientes.getInstancia().buscarPorApellido(apellido);
	}

	public Paciente buscar(int id) throws Exception
	{
		Paciente pac = null;
		pac = pacientes.get(id);
		if (pac != null)
			return pac;
		else
		{
			pac = AdmPersistenciaPacientes.getInstancia().buscar(id);
			if (pac != null) pacientes.put(id, pac);
			return pac;
		}
	}
	
	public PacienteView obtener(int id) throws Exception
	{
		Paciente p = buscar(id);
		if (p != null)
			return p.getView();
		else return null;
	}
	
	public void insertar(String apellido, String nombre, Integer dni, Date fechaNacimiento, String telefono, String email, boolean activo, List<ItemCoberturaView> coberturas) throws Exception 
	{
		List<Cobertura> coberturasPaciente = new ArrayList<Cobertura>();
		for	(ItemCoberturaView icv : coberturas)
		{
			Plan plan = AdminObrasSociales.getInstancia().obtenerPlan(icv.getIdPlan());
			Cobertura cobertura = new Cobertura(icv.getNroCredencial(), icv.getPrimaria(), plan);
			coberturasPaciente.add(cobertura);
		}
		Paciente nuevoPaciente = new Paciente(apellido, nombre, dni, fechaNacimiento, telefono, email, activo, coberturasPaciente);
		pacientes.put(nuevoPaciente.getId(), nuevoPaciente);
	}
	
	public void modificar(int id, String apellido, String nombre, int dni, Date fechaNacimiento, String telefono, String email, boolean activo, List<ItemCoberturaView> coberturas) throws Exception 
	{	
		Paciente paciente = this.buscar(id);
		if (paciente == null) throw new ExceptionDeNegocio("El paciente especificado no existe: id=" + Integer.toString(id));

		paciente.setApellido(apellido);
		paciente.setNombre(nombre);
		paciente.setDNI(dni);
		paciente.setFechaNacimiento(fechaNacimiento);
		paciente.setTelefono(telefono);
		paciente.setEmail(email);
		paciente.setActivo(activo);
		paciente.setCambiosCoberturas(coberturas);		
		
		paciente.actualizar();
		pacientes.replace(id, paciente);
	}
	
	public void eliminar(int id) throws Exception 
	{
		Paciente paciente = buscar(id);
		if (paciente == null) throw new ExceptionDeNegocio("El paciente especificado no existe: id=" + Integer.toString(id));

		paciente.eliminar();
		pacientes.remove(id);
	}
	
	public void limpiarCache()
	{
		pacientes.clear();
	}
}
