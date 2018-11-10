package controlador;

import java.util.Date;

public class ErrorTurnoView 
{
	private Date fechaHoraInicio;
	private IdNombreView paciente;
	private String causa;
	
	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}
	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}
	public IdNombreView getPaciente() {
		return paciente;
	}
	public void setPaciente(IdNombreView paciente) {
		this.paciente = paciente;
	}
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
}
