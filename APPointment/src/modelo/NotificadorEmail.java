package modelo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificadorEmail
{
	private static NotificadorEmail instancia;
	private String de;
	private String smtpUsuario;
	private String smtpPassword;
	private Email email;
	private Properties props;
	private Session session;

	private NotificadorEmail()
	{
		try
		{
			props = new Properties();
			props.load(getClass().getClassLoader().getResourceAsStream("ConfigEmail.txt"));
			props.put("mail.smtp.ssl.trust", props.getProperty("mail.smtp.host"));
			smtpUsuario = props.getProperty("smtpUsuario");
			smtpPassword = props.getProperty("smtpPassword");
			de = props.getProperty("de");
			session = Session.getInstance(props,
					  new javax.mail.Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(smtpUsuario, smtpPassword);
						}
					  });
		}
		catch (Exception e)
		{
			System.out.println("Error al inicializar la configuracion de emails: " + e.getMessage());
			session = null;
		}
	}
	
	public static NotificadorEmail getInstancia()
	{
		if (instancia == null)
			instancia = new NotificadorEmail();
		return instancia;
	}

	public void enviar(Email email)
	{
		try
		{
			this.email = email;
			this.enviar();
		}
		catch (Exception e)
		{
			// Si falla la notificacion, no tiene que fallar la aplicacion, asi que no re-lanzo la excepcion
			System.out.println("Error al enviar email: " + e.getMessage());
		}		
	}
	
	private void enviar() throws Exception
	{
		String texto = email.getTexto();
		String destinatarios = email.getDestinatarios();
		
		System.out.println("Enviando mail a: " + destinatarios);
		if (session != null)
		{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(de));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatarios));
			message.setSubject(email.getAsunto());
			message.setContent(texto, "text/html; charset=utf-8");
			Transport.send(message);
			System.out.println("enviado correctamente.");
		}
	}
}
