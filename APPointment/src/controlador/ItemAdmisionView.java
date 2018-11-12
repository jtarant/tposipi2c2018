package controlador;

public class ItemAdmisionView 
{
	private int idServicio;
	private Integer idCobertura;
	
	public ItemAdmisionView(int idServicio, Integer idCobertura) {
		this.idServicio = idServicio;
		this.idCobertura = idCobertura;
	}

	public int getIdServicio() {
		return idServicio;
	}

	public Integer getIdCobertura() {
		return idCobertura;
	}
}
