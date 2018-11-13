package modelo;

import java.util.ArrayList;
import java.util.List;

public class Admision 
{
	private int id;
	private List<DetalleAdmision> detalleAdmision;
	private float totalAbonar;
	private float totalAbonado;
	
	public Admision()
	{
		detalleAdmision = new ArrayList<DetalleAdmision>();
		totalAbonar = 0;
		totalAbonado = 0;
	}
	
	public void agregarDetalle(Prestacion prestacion, Cobertura cobertura) throws Exception
	{
		DetalleAdmision item = new DetalleAdmision(prestacion, cobertura);
		totalAbonar += item.getImporteAbonar();
		detalleAdmision.add(item);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<DetalleAdmision> getDetalleAdmision() {
		return detalleAdmision;
	}

	public void setDetalleAdmision(List<DetalleAdmision> detalleAdmision) {
		this.detalleAdmision = detalleAdmision;
	}

	public float getTotalAbonar() {
		return totalAbonar;
	}

	public void setTotalAbonar(float totalAbonar) {
		this.totalAbonar = totalAbonar;
	}

	public float getTotalAbonado() {
		return totalAbonado;
	}

	public void setTotalAbonado(float totalAbonado) {
		this.totalAbonado = totalAbonado;
	}	
}
