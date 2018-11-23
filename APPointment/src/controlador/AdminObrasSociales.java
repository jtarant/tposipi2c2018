package controlador;

import java.util.Hashtable;
import java.util.List;

import modelo.ObraSocial;
import persistencia.AdmPersistenciaObrasSociales;

public class AdminObrasSociales 
{
	private static AdminObrasSociales instancia;
	private Hashtable<Integer, ObraSocial> obrasSociales;
	
	private AdminObrasSociales()
	{
		obrasSociales = new Hashtable<Integer, ObraSocial>();
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
}
