package modelo;

import java.time.DayOfWeek;
import java.util.Date;

import controlador.ReglaView;
import persistencia.AdmPersistenciaReglas;

public class Regla 
{
	private int id;
	private Profesional profesional;
	private Paciente paciente;
	private Date fechaInicio;
	private Date fechaFin;
	private DayOfWeek repiteDia;
	private Date hora;
	private int repiteCada;
	private Boolean activa;

	public Regla(Profesional pro, Paciente pac, Date inicio, Date fin, DayOfWeek repiteDias, Date hora, int repiteCada) throws Exception
	{
		setProfesional(pro);
		setPaciente(pac);
		setFechaInicio(inicio);
		setFechaFin(fin);
		setRepiteDia(repiteDias);
		setHora(hora);
		setRepiteCada(repiteCada);
		setActiva(true);
		AdmPersistenciaReglas.getInstancia().insertar(this);
	}
	
	public Regla(int id, Profesional pro, Paciente pac, Date inicio, Date fin, DayOfWeek repiteDias, Date hora, int repiteCada, Boolean activa)
	{
		setId(id);
		setProfesional(pro);
		setPaciente(pac);
		setFechaInicio(inicio);
		setFechaFin(fin);
		setRepiteDia(repiteDias);
		setHora(hora);
		setRepiteCada(repiteCada);
		setActiva(activa);		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public DayOfWeek getRepiteDia() {
		return repiteDia;
	}
	public void setRepiteDia(DayOfWeek repiteDia) {
		this.repiteDia = repiteDia;
	}
	public Date getHora() {
		return hora;
	}
	public void setHora(Date hora) {
		this.hora = hora;
	}
	public int getRepiteCada() {
		return repiteCada;
	}
	public void setRepiteCada(int repiteCada) {
		this.repiteCada = repiteCada;
	}
	public Boolean getActiva() {
		return activa;
	}
	public void setActiva(Boolean activa) {
		this.activa = activa;
	}
	
	public ReglaView getView()
	{
		ReglaView view = new ReglaView();
		view.setId(this.getId());
		view.setIdProfesional(this.profesional.getId());
		view.setIdPaciente(this.paciente.getId());
		view.setFechaInicio(this.getFechaInicio());
		view.setFechaFin(this.getFechaFin());
		view.setHora(this.getHora());
		view.setRepiteDia(this.getRepiteDia().getValue());
		view.setRepiteCada(this.getRepiteCada());
		view.setActivo(this.getActiva());
		return view;
	}

	public void actualizar() throws Exception 
	{
		AdmPersistenciaReglas.getInstancia().modificar(this);
	}
	
	public void eliminar() throws Exception
	{
		setActiva(false);
		actualizar();
	}
}
