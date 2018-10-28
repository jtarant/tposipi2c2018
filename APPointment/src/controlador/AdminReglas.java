package controlador;

import java.time.DayOfWeek;
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
	
	public void limpiarCache()
	{
		reglas.clear();
	}
}
