package modelo;

import controlador.IdNombreView;
import controlador.PrestacionView;

public class Prestacion 
{
	private int id;
	private Servicio servicio;
	private float importeHonorarios;
	
	public Prestacion(int id, Servicio servicio, float importeHonorarios) {
		this.id = id;
		this.servicio = servicio;
		this.importeHonorarios = importeHonorarios;
	}

	public int getId() {
		return id;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public float getImporteHonorarios() {
		return importeHonorarios;
	}
	
	public PrestacionView getView()
	{
		return new PrestacionView(getId(), new IdNombreView(getServicio().getId(),getServicio().getNombre()), getImporteHonorarios());
	}
}
