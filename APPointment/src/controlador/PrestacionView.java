package controlador;

public class PrestacionView 
{
	private int id;
	private int idServicio;
	private String servicio;
	private float importeHonorarios;
	
	public PrestacionView(int id, IdNombreView servicio, float importeHonorarios) {
		this.id = id;
		this.idServicio = servicio.getId();
		this.servicio = servicio.getNombre();
		this.importeHonorarios = importeHonorarios;
	}

	public int getId() {
		return id;
	}

	public int getIdServicio() {
		return idServicio;
	}

	public String getServicio() {
		return servicio;
	}

	public float getImporteHonorarios() {
		return importeHonorarios;
	}

	@Override
	public String toString() {
		return getServicio();
	}
}
