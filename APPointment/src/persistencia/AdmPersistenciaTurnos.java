package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controlador.ItemAgendaView;
import controlador.RecordarTurnoView;
import modelo.EstadoTurno;
import modelo.ExceptionDeNegocio;
import modelo.Paciente;
import modelo.Profesional;
import modelo.Turno;

public class AdmPersistenciaTurnos {
	private static AdmPersistenciaTurnos instancia;
	
	private AdmPersistenciaTurnos()
	{
	}

	public static AdmPersistenciaTurnos getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaTurnos();
		return instancia;
	}
	
	public void insertar(Turno turno) throws Exception
	{
		Connection cnx = null;
		ResultSet clavesGeneradas = null;

		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			
			StringBuilder query = new StringBuilder("IF EXISTS(SELECT TOP 1 ID FROM Turno WHERE fechaHoraInicio<? AND fechaHoraFin>? AND ID_Profesional=? AND Estado=0) ");
			query.append("BEGIN RAISERROR('Superposicion',16,1) RETURN END ");
			query.append("INSERT INTO Turno (ID_Profesional,ID_Paciente,fechaHoraInicio,fechaHoraFin,estado) VALUES (?,?,?,?,?)");
			
			PreparedStatement cmdSql = cnx.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
			// para verificar superposicion
			cmdSql.setTimestamp(1, new Timestamp(turno.getFechaHoraFin().getTime()));
			cmdSql.setTimestamp(2, new Timestamp(turno.getFechaHoraInicio().getTime()));
			cmdSql.setInt(3, turno.getProfesional().getId());
			// para insertar
			cmdSql.setInt(4, turno.getProfesional().getId());
			cmdSql.setInt(5, turno.getPaciente().getId());
			cmdSql.setTimestamp(6, new Timestamp(turno.getFechaHoraInicio().getTime()));
			cmdSql.setTimestamp(7, new Timestamp(turno.getFechaHoraFin().getTime()));
			cmdSql.setInt(8, turno.getEstado().getValor());
			cmdSql.execute();
			clavesGeneradas = cmdSql.getGeneratedKeys();
			if (clavesGeneradas.next())
			{
				turno.setId(clavesGeneradas.getInt(1));
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
		}
		catch (SQLException se)
		{
			if (se.getMessage().equalsIgnoreCase("Superposicion")) throw new ExceptionDeNegocio("El horario ya esta ocupado por otro paciente.");
			else throw se;
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


	public Turno buscar(int id) throws Exception
	{
		Connection cnx = null;
		Turno turno = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT ID_Paciente,ID_Profesional,fechaHoraInicio,fechaHoraFin,Estado FROM TURNO WHERE id=?");
			cmdSql.setInt(1, id);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				int IdPaciente = result.getInt(1);
				int IdProfesional = result.getInt(2);
				Date fechaHoraInicio = result.getDate(3);
				Date fechaHoraFin = result.getDate(4);
				int estado = result.getInt(5);
				
				Paciente pacienteGhost = new Paciente(IdPaciente,null,null,0,null,null,null,null);
				pacienteGhost.setId(IdPaciente);
				
				Profesional profesionalGhost = new Profesional(IdProfesional,null,null,null);
				profesionalGhost.setId(IdProfesional);
				
				turno = new Turno(id, profesionalGhost, pacienteGhost, fechaHoraInicio, fechaHoraFin, EstadoTurno.fromInt(estado));
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return turno;
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

	public List<ItemAgendaView> obtenerAgenda(Date desde, Date hasta, int IdProfesional) throws Exception
	{
		Connection cnx = null;
		List<ItemAgendaView> lista = new ArrayList<ItemAgendaView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			
			StringBuilder query = new StringBuilder("SELECT turno.ID, turno.fechaHoraInicio, turno.fechaHoraFin,");
			query.append("paciente.ID, paciente.apellido, paciente.nombre, paciente.DNI ");
			query.append("FROM Turno INNER JOIN Paciente ON turno.ID_Paciente=Paciente.ID ");
			query.append("WHERE turno.estado=0 ");
			query.append("AND turno.ID_Profesional=? ");
			query.append("AND turno.fechaHoraInicio>=? ");
			query.append("AND turno.fechaHoraFin<=? ");
			query.append("ORDER BY turno.fechaHoraInicio");

			PreparedStatement cmdSql = cnx.prepareStatement(query.toString());
			cmdSql.setInt(1, IdProfesional);
			cmdSql.setTimestamp(2, new Timestamp(desde.getTime()));
			cmdSql.setTimestamp(3, new Timestamp(hasta.getTime()));
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				ItemAgendaView itemAgenda = new ItemAgendaView();
				itemAgenda.setIdTurno(result.getInt(1));
				itemAgenda.setFechaHoraInicio(result.getTimestamp(2));
				itemAgenda.setFechaHoraFin(result.getTimestamp(3));
				itemAgenda.setIdPaciente(result.getInt(4));
				itemAgenda.setApellido(result.getString(5));
				itemAgenda.setNombre(result.getString(6));
				itemAgenda.setDNI(result.getInt(7));
				lista.add(itemAgenda);
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

	public void anular(int idTurno) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql;
			cmdSql = cnx.prepareStatement("UPDATE TURNO SET Estado=? WHERE IdTurno=?");
			cmdSql.setInt(1, EstadoTurno.ANULADO.getValor());
			cmdSql.setInt(2, idTurno);
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

	public List<RecordarTurnoView> obtenerTurnosARecordar(int diasAntes) throws Exception 
	{
		Connection cnx = null;
		List<RecordarTurnoView> lista = new ArrayList<RecordarTurnoView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			
			StringBuilder query = new StringBuilder("SELECT turno.ID, turno.fechaHoraInicio, ");
			query.append("paciente.apellido, paciente.nombre, paciente.email, usuario.apellido, usuario.nombre ");
			query.append("FROM Turno INNER JOIN Paciente ON turno.ID_Paciente=Paciente.ID ");
			query.append("INNER JOIN Profesional ON turno.ID_Profesional=Profesional.ID ");
			query.append("INNER JOIN Usuario ON profesional.ID_Usuario=Usuario.ID ");
			query.append("WHERE turno.estado=0 ");
			query.append("AND DATEDIFF(\"d\",GETDATE(),FechaHoraInicio)=? ");
			query.append("ORDER BY turno.id");

			PreparedStatement cmdSql = cnx.prepareStatement(query.toString());
			cmdSql.setInt(1, diasAntes);
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				RecordarTurnoView rec = new RecordarTurnoView();
				rec.setIdTurno(result.getInt(1));
				rec.setFechaHora(result.getTimestamp(2));
				rec.setApellidoPaciente(result.getString(3));
				rec.setNombrePaciente(result.getString(4));
				rec.setEmailPaciente(result.getString(5));
				rec.setApellidoProfesional(result.getString(6));
				rec.setNombreProfesional(result.getString(7));
				lista.add(rec);
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
}
