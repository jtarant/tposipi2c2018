package modelo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import controlador.ItemAdmisionView;
import controlador.TurnoView;
import persistencia.AdmPersistenciaTurnos;

public class Turno 
{
	private int id;
	private Profesional profesional;
	private Paciente paciente;
	private Date fechaHoraInicio;
	private Date fechaHoraFin;
	private EstadoTurno estado;
	private Integer idReglaOrigen;
	private Admision admision;
	
	public Turno(Profesional profesional, Paciente paciente, Date fechaHoraInicio, int duracion) throws Exception
	{
		this(profesional, paciente, fechaHoraInicio, duracion, null);
	}
	
	public Turno(Profesional profesional, Paciente paciente, Date fechaHoraInicio, int duracion, Integer idReglaOrigen) throws Exception
	{
		this.setProfesional(profesional);
		this.setPaciente(paciente);
		this.setFechaHoraInicio(fechaHoraInicio);
		this.setIdReglaOrigen(idReglaOrigen);
		
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
	
	public Integer getIdReglaOrigen() {
		return idReglaOrigen;
	}

	public void setIdReglaOrigen(Integer idReglaOrigen) {
		this.idReglaOrigen = idReglaOrigen;
	}

	public Admision getAdmision()
	{
		return admision;
	}

	public void cancelar() throws Exception
	{
		AdmPersistenciaTurnos.getInstancia().anular(getId());
	}
	
	public void admitir(List<ItemAdmisionView> items, float importeAbonado) throws Exception 
	{
		Admision admision = new Admision();
		Prestacion prestacion;
		Cobertura cobertura;
		
		for (ItemAdmisionView item : items)
		{
			prestacion = this.profesional.obtenerPrestacion(item.getIdServicio());
			if (prestacion == null) 
				throw new ExceptionDeNegocio("El profesional no brinda la prestacion solicitada.");
			
			if (item.getIdCobertura() != null)
			{
				cobertura = this.paciente.obtenerCobertura(item.getIdCobertura());
				if (cobertura == null)
					throw new ExceptionDeNegocio("El paciente no tiene la cobertura seleccionada.");
			}
			else
			{
				cobertura = null;
			}
			admision.agregarDetalle(prestacion, cobertura);			
		}
		admision.setTotalAbonado(importeAbonado);
		this.admision = admision;
		setEstado(EstadoTurno.CERRADO);
		AdmPersistenciaTurnos.getInstancia().insertarAdmision(this);
	}
	
	public TurnoView getView()
	{
		TurnoView view = new TurnoView();
		view.setId(getId());
		view.setPaciente(getPaciente().getIdNombreView());
		view.setProfesional(getProfesional().getIdNombreView());
		view.setFechaHoraInicio(getFechaHoraInicio());
		view.setFechaHoraFin(getFechaHoraFin());
		return view;
	}
}
