package controlador;

import java.util.List;

public class PlanView 
{
	private int id;
	private String nombre;
	private List<AlcanceCoberturaView> alcanceCobertura;
	private ObraSocialView obraSocial;
	
	public PlanView(int id, String nombre, List<AlcanceCoberturaView> alcanceCobertura, ObraSocialView obraSocial) {
		this.id = id;
		this.nombre = nombre;
		this.alcanceCobertura = alcanceCobertura;
		this.obraSocial = obraSocial;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public List<AlcanceCoberturaView> getAlcanceCobertura() 
	{
		return alcanceCobertura;
	}

	public ObraSocialView getObraSocial() {
		return obraSocial;
	}
}
