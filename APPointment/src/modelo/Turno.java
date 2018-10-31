package modelo;

import java.util.Calendar;
import java.util.Date;

import persistencia.AdmPersistenciaTurnos;

public class Turno 
{
	private int id;
	private Profesional profesional;
	private Paciente paciente;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private EstadoTurno estado;
	
	public Turno(Profesional profesional, Paciente paciente, Date fechaHoraInicio, int duracion) throws Exception
	{
		this.setProfesional(profesional);
		this.setPaciente(paciente);
		this.setFechaHoraInicio(fechaHoraInicio);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaHoraInicio);
		cal.add(Calendar.MINUTE, duracion);
		this.setFechaHoraFin(cal.getTime());
		
		this.setEstado(EstadoTurno.ABIERTO);
		AdmPersistenciaTurnos.getInstancia().insertar(this);
	}
	
	public Turno(int Id, Profesional profesional, Paciente paciente, Date fechaHoraInicio, Date fechaHoraFin, EstadoTurno estado)
	{
		this.setId(Id);
		this.setProfesional(profesional);
		this.setPaciente(paciente);
		this.setFechaHoraInicio(fechaHoraInicio);
		this.setFechaHoraFin(fechaHoraFin);
		this.setEstado(estado);
	}
	
	public Profesional getProfesional() {
		return profesional;
	}

	public void setProfesional(Profesional profesional) {
		this.profesional = profesional;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
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

	public EstadoTurno getEstado() {
		return estado;
	}

	public void setEstado(EstadoTurno estado) {
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void cancelar() throws Exception
	{
		if (getEstado() != EstadoTurno.ABIERTO) throw new ExceptionDeNegocio("No se puede anular un turno que no esta abierto.");
		AdmPersistenciaTurnos.getInstancia().anular(getId());
	}
}
