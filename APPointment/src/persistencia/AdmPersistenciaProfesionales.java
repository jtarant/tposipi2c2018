package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import controlador.IdNombreView;
import modelo.Profesional;

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
	
	public Profesional buscar(int id) throws Exception
	{
		Connection cnx = null;
		Profesional profesional = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT ID,ID_Usuario,especialidad,duracionTurno,telefono,activo FROM Profesional WHERE ID=?");
			cmdSql.setInt(1, id);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				String especialidad = result.getString(3);
				int duracionTurno = result.getInt(4);
				String telefono = result.getString(5);
				Boolean activo = result.getBoolean(6);
				profesional = new Profesional(id, especialidad, duracionTurno, telefono, activo);
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
}
