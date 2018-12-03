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

import controlador.PacienteEncontradoView;
import modelo.Cobertura;
import modelo.Paciente;
import modelo.Plan;

public class AdmPersistenciaPacientes {
	private static AdmPersistenciaPacientes instancia;
	
	private AdmPersistenciaPacientes()
	{
	}

	public static AdmPersistenciaPacientes getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaPacientes();
		return instancia;
	}

	public List<PacienteEncontradoView> buscarPorApellido(String apellido) throws Exception
	{
		Connection cnx = null;
		List<PacienteEncontradoView> lista = new ArrayList<PacienteEncontradoView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();

			PreparedStatement cmdSql = cnx.prepareStatement("SELECT Id,apellido,nombre,DNI,Telefono FROM Paciente WHERE apellido LIKE ?");
			cmdSql.setString(1, '%' + apellido + '%');
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				PacienteEncontradoView pac = new PacienteEncontradoView();
				pac.setId(result.getInt(1));
				pac.setApellido(result.getString(2));
				pac.setNombre(result.getString(3));
				pac.setDNI(result.getInt(4));
				pac.setTelefono(result.getString(5));
				lista.add(pac);
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

	public Paciente buscar(int id) throws Exception
	{
		Connection cnx = null;
		Paciente paciente = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT apellido,nombre,DNI,fechaNacimiento,telefono,email,activo FROM Paciente WHERE id=?");
			cmdSql.setInt(1, id);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				String apellido = result.getString(1);
				String nombre = result.getString(2);
				int dni = result.getInt(3);
				Date fechaNac = result.getDate(4);
				String telefono = result.getString(5);
				String email = result.getString(6);
				Boolean activo = result.getBoolean(7);
				List<Cobertura> coberturas = obtenerCoberturas(id, cnx);
				paciente = new Paciente(id, apellido, nombre, dni, fechaNac, telefono, email, activo, coberturas);
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return paciente;
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

	private List<Cobertura> obtenerCoberturas(int idPaciente, Connection cnx) throws Exception
	{
		List<Cobertura> coberturas = new ArrayList<Cobertura>();
		
		StringBuilder sql = new StringBuilder("SELECT cobertura.ID AS ID_Cobertura,[plan].ID AS ID_Plan,[Plan].nombre AS NombrePlan, nroCredencial, primaria, ID_ObraSocial FROM Cobertura ");
		sql.append("INNER JOIN dbo.[Plan] ON Cobertura.ID_Plan=dbo.[Plan].ID ");
		sql.append("INNER JOIN ObraSocial ON [Plan].ID_ObraSocial=ObraSocial.ID ");
		sql.append("WHERE dbo.[Plan].activo=1 AND Cobertura.activa=1 AND ID_Paciente=? ORDER BY primaria");
		PreparedStatement cmdSql = cnx.prepareStatement(sql.toString());
		cmdSql.setInt(1, idPaciente);
		ResultSet result = cmdSql.executeQuery();
		
		if (result.next())
		{
			Plan plan = new Plan(result.getInt(2), result.getString(3), result.getInt(6));
			Cobertura c = new Cobertura(result.getInt(1), result.getString(4), result.getBoolean(5), plan);
			coberturas.add(c);
		}
		return coberturas;
	}
	
	public void insertar(Paciente paciente) throws Exception
	{
		Connection cnx = null;
		ResultSet clavesGeneradas = null;
		try
		{
			cnx = PoolConexiones.getInstancia().iniciarTransaccion();
			PreparedStatement cmdSql = cnx.prepareStatement("INSERT INTO Paciente (nombre,apellido,DNI,fechaNacimiento,telefono,email,activo) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			cmdSql.setString(1, paciente.getNombre());
			cmdSql.setString(2, paciente.getApellido());
			if (paciente.getDNI() != null)
				cmdSql.setInt(3, paciente.getDNI());
			else
				cmdSql.setNull(3, java.sql.Types.INTEGER);
			cmdSql.setTimestamp(4, new Timestamp(paciente.getFechaNacimiento().getTime()));
			cmdSql.setString(5, paciente.getTelefono());
			cmdSql.setString(6, paciente.getEmail());
			cmdSql.setBoolean(7, paciente.getActivo());
			cmdSql.execute();
			clavesGeneradas = cmdSql.getGeneratedKeys();
			if (clavesGeneradas.next())
			{
				paciente.setId(clavesGeneradas.getInt(1));
			}
			for (Cobertura item: paciente.getCoberturas())
			{
				insertarCobertura(paciente.getId(), item, cnx);
			}
			cnx.commit();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			if (cnx != null) cnx.rollback();
			throw e;
		}		
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().finTransaccion(); 
		}		
	}
	
	private void insertarCobertura(int idPaciente, Cobertura cobertura, Connection cnx) throws SQLException
	{
		PreparedStatement cmdSql = cnx.prepareStatement("INSERT INTO Cobertura (ID_Paciente,ID_Plan,nroCredencial,primaria,activa) VALUES (?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
		cmdSql.setInt(1, idPaciente);
		cmdSql.setInt(2, cobertura.getPlan().getId());
		cmdSql.setString(3, cobertura.getNumeroCredencial());
		cmdSql.setBoolean(4, cobertura.getPrimaria());
		cmdSql.setBoolean(5, cobertura.getActiva());
		cmdSql.execute();
		ResultSet clavesGeneradas = cmdSql.getGeneratedKeys();
		if (clavesGeneradas.next())
		{
			cobertura.setId(clavesGeneradas.getInt(1));
		}
	}

	private void eliminarCobertura(Cobertura cobertura, Connection cnx) throws SQLException
	{
		PreparedStatement cmdSql = cnx.prepareStatement("UPDATE Cobertura SET activa=0 WHERE ID=?");
		cmdSql.setInt(1, cobertura.getId());
		cmdSql.execute();
	}
	
	public void modificar(Paciente paciente) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().iniciarTransaccion();
			PreparedStatement cmdSql = cnx.prepareStatement("UPDATE Paciente SET nombre=?,apellido=?,DNI=?,fechaNacimiento=?,telefono=?,email=?,activo=? WHERE ID=?");
			cmdSql.setString(1, paciente.getNombre());
			cmdSql.setString(2, paciente.getApellido());
			if (paciente.getDNI() != null)
				cmdSql.setInt(3, paciente.getDNI());
			else
				cmdSql.setNull(3, java.sql.Types.INTEGER);
			cmdSql.setTimestamp(4, new Timestamp(paciente.getFechaNacimiento().getTime()));
			cmdSql.setString(5, paciente.getTelefono());
			cmdSql.setString(6, paciente.getEmail());
			cmdSql.setBoolean(7, paciente.getActivo());
			cmdSql.setInt(8, paciente.getId());		
			cmdSql.execute();
			
			for (Cobertura nueva : paciente.getNuevos())
			{
				insertarCobertura(paciente.getId(), nueva, cnx);
			}
			for (Cobertura eliminada : paciente.getEliminados())
			{
				eliminarCobertura(eliminada, cnx);
			}
			cnx.commit();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			if (cnx != null) cnx.rollback();
			throw e;
		}
		finally
		{
			if (cnx != null) PoolConexiones.getInstancia().finTransaccion(); 
		}
	}

	public void eliminar(Paciente paciente) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSqlPac = cnx.prepareStatement("UPDATE Paciente SET activo = 0 WHERE ID=?");
			cmdSqlPac.setInt(1, paciente.getId());
			cmdSqlPac.execute();
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
