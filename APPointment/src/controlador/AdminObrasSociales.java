package controlador;

import java.util.List;

import persistencia.AdmPersistenciaObrasSociales;

public class AdminObrasSociales 
{
	private static AdminObrasSociales instancia;
	
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
}
