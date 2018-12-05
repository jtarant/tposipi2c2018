package modelo;

import controlador.CoberturaView;

public class Cobertura 
{
	private int id;
	private String numeroCredencial;
	private Boolean primaria;
	private Plan plan;
	private Boolean activa;
	
	public Cobertura(String numeroCredencial, Boolean primaria, Plan plan)
	{
		this.id = -1;
		this.numeroCredencial = numeroCredencial;
		this.primaria = primaria;
		this.plan = plan;
		this.activa = true;
	}
	
	public Cobertura(int id, String numeroCredencial, Boolean primaria, Plan plan) {
		this.id = id;
		this.numeroCredencial = numeroCredencial;
		this.primaria = primaria;
		this.plan = plan;
		this.activa = true;
	}

	public Plan getPlan() {
		return plan;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNumeroCredencial() {
		return numeroCredencial.trim();
	}

	public Boolean getPrimaria() {
		return primaria;
	}
		
	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}
	
	public void setPrimaria(Boolean primaria) {
		this.primaria = primaria;
	}

	public CoberturaView getView() throws Exception
	{
		return new CoberturaView(getId(), getNumeroCredencial(), getPrimaria(), getPlan().getView());
	}
}
