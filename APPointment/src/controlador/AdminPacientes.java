package controlador;

import java.util.Hashtable;
import java.util.List;

import modelo.Paciente;
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
	
	public void limpiarCache()
	{
		pacientes.clear();
	}
}
