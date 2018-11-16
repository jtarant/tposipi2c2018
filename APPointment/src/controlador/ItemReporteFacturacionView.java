package controlador;

import java.util.Date;

public class ItemReporteFacturacionView 
{
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private String nombre;
	private String apellido;
	private String nroCredencial;
	private String codigoPractica;
	private String descripcionPractica;
	
	public ItemReporteFacturacionView(Date fechaHoraInicio, Date fechaHoraFin, String nombre, String apellido,
			String nroCredencial, String codigoPrestacion, String descripcionPractica) {
		this.fechaHoraInicio = fechaHoraInicio;
		this.fechaHoraFin = fechaHoraFin;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nroCredencial = nroCredencial;
		this.codigoPractica = codigoPrestacion;
		this.descripcionPractica = descripcionPractica;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getCodigoPractica() {
		return codigoPractica;
	}
	
	public String getNroCredencial() {
		return nroCredencial;
	}
	
	public String getDescripcionPractica() {
		return descripcionPractica;
	}
}
