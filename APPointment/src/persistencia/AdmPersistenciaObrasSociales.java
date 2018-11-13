package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import controlador.IdNombreView;
import modelo.AlcanceCobertura;
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
}
