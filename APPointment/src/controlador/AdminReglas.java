package controlador;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import modelo.ExceptionDeNegocio;
import modelo.Paciente;
import modelo.Profesional;
import modelo.Regla;
import persistencia.AdmPersistenciaReglas;

public class AdminReglas 
{
	private static AdminReglas instancia;
	private Hashtable<Integer, Regla> reglas;
	
	private AdminReglas()
	{
		reglas = new Hashtable<Integer, Regla>();
	}
		
	public static AdminReglas getInstancia()
	{
		if (instancia == null)
			instancia = new AdminReglas();
		return instancia;
	}
	
	public void insertar(int idProfesional, int idPaciente, Date inicio, Date fin, DayOfWeek repiteDias, Date hora, int repiteCada) throws Exception
	{
		Profesional pro = AdminProfesionales.getInstancia().buscar(idProfesional);
		if (pro == null) throw new ExceptionDeNegocio("El profesional especificado no existe: id=" + Integer.toString(idProfesional));

		Paciente pac = AdminPacientes.getInstancia().buscar(idPaciente);
		if (pac == null) throw new ExceptionDeNegocio("El paciente especificado no existe: id=" + Integer.toString(idPaciente));

		Regla regla = new Regla(pro,pac,inicio,fin,repiteDias,hora,repiteCada);
		reglas.put(regla.getId(), regla);
	}
	
	public void modificar(int id, Date inicio, Date fin, DayOfWeek repiteDias, Date hora, int repiteCada, Boolean activa) throws Exception
	{
		Regla regla = buscar(id);
		if (regla == null) throw new ExceptionDeNegocio("La regla especificada no existe: id=" + Integer.toString(id));
		
		regla.setFechaInicio(inicio);
		regla.setFechaFin(fin);
		regla.setRepiteDia(repiteDias);
		regla.setHora(hora);
		regla.setRepiteCada(repiteCada);
		regla.setActiva(activa);
		regla.actualizar();
		reglas.replace(regla.getId(), regla);
	}
	
	public List<ReglaView> obtenerReglas(int IdProfesional, int idPaciente) throws Exception
	{
		return AdmPersistenciaReglas.getInstancia().obtenerReglas(IdProfesional, idPaciente);
	}
	
	private Regla buscar(int id) throws Exception
	{
		Regla regla = reglas.get(id);
		if (regla != null)
			return regla;
		else
		{
			regla = AdmPersistenciaReglas.getInstancia().buscar(id);
			if (regla != null)
			{
				// reemplazar el Ghost de paciente
				Paciente pac = AdminPacientes.getInstancia().buscar(regla.getPaciente().getId());
				regla.setPaciente(pac);
				
				// reemplazar el Ghost de profesional
				Profesional prof = AdminProfesionales.getInstancia().buscar(regla.getProfesional().getId());
				regla.setProfesional(prof);
				reglas.put(id, regla);
			}
			return regla;
		}
	}
	
	public ReglaView obtener(int id) throws Exception
	{
		Regla regla = buscar(id);
		if (regla != null)
			return regla.getView();
		else
			return null;
	}

	public void eliminar(int id) throws Exception
	{
		Regla regla = buscar(id);
		if (regla != null)
		{
			regla.eliminar();
			reglas.replace(regla.getId(), regla);
		}
	}
	
	private List<Regla> obtenerReglas(int idProfesional, Date desde, Date hasta) throws Exception
	{
		List<Regla> reglas = new ArrayList<Regla>();
		List<Integer> idReglas = AdmPersistenciaReglas.getInstancia().obtenerIdReglas(idProfesional, desde, hasta);
		for (Integer id : idReglas)
		{
			Regla regla = this.buscar(id);
			if (regla != null)
				reglas.add(regla);
		}
		return reglas;
	}
	
	public List<ErrorTurnoView> generarTurnos(Date fecha, int idProfesional) throws Exception
	{
		List<ErrorTurnoView> errores = new ArrayList<ErrorTurnoView>();
		List<Regla> reglas = new ArrayList<Regla>();
		List<Date> fechas;
		
		Date periodoDesde = Date.from(Regla.getInicioMes(fecha).atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date periodoHasta = Date.from(Regla.getFinMes(fecha).atStartOfDay(ZoneId.systemDefault()).toInstant());
		periodoHasta.setHours(23);
		periodoHasta.setMinutes(59);
		periodoHasta.setSeconds(59);
		
		Boolean yaGenerados = AdminTurnos.getInstancia().existenTurnosGenerados(periodoDesde, periodoHasta, idProfesional);
		
		if (!yaGenerados)
		{
			reglas = obtenerReglas(idProfesional, periodoDesde, periodoHasta);
			for	(Regla regla : reglas)
			{
				fechas = regla.calcularFechas(fecha);
				for (Date fechaHora : fechas)
				{
					try
					{
						AdminTurnos.getInstancia().reservar(regla.getPaciente().getId(), idProfesional, fechaHora, regla.getId());						
					}
					catch (Exception exReserva)
					{
						ErrorTurnoView error = new ErrorTurnoView();
						error.setCausa(exReserva.getMessage());
						error.setFechaHoraInicio(fechaHora);
						error.setPaciente(new IdNombreView(regla.getPaciente().getId(), regla.getPaciente().getApellido() + ", " + regla.getPaciente().getNombre()));
						errores.add(error);
					}
				}
			}
		}
		return errores;
	}
	
	public void limpiarCache()
	{
		reglas.clear();
	}
}
