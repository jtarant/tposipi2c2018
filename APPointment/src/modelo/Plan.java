package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import controlador.AdminObrasSociales;
import controlador.AlcanceCoberturaView;
import controlador.PlanView;
import persistencia.AdmPersistenciaObrasSociales;

public class Plan 
{
	private int id;
	private String nombre;
	private List<AlcanceCobertura> alcanceCobertura; // Lazy-loading
	private ObraSocial obraSocial;

	public Plan(int id, String nombre, int idObraSocial) throws Exception {
		this.id = id;
		this.nombre = nombre;
		this.alcanceCobertura = null;
		this.obraSocial = AdminObrasSociales.getInstancia().buscar(idObraSocial);
	}
	
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public ObraSocial getObraSocial() {
		return obraSocial;
	}

	public List<AlcanceCobertura> getAlcancesCobertura() throws Exception 
	{
		// Lazy-Loading, dado que solo se usa en admision
		if (alcanceCobertura == null)
		{
			alcanceCobertura = AdmPersistenciaObrasSociales.getInstancia().obtenerAlcanceCobertura(id);
		}
		return alcanceCobertura;
	}

	public AlcanceCobertura obtenerAlcanceCobertura(int idServicio) throws Exception
	{
		Iterator<AlcanceCobertura> iterador = getAlcancesCobertura().iterator();
		while (iterador.hasNext())
		{
			AlcanceCobertura ac = iterador.next();
			if (ac.getServicio().getId() == idServicio) return ac;
		}
		return null;
	}

	public PlanView getView() throws Exception
	{
		List<AlcanceCoberturaView> av = new ArrayList<AlcanceCoberturaView>();
		for(AlcanceCobertura ac : getAlcancesCobertura())
			av.add(ac.getView());
		return new PlanView(getId(), getNombre(), av, getObraSocial().getView());
	}
}
