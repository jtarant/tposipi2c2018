package controlador;

public class CoberturaView 
{
	private int id;
	private String numeroCredencial;
	private Boolean primaria;
	private PlanView plan;
	
	public CoberturaView(int id, String numeroCredencial, Boolean primaria, PlanView plan) {
		this.id = id;
		this.numeroCredencial = numeroCredencial;
		this.primaria = primaria;
		this.plan = plan;
	}

	public PlanView getPlan() {
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
}
