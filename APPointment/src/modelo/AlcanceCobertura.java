package modelo;

import controlador.AlcanceCoberturaView;
import controlador.IdNombreView;

public class AlcanceCobertura 
{
	private int id;
	private Servicio servicio;
	private float importeCopago;
	
	public AlcanceCobertura(int id, Servicio servicio, float importeCopago) {
		this.id = id;
		this.servicio = servicio;
		this.importeCopago = importeCopago;
	}

	public int getId() {
		return id;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public float getImporteCopago() {
		return importeCopago;
	}
	
	public AlcanceCoberturaView getView()
	{
		return new AlcanceCoberturaView(new IdNombreView(getServicio().getId(),getServicio().getNombre()),getImporteCopago());
	}
}
