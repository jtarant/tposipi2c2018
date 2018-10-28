package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controlador.ReglaView;
import modelo.Paciente;
import modelo.Profesional;
import modelo.Regla;

public class AdmPersistenciaReglas 
{
	private static AdmPersistenciaReglas instancia;
	
	private AdmPersistenciaReglas()
	{
	}

	public static AdmPersistenciaReglas getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaReglas();
		return instancia;
	}
	
	public void insertar(Regla regla) throws Exception
	{
		Connection cnx = null;
		ResultSet clavesGeneradas = null;

		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("INSERT INTO ReglaTurnoProgramado (ID_Profesional,ID_Paciente,fechaInicio,fechaFin,repiteDia,hora,repiteCada,activa) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			cmdSql.setInt(1, regla.getProfesional().getId());
			cmdSql.setInt(2, regla.getPaciente().getId());
			cmdSql.setTimestamp(3, new Timestamp(regla.getFechaInicio().getTime()));
			if (regla.getFechaFin() != null)
			{
				cmdSql.setTimestamp(4, new Timestamp(regla.getFechaFin().getTime()));
			}
			else cmdSql.setTimestamp(4, null);
			cmdSql.setInt(5, regla.getRepiteDia().getValue());
			cmdSql.setTimestamp(6, new Timestamp(regla.getHora().getTime()));
			cmdSql.setInt(7, regla.getRepiteCada());
			cmdSql.setBoolean(8, regla.getActiva());
			cmdSql.execute();
			clavesGeneradas = cmdSql.getGeneratedKeys();
			if (clavesGeneradas.next())
			{
				regla.setId(clavesGeneradas.getInt(1));
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
		}
		catch (SQLException se)
		{
			throw se;
		}
		catch (Exception e)
		{
			throw e;
		}		
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}		
	}
	
	public List<ReglaView> obtenerReglas(int IdProfesional, int idPaciente) throws Exception
	{
		Connection cnx = null;
		List<ReglaView> lista = new ArrayList<ReglaView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT ID,ID_Profesional,ID_Paciente,FechaInicio,FechaFin,RepiteDia,Hora,RepiteCada,Activa FROM ReglaTurnoProgramado WHERE ID_Profesional=? AND ID_Paciente=? ORDER BY FechaInicio,RepiteDia,Hora");
			cmdSql.setInt(1, IdProfesional);
			cmdSql.setInt(2, idPaciente);
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				ReglaView itemRegla = new ReglaView();
				itemRegla.setId(result.getInt(1));
				itemRegla.setIdProfesional(result.getInt(2));
				itemRegla.setIdPaciente(result.getInt(3));
				itemRegla.setFechaInicio(result.getTimestamp(4));
				itemRegla.setFechaFin(result.getTimestamp(5));
				itemRegla.setRepiteDia(result.getInt(6));
				itemRegla.setHora(result.getTimestamp(7));
				itemRegla.setRepiteCada(result.getInt(8));
				itemRegla.setActivo(result.getBoolean(9));
				lista.add(itemRegla);
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return lista;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}				
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}
	}
	
	public Regla buscar(int id) throws Exception
	{
		Connection cnx = null;
		Regla regla = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT ID_Profesional,ID_Paciente,FechaInicio,FechaFin,RepiteDia,Hora,RepiteCada,Activa FROM ReglaTurnoProgramado WHERE id=?");
			cmdSql.setInt(1, id);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				int IdProfesional = result.getInt(1);
				int IdPaciente = result.getInt(2);
				Date fechaInicio = result.getDate(3);
				Date fechaFin = result.getDate(4);
				DayOfWeek dia = DayOfWeek.of(result.getInt(5));
				Date hora = result.getTimestamp(6);
				int repiteCada = result.getInt(7);
				Boolean activo = result.getBoolean(8);
				
				Paciente pacienteGhost = new Paciente(IdPaciente,null,null,0,null,null,null,null);
				pacienteGhost.setId(IdPaciente);
				
				Profesional profesionalGhost = new Profesional(IdProfesional,null,null,null);
				profesionalGhost.setId(IdProfesional);
				
				regla = new Regla(id, profesionalGhost, pacienteGhost, fechaInicio, fechaFin, dia, hora, repiteCada, activo); 
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return regla;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}		
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}		
	}

	public void modificar(Regla regla) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("UPDATE ReglaTurnoProgramado SET FechaInicio=?,FechaFin=?,RepiteDia=?,Hora=?,RepiteCada=?,Activa=? WHERE Id=?");
			cmdSql.setTimestamp(1, new Timestamp(regla.getFechaInicio().getTime()));
			if (regla.getFechaFin() != null)
			{
				cmdSql.setTimestamp(2, new Timestamp(regla.getFechaFin().getTime()));
			}
			else cmdSql.setTimestamp(2, null);
			cmdSql.setInt(3, regla.getRepiteDia().getValue());
			cmdSql.setTimestamp(4, new Timestamp(regla.getHora().getTime()));
			cmdSql.setInt(5, regla.getRepiteCada());
			cmdSql.setBoolean(6, regla.getActiva());
			cmdSql.setInt(7, regla.getId());
			cmdSql.execute();
			PoolConexiones.getInstancia().realeaseConnection(cnx);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().realeaseConnection(cnx); 
		}
	}
}
