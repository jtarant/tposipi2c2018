package controlador;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import modelo.EmailRecordatorioTurno;
import modelo.ExceptionDeNegocio;
import modelo.NotificadorEmail;
import modelo.Paciente;
import modelo.Profesional;
import modelo.Seguridad;
import modelo.Turno;
import persistencia.AdmPersistenciaTurnos;

public class AdminTurnos {
	private static AdminTurnos instancia;
	private Hashtable<Integer, Turno> turnos;
	
	private AdminTurnos()
	{
		turnos = new Hashtable<Integer, Turno>();
	}
		
	public static AdminTurnos getInstancia()
	{
		if (instancia == null)
			instancia = new AdminTurnos();
		return instancia;
	}
	
	public void reservar(int idPaciente, int idProfesional, Date fechaHoraInicio) throws Exception
	{
		this.reservar(idPaciente, idProfesional, fechaHoraInicio, null);
	}

	public void reservar(int idPaciente, int idProfesional, Date fechaHoraInicio, Integer idReglaOrigen) throws Exception
	{
		Paciente paciente = AdminPacientes.getInstancia().buscar(idPaciente);
		if (paciente == null)
			throw new ExceptionDeNegocio("El paciente especificado no existe: id=" + Integer.toString(idPaciente));
		else
			if (!paciente.getActivo()) throw new ExceptionDeNegocio("El paciente no puede tomar turnos porque no esta activo.");
		
		Profesional profesional = AdminProfesionales.getInstancia().buscar(idProfesional);
		if (profesional == null)
			throw new ExceptionDeNegocio("El profesional especificado no existe: id=" + Integer.toString(idProfesional));
		else
			if (!profesional.getActivo()) throw new ExceptionDeNegocio("El profesional no puede tomar turnos porque no esta activo.");

		Turno nuevoTurno;
		if (idReglaOrigen == null)
		{
			nuevoTurno = new Turno(profesional, paciente, fechaHoraInicio, profesional.getDuracionTurno());
		}
		else
		{
			nuevoTurno = new Turno(profesional, paciente, fechaHoraInicio, profesional.getDuracionTurno(), idReglaOrigen);
		}
		turnos.put(nuevoTurno.getId(), nuevoTurno);
	}
	
	private Turno buscar(int id) throws Exception
	{
		Turno turno = null;
		turno = turnos.get(id);
		if (turno != null)
			return turno;
		else
		{
			turno = AdmPersistenciaTurnos.getInstancia().buscar(id);
			if (turno != null)
			{
				// reemplazar el Ghost de paciente
				Paciente pac = AdminPacientes.getInstancia().buscar(turno.getPaciente().getId());
				turno.setPaciente(pac);
				
				// reemplazar el Ghost de profesional
				Profesional prof = AdminProfesionales.getInstancia().buscar(turno.getProfesional().getId());
				turno.setProfesional(prof);
				turnos.put(id, turno);
			}
			return turno;
		}
	}
	
	public AgendaView obtenerAgenda(Date desde, Date hasta, int IdProfesional) throws Exception
	{
		List<ErrorTurnoView> errores = AdminReglas.getInstancia().generarTurnos(desde,IdProfesional);
		List<ItemAgendaView> turnos = AdmPersistenciaTurnos.getInstancia().obtenerAgenda(desde, hasta, IdProfesional);
		return new AgendaView(turnos, errores);
	}
	
	public TurnoView obtenerTurno(String idOfuscado) throws Exception
	{
		int id = Seguridad.desOfuscarId(idOfuscado);
		
		Turno turno = this.buscar(id);
		if (turno == null)
			throw new ExceptionDeNegocio("No se pudo encontrar el turno.");
		else
			return turno.getView();
	}
	
	public void recordarTurnos() throws Exception
	{
		final int DIAS_ANTES = 2;
		EmailRecordatorioTurno email;

		List<RecordarTurnoView> recordatorios = AdmPersistenciaTurnos.getInstancia().obtenerTurnosARecordar(DIAS_ANTES);
		
		for (RecordarTurnoView turno : recordatorios)
		{
			email = new EmailRecordatorioTurno(turno.getEmailPaciente(), turno.getApellidoPaciente(), turno.getNombrePaciente(), turno.getFechaHora(), turno.getApellidoProfesional(), turno.getNombreProfesional(), turno.getIdTurno());
			NotificadorEmail.getInstancia().enviar(email);
		}
	}
	
	public void anular(String idOfuscado) throws Exception
	{
		int id = Seguridad.desOfuscarId(idOfuscado);
		anular(id);
	}
	
	public void anular(int id) throws Exception
	{
		Turno turno = buscar(id);
		if (turno == null)
			throw new ExceptionDeNegocio("No se pudo encontrar el turno.");
		else
			turno.cancelar();
	}
	
	public Boolean existenTurnosGenerados(Date desde, Date hasta, int idProfesional) throws Exception
	{
		return AdmPersistenciaTurnos.getInstancia().existenTurnosGenerados(desde,hasta,idProfesional);
	}
	
	public void limpiarCache()
	{
		turnos.clear();
	}
}
