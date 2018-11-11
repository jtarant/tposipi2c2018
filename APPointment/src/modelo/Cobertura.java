package modelo;

import controlador.CoberturaView;

public class Cobertura 
{
	private int id;
	private String numeroCredencial;
	private Boolean primaria;
	private Plan plan;
	
	public Cobertura(int id, String numeroCredencial, Boolean primaria, Plan plan) {
		this.id = id;
		this.numeroCredencial = numeroCredencial;
		this.primaria = primaria;
		this.plan = plan;
	}

	public Plan getPlan() {
		return plan;
	}

	public int getId() {
		return id;
	}

	public String getNumeroCredencial() {
		return numeroCredencial;
	}

	public Boolean getPrimaria() {
		return primaria;
	}
	
	public CoberturaView getView() throws Exception
	{
		return new CoberturaView(getId(), getNumeroCredencial(), getPrimaria(), getPlan().getView());
	}
}
