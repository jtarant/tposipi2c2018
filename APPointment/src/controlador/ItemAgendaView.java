package controlador;

import java.util.Date;

public class ItemAgendaView {
	private int IdTurno;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private int IdPaciente;
	private String Apellido;
	private String Nombre;
	private int DNI;
	private int estado;
	
	public int getIdTurno() {
		return IdTurno;
	}
	public void setIdTurno(int idTurno) {
		IdTurno = idTurno;
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
	public int getIdPaciente() {
		return IdPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		IdPaciente = idPaciente;
	}
	public String getApellido() {
		return Apellido;
	}
	public void setApellido(String apellido) {
		Apellido = apellido;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public int getDNI() {
		return DNI;
	}
	public void setDNI(int dNI) {
		DNI = dNI;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}	
}
