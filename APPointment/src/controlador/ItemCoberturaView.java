package controlador;

public class ItemCoberturaView 
{
	private int id;
	private int idPlan;
	private String nroCredencial;
	private Boolean primaria;
	private String nombrePlan;
	private String nombreObraSocial;
	
	public ItemCoberturaView(int idPlan, String nroCredencial) {
		this.id = -1;
		this.idPlan = idPlan;
		this.nroCredencial = nroCredencial;
		this.primaria = false;
	}
	
	public ItemCoberturaView(int id, int idPlan, String nroCredencial, Boolean primaria)
	{
		this.id = id;
		this.idPlan = idPlan;
		this.nroCredencial = nroCredencial;
		this.primaria = primaria;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getIdPlan() {
		return idPlan;
	}

	public String getNroCredencial() {
		return nroCredencial;
	}	
	
	public void setPrimaria(Boolean primaria) {
		this.primaria = primaria;
	}

	public Boolean getPrimaria() {
		return primaria;
	}

	public String getNombrePlan() {
		return nombrePlan;
	}

	public String getNombreObraSocial() {
		return nombreObraSocial;
	}

	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}

	public void setNombreObraSocial(String nombreObraSocial) {
		this.nombreObraSocial = nombreObraSocial;
	}
}
