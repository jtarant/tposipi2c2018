package controlador;

import java.util.Date;

public class TurnoView 
{
	private int id;
	private IdNombreView profesional;
	private IdNombreView paciente;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public IdNombreView getProfesional() {
		return profesional;
	}
	public void setProfesional(IdNombreView profesional) {
		this.profesional = profesional;
	}
	public IdNombreView getPaciente() {
		return paciente;
	}
	public void setPaciente(IdNombreView paciente) {
		this.paciente = paciente;
	}
	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}
	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}
	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}
}
