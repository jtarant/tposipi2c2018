package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controlador.ItemProfesionalView;
import controlador.IdNombreView;
import modelo.Prestacion;
import modelo.Profesional;
import modelo.Servicio;

public class AdmPersistenciaProfesionales {
	private static AdmPersistenciaProfesionales instancia;
	
	private AdmPersistenciaProfesionales()
	{
	}

	public static AdmPersistenciaProfesionales getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaProfesionales();
		return instancia;
	}

	public List<IdNombreView> listarPorIdNombre() throws Exception
	{
		Connection cnx = null;
		List<IdNombreView> lista = new ArrayList<IdNombreView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT Profesional.Id,apellido,nombre FROM Profesional INNER JOIN Usuario ON Profesional.ID_Usuario=Usuario.ID WHERE Profesional.activo=1 ORDER BY apellido,nombre");
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				lista.add(new IdNombreView(result.getInt(1), result.getString(2) + ", " + result.getString(3)));
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
	
	public List<ItemProfesionalView> listarProfesionales() throws Exception
	{
		Connection cnx = null;
		List<ItemProfesionalView> lista = new ArrayList<ItemProfesionalView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT apellido,nombre,especialidad,Profesional.activo FROM Profesional INNER JOIN Usuario ON Profesional.ID_Usuario=Usuario.ID ORDER BY apellido,nombre");
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				lista.add(new ItemProfesionalView(result.getString(1), result.getString(2), result.getString(3), result.getBoolean(4)));
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
	public Profesional buscar(int id) throws Exception
	{
		Connection cnx = null;
		Profesional profesional = null;
		try
		{
			StringBuilder sql = new StringBuilder("SELECT Profesional.ID,ID_Usuario,especialidad,duracionTurno,telefono,Profesional.activo,nombre,apellido ");
			sql.append("FROM Profesional INNER JOIN Usuario ON Profesional.ID_Usuario=Usuario.ID WHERE Profesional.ID=?");
			
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement(sql.toString());
			cmdSql.setInt(1, id);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				String especialidad = result.getString(3);
				int duracionTurno = result.getInt(4);
				String telefono = result.getString(5);
				Boolean activo = result.getBoolean(6);
				List<Prestacion> prestaciones = obtenerPrestaciones(id, cnx);
				profesional = new Profesional(id, especialidad, duracionTurno, telefono, activo, prestaciones);
				profesional.setNombre(result.getString(7));
				profesional.setApellido(result.getString(8));
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return profesional;
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
	
	private List<Prestacion> obtenerPrestaciones(int idProfesional, Connection cnx) throws SQLException
	{
		List<Prestacion> prestaciones = new ArrayList<Prestacion>();
		
		StringBuilder sql = new StringBuilder("SELECT servicio.ID AS ID_Servicio,servicio.nombre,prestacion.ID AS ID_Prestacion,importeHonorarios FROM Prestacion ");
		sql.append("INNER JOIN Profesional ON Prestacion.ID_Profesional=Profesional.ID ");
		sql.append("INNER JOIN Servicio ON Prestacion.ID_Servicio=Servicio.ID ");
		sql.append("WHERE Servicio.activo=1 AND Profesional.ID=?");
		PreparedStatement cmdSql = cnx.prepareStatement(sql.toString());
		cmdSql.setInt(1, idProfesional);
		ResultSet result = cmdSql.executeQuery();
		
		while (result.next())
		{
			Prestacion p = new Prestacion(result.getInt(3), new Servicio(result.getInt(1), result.getString(2)), result.getFloat(4));
			prestaciones.add(p);
		}
		return prestaciones;
	}
}
