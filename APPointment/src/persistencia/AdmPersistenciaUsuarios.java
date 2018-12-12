package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import modelo.Seguridad;
import modelo.Usuario;

public class AdmPersistenciaUsuarios {
	private static AdmPersistenciaUsuarios instancia;
	
	private AdmPersistenciaUsuarios()
	{
	}

	public static AdmPersistenciaUsuarios getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaUsuarios();
		return instancia;
	}
	

	public Usuario buscar(String idUsuario) throws Exception
	{
		Connection cnx = null;
		Usuario usr = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT ID,nombre,apellido,contrasena,email,rol,activo FROM USUARIO WHERE email=? AND Activo<>0");
			cmdSql.setString(1, idUsuario);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				int id = result.getInt(1);
				String nombre = result.getString(2);
				String apellido = result.getString(3);
				String contrasena = result.getString(4);
				String email = result.getString(5);
				String rol = result.getString(6);
				Boolean activo = result.getBoolean(7);
				
				usr = new Usuario(id,nombre,apellido, contrasena, email, rol, activo);
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return usr;
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


	public void modificar(Usuario usr) throws Exception 
	{
		Connection cnx = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("UPDATE Usuario SET contrasena=? WHERE ID=?");
			cmdSql.setString(1, usr.getContrasena());
			cmdSql.setInt(2, usr.getIdUsuario());
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
