package modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EmailRecuperoPassword extends Email 
{
	final String TOKEN_APELLIDO_USR = "@USR_APELLIDO";
	final String TOKEN_NOMBRE_USR = "@USR_NOMBRE";
	final String TOKEN_PASSWORD = "@PASSWORD";
	private String apellidoUsuario;
	private String nombreUsuario;
	private String password;
	private static String templateEmail;	
	
	public static void inicializar() throws IOException
	{
		if (templateEmail == null)
		{
			templateEmail = new String(Files.readAllBytes(Paths.get("./bin/EmailPassword.html")));
		}
	}
	
	public EmailRecuperoPassword(String destinatario, String apellidoUsuario, String nombreUsuario, String password) throws Exception 
	{
		inicializar();
		setAsunto("APPointment - Recupero de contraseña");
		setDestinatarios(destinatario);
		this.apellidoUsuario = apellidoUsuario;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
	}

	@Override
	public String getTexto() 
	{
		String body = templateEmail.replace(TOKEN_APELLIDO_USR, this.apellidoUsuario);
		body = body.replace(TOKEN_NOMBRE_USR, this.nombreUsuario);
		body = body.replace(TOKEN_PASSWORD, this.password);
		return body;
	}
}
