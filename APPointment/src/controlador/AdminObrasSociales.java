package controlador;

import java.util.Hashtable;
import java.util.List;

import modelo.ObraSocial;
import modelo.Plan;
import persistencia.AdmPersistenciaObrasSociales;

public class AdminObrasSociales 
{
	private static AdminObrasSociales instancia;
	private Hashtable<Integer, ObraSocial> obrasSociales;
	private Hashtable<Integer, Plan> planes;
	
	private AdminObrasSociales()
	{
		obrasSociales = new Hashtable<Integer, ObraSocial>();
		planes = new Hashtable<Integer, Plan>();
	}
	
	public static AdminObrasSociales getInstancia()
	{
		if (instancia == null)
			instancia = new AdminObrasSociales();
		return instancia;
	}
	
	public List<IdNombreView> listarPorIdNombre() throws Exception
	{
		return AdmPersistenciaObrasSociales.getInstancia().listarPorIdNombre();
	}
	
	public List<ObraSocialView> listarObrasSociales() throws Exception
	{
		return AdmPersistenciaObrasSociales.getInstancia().listarObrasSociales();
	}
	
	public List<IdNombreView> listarPlanesPorIdNombre(int idObraSocial) throws Exception
	{
		return AdmPersistenciaObrasSociales.getInstancia().listarPlanesPorIdNombre(idObraSocial);
	}
	
	public ObraSocial buscar(int id) throws Exception
	{
		ObraSocial os = null;
		os = obrasSociales.get(id);
		if (os != null)
			return os;
		else
		{
			os = AdmPersistenciaObrasSociales.getInstancia().buscar(id);
			if (os != null) obrasSociales.put(id, os);
			return os;
		}
	}
	
	public Plan obtenerPlan(int id) throws Exception
	{
		Plan plan = null;
		plan = planes.get(id);
		if (plan != null)
			return plan;
		else
		{
			plan = AdmPersistenciaObrasSociales.getInstancia().obtenerPlan(id);
			if (plan != null) planes.put(id, plan);
			return plan;
		}
	}
}
