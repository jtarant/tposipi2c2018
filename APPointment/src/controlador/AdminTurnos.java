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
	
	public void reservar(int idPaciente, int idProfesional, Date fechaHoraInicio, int duracion) throws Exception
	{
		Paciente paciente = AdminPacientes.getInstancia().buscar(idPaciente);
		if (paciente == null) throw new ExceptionDeNegocio("El paciente especificado no existe: id=" + Integer.toString(idPaciente));
		
		Profesional profesional = AdminProfesionales.getInstancia().buscar(idProfesional);
		if (profesional == null)
			throw new ExceptionDeNegocio("El profesional especificado no existe: id=" + Integer.toString(idProfesional));
		else
			if (!profesional.getActivo()) throw new ExceptionDeNegocio("El profesional no puede tomar turnos porque no esta activo.");

		Turno nuevoTurno = new Turno(profesional, paciente, fechaHoraInicio, duracion);
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
	
	public List<ItemAgendaView> obtenerAgenda(Date desde, Date hasta, int IdProfesional) throws Exception
	{
		return AdmPersistenciaTurnos.getInstancia().obtenerAgenda(desde, hasta, IdProfesional);
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
			AdmPersistenciaTurnos.getInstancia().anular(id);
	}
	
	public void limpiarCache()
	{
		turnos.clear();
	}
}
