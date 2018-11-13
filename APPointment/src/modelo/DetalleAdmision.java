package modelo;

public class DetalleAdmision 
{
	private int id;
	private Servicio servicio;
	private Cobertura cobertura;
	private float importeAbonar;
	
	public DetalleAdmision(Prestacion prestacion, Cobertura cobertura) throws Exception 
	{
		this.servicio = prestacion.getServicio();
		
		if (cobertura == null)
		{
			this.importeAbonar = prestacion.getImporteHonorarios();
		}
		else
		{
			AlcanceCobertura alcance = cobertura.getPlan().obtenerAlcanceCobertura(this.servicio.getId());
			if (alcance == null)
				throw new ExceptionDeNegocio("Esta prestacion no es cubierta por su plan.");
			else
				this.importeAbonar = alcance.getImporteCopago();
		}
		this.cobertura = cobertura;
	}

	public int getId() {
		return id;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public Cobertura getCobertura() {
		return cobertura;
	}
	
	public float getImporteAbonar()
	{
		return importeAbonar;
	}
}
