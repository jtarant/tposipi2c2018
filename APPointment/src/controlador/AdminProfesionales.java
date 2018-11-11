package controlador;

import java.util.Hashtable;
import java.util.List;

import modelo.Profesional;
import persistencia.AdmPersistenciaProfesionales;

public class AdminProfesionales {
	private static AdminProfesionales instancia;
	private Hashtable<Integer, Profesional> profesionales;
	
	private AdminProfesionales()
	{
		profesionales = new Hashtable<Integer, Profesional>();
	}
	
	public static AdminProfesionales getInstancia()
	{
		if (instancia == null)
			instancia = new AdminProfesionales();
		return instancia;
	}
	
	public List<IdNombreView> listarPorIdNombre() throws Exception
	{
		return AdmPersistenciaProfesionales.getInstancia().listarPorIdNombre();
	}
	
	public Profesional buscar(int id) throws Exception
	{
		Profesional pro = null;
		pro = profesionales.get(id);
		if (pro != null)
			return pro;
		else
		{
			pro = AdmPersistenciaProfesionales.getInstancia().buscar(id);
			if (pro != null) profesionales.put(id, pro);
			return pro;
		}
	}
	
	public ProfesionalView obtener(int id) throws Exception
	{
		Profesional p = this.buscar(id);
		if (p != null)
		{
			return p.getView();
		}
		else return null;
	}
	
	public void limpiarCache()
	{
		profesionales.clear();
	}
}
