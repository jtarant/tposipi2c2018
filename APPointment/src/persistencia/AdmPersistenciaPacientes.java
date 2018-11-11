package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

			PreparedStatement cmdSql = cnx.prepareStatement("SELECT Id,apellido,nombre,DNI FROM Paciente WHERE apellido LIKE ?");
			cmdSql.setString(1, '%' + apellido + '%');
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				PacienteEncontradoView pac = new PacienteEncontradoView();
				pac.setId(result.getInt(1));
				pac.setApellido(result.getString(2));
				pac.setNombre(result.getString(3));
				pac.setDNI(result.getInt(4));
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

	private List<Cobertura> obtenerCoberturas(int idPaciente, Connection cnx) throws SQLException
	{
		List<Cobertura> coberturas = new ArrayList<Cobertura>();
		
		StringBuilder sql = new StringBuilder("SELECT cobertura.ID AS ID_Cobertura,[plan].ID AS ID_Plan,[Plan].nombre AS NombrePlan, nroCredencial, primaria FROM Cobertura ");
		sql.append("INNER JOIN dbo.[Plan] ON Cobertura.ID_Plan=dbo.[Plan].ID ");
		sql.append("WHERE dbo.[Plan].activo=1 AND ID_Paciente=? ORDER BY primaria");
		PreparedStatement cmdSql = cnx.prepareStatement(sql.toString());
		cmdSql.setInt(1, idPaciente);
		ResultSet result = cmdSql.executeQuery();
		
		if (result.next())
		{
			Plan plan = new Plan(result.getInt(2), result.getString(3), null);
			Cobertura c = new Cobertura(result.getInt(1), result.getString(4), result.getBoolean(5), plan);
			coberturas.add(c);
		}
		return coberturas;
	}
}
