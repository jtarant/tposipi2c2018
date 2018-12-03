package controlador;

import java.util.Hashtable;

import modelo.EmailRecuperoPassword;
import modelo.NotificadorEmail;
import modelo.Seguridad;
import modelo.Usuario;
import persistencia.AdmPersistenciaUsuarios;

public class AdminUsuarios {
	private static AdminUsuarios instancia;
	private Hashtable<String, Usuario> usuarios;
	private Usuario usuarioLogueado;
	
	private AdminUsuarios()
	{
		usuarios = new Hashtable<String, Usuario>();
		usuarioLogueado = null;
	}
	
	public static AdminUsuarios getInstancia()
	{
		if (instancia == null)
			instancia = new AdminUsuarios();
		return instancia;
	}
	
	private Usuario buscar(String email) throws Exception
	{
		String emailBuscado = email.trim().toLowerCase();
		// Primero lo busco en la coleccion de usuarios, que uso como cache
		Usuario usr = null;
		usr = usuarios.get(emailBuscado);
		if (usr != null)
			return usr;
		else
			// Si no lo encontre, voy a la base a buscarlo y si estaba, lo agrego a la coleccion para tenerlo cacheado
			usr = AdmPersistenciaUsuarios.getInstancia().buscar(email);
			if (usr != null) usuarios.put(email, usr);
			return usr;
	}
	
	public Boolean autenticar(String email, String password) throws Exception
	{
		Usuario usr = this.buscar(email);
		if (usr != null)
		{
			if (usr.getContrasena().equals(password))
			{
				this.usuarioLogueado = usr;
				return true;
			}
			else 
			{
				this.usuarioLogueado = null;
				return false;
			}
		}
		else return false;
	}
	
	public void recuperarContrasena(String email) throws Exception {
		Usuario usuarioNuevaPass = this.buscar(email);
		EmailRecuperoPassword emailPassword;
		String nuevaPassword;
		if(usuarioNuevaPass != null){
			nuevaPassword = usuarioNuevaPass.getApellido() + "2018";
			usuarioNuevaPass.setContrasena(Seguridad.ofuscarPassword(nuevaPassword));
			AdmPersistenciaUsuarios.getInstancia().modificar(usuarioNuevaPass);
			
			emailPassword = new EmailRecuperoPassword(email, usuarioNuevaPass.getApellido(), usuarioNuevaPass.getNombre(), nuevaPassword);
			NotificadorEmail.getInstancia().enviar(emailPassword);
		}
	}
}
