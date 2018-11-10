package controlador;

import java.util.Date;

public class RecordarTurnoView {
	private int idTurno;
	private String apellidoPaciente;
	private String nombrePaciente;
	private String emailPaciente;
	private Date fechaHora;
	private String apellidoProfesional;
	private String nombreProfesional;
	
	public int getIdTurno() {
		return idTurno;
	}
	public void setIdTurno(int idTurno) {
		this.idTurno = idTurno;
	}
	public String getApellidoPaciente() {
		return apellidoPaciente;
	}
	public void setApellidoPaciente(String apellidoPaciente) {
		this.apellidoPaciente = apellidoPaciente;
	}
	public String getNombrePaciente() {
		return nombrePaciente;
	}
	public void setNombrePaciente(String nombrePaciente) {
		this.nombrePaciente = nombrePaciente;
	}
	public String getEmailPaciente() {
		return emailPaciente;
	}
	public void setEmailPaciente(String emailPaciente) {
		this.emailPaciente = emailPaciente;
	}
	public Date getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	public String getApellidoProfesional() {
		return apellidoProfesional;
	}
	public void setApellidoProfesional(String apellidoProfesional) {
		this.apellidoProfesional = apellidoProfesional;
	}
	public String getNombreProfesional() {
		return nombreProfesional;
	}
	public void setNombreProfesional(String nombreProfesional) {
		this.nombreProfesional = nombreProfesional;
	}
}
