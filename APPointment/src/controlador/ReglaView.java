package controlador;

import java.util.Date;

public class ReglaView 
{
	private int id;
	private int idProfesional;
	private int idPaciente;
	private Date fechaInicio;
	private Date fechaFin;
	private Date hora;
	private int repiteDia;
	private int repiteCada;
	private Boolean activo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdProfesional() {
		return idProfesional;
	}
	public void setIdProfesional(int idProfesional) {
		this.idProfesional = idProfesional;
	}
	public int getIdPaciente() {
		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getHora() {
		return hora;
	}
	public void setHora(Date hora) {
		this.hora = hora;
	}
	public int getRepiteDia() {
		return repiteDia;
	}
	public void setRepiteDia(int repiteDia) {
		this.repiteDia = repiteDia;
	}
	public int getRepiteCada() {
		return repiteCada;
	}
	public void setRepiteCada(int repiteCada) {
		this.repiteCada = repiteCada;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
