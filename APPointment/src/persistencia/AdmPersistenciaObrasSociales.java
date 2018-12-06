package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import controlador.IdNombreView;
import controlador.ObraSocialView;
import modelo.AlcanceCobertura;
import modelo.ObraSocial;
import modelo.Plan;
import modelo.Servicio;

public class AdmPersistenciaObrasSociales 
{
	private static AdmPersistenciaObrasSociales instancia;
	
	private AdmPersistenciaObrasSociales()
	{
	}

	public static AdmPersistenciaObrasSociales getInstancia()
	{
		if (instancia == null)
			instancia = new AdmPersistenciaObrasSociales();
		return instancia;
	}

	public List<AlcanceCobertura> obtenerAlcanceCobertura(int idPlan) throws Exception
	{
		List<AlcanceCobertura> alcances = new ArrayList<AlcanceCobertura>();
		Connection cnx = null;

		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();

			StringBuilder sql = new StringBuilder("SELECT AlcanceCobertura.ID AS ID_Alcance,Servicio.ID AS ID_Servicio,Servicio.Nombre,importeCopago FROM AlcanceCobertura ");
			sql.append("INNER JOIN ServicioDetalle ON AlcanceCobertura.ID_PracticaMedica=ServicioDetalle.ID_Practica ");
			sql.append("INNER JOIN Servicio ON ServicioDetalle.ID_Servicio=Servicio.ID WHERE ID_Plan=?");
			PreparedStatement cmdSql = cnx.prepareStatement(sql.toString());
			cmdSql.setInt(1, idPlan);
			ResultSet result = cmdSql.executeQuery();
			
			while (result.next())
			{
				AlcanceCobertura a = new AlcanceCobertura(result.getInt(1), new Servicio(result.getInt(2), result.getString(3)), result.getFloat(4));
				alcances.add(a);
			}
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
		return alcances;
	}
	
	public List<IdNombreView> listarPorIdNombre() throws Exception
	{
		Connection cnx = null;
		List<IdNombreView> lista = new ArrayList<IdNombreView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT ID,Nombre FROM ObraSocial WHERE activo=1 ORDER BY nombre");
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				lista.add(new IdNombreView(result.getInt(1), result.getString(2)));
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
	
	public List<ObraSocialView> listarObrasSociales() throws Exception
	{
		Connection cnx = null;
		List<ObraSocialView> lista = new ArrayList<ObraSocialView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT obrasocial.id,obrasocial.nombre,telefonoPrestadores,direccionFacturacion,email,activo,nomenclador.nombre FROM ObraSocial INNER JOIN Nomenclador ON ObraSocial.ID_Nomenclador=Nomenclador.ID ORDER BY obrasocial.nombre");
			
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				lista.add(new ObraSocialView(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getBoolean(6), result.getString(7)));
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
	
	public List<IdNombreView> listarPlanesPorIdNombre(int idObraSocial) throws Exception
	{
		Connection cnx = null;
		List<IdNombreView> lista = new ArrayList<IdNombreView>();
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT ID, nombre FROM [Plan] WHERE ID_ObraSocial=? AND activo=1");
			cmdSql.setInt(1, idObraSocial);
			ResultSet result = cmdSql.executeQuery();
			while (result.next())
			{
				lista.add(new IdNombreView(result.getInt(1), result.getString(2)));
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

	public ObraSocial buscar(int id) throws Exception
	{
		Connection cnx = null;
		ObraSocial os = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT obrasocial.nombre,telefonoPrestadores,direccionFacturacion,email,activo,nomenclador.nombre FROM ObraSocial INNER JOIN Nomenclador ON ObraSocial.ID_Nomenclador=Nomenclador.ID WHERE ObraSocial.ID=?");
			cmdSql.setInt(1, id);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				String nombre = result.getString(1);
				String telefonoPrestadores = result.getString(2);
				String direccionFacturacion = result.getString(3);
				String email = result.getString(4);
				Boolean activo = result.getBoolean(5);
				String nomenclador = result.getString(6);
				os = new ObraSocial(id, nombre, telefonoPrestadores, direccionFacturacion, email, activo, nomenclador);
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return os;
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

	public Plan obtenerPlan(int id) throws Exception 
	{
		Connection cnx = null;
		Plan plan = null;
		try
		{
			cnx = PoolConexiones.getInstancia().getConnection();
			PreparedStatement cmdSql = cnx.prepareStatement("SELECT id,nombre,activo,ID_ObraSocial FROM [Plan] WHERE [Plan].ID=?");
			cmdSql.setInt(1, id);
			ResultSet result = cmdSql.executeQuery();
			if (result.next())
			{
				plan = new Plan(result.getInt(1), result.getString(2), result.getInt(4));
			}
			PoolConexiones.getInstancia().realeaseConnection(cnx);
			return plan;
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
