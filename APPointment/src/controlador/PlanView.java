package controlador;

import java.util.List;

public class PlanView 
{
	private int id;
	private String nombre;
	private List<AlcanceCoberturaView> alcanceCobertura;
	
	public PlanView(int id, String nombre, List<AlcanceCoberturaView> alcanceCobertura) {
		this.id = id;
		this.nombre = nombre;
		this.alcanceCobertura = alcanceCobertura;
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
}
