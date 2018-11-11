package controlador;

public class AlcanceCoberturaView 
{
	private IdNombreView servicio;
	private float importeCopago;
	
	public AlcanceCoberturaView(IdNombreView servicio, float importeCopago) {
		this.servicio = servicio;
		this.importeCopago = importeCopago;
	}

	public IdNombreView getServicio() {
		return servicio;
	}

	public float getImporteCopago() {
		return importeCopago;
	}
}
