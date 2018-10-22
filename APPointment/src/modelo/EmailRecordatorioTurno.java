package modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailRecordatorioTurno extends Email
{
	final String TOKEN_APELLIDO_PAC = "@PAC_APELLIDO";
	final String TOKEN_NOMBRE_PAC = "@PAC_NOMBRE";
	final String TOKEN_FECHA_HORA = "@FECHAHORA";
	final String TOKEN_APELLIDO_PRO = "@PRO_APELLIDO";
	final String TOKEN_NOMBRE_PRO = "@PRO_NOMBRE";
	final String TOKEN_TURNO_ID = "@IDTURNO";	
	private String apellidoPaciente;
	private String nombrePaciente;
	private Date fechaHoraTurno;
	private String apellidoProfesional;
	private String nombreProfesional;
	private int idTurno;
	private static String templateEmail;
	
	public static void inicializar() throws IOException
	{
		if (templateEmail == null)
		{
			templateEmail = new String(Files.readAllBytes(Paths.get("./bin/EmailRecordatorio.html")));
		}
	}
	
	public EmailRecordatorioTurno(String destinatario, String apellidoPaciente, String nombrePaciente, Date fechaHoraTurno, String apellidoProfesional, String nombreProfesional, int idTurno) throws Exception 
	{
		inicializar();
		setAsunto("Recordatorio de turno: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.fechaHoraTurno));
		setDestinatarios(destinatario);
		this.apellidoPaciente = apellidoPaciente;
		this.nombrePaciente = nombrePaciente;
		this.fechaHoraTurno = fechaHoraTurno;
		this.apellidoProfesional = apellidoProfesional;
		this.nombreProfesional = nombreProfesional;
		this.idTurno = idTurno;
	}

	@Override
	public String getTexto() 
	{
		String body = templateEmail.replace(TOKEN_APELLIDO_PAC, this.apellidoPaciente);
		body = body.replace(TOKEN_NOMBRE_PAC, this.nombrePaciente);
		body = body.replace(TOKEN_FECHA_HORA, new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.fechaHoraTurno));
		body = body.replace(TOKEN_APELLIDO_PRO, this.apellidoProfesional);
		body = body.replace(TOKEN_NOMBRE_PRO, this.nombreProfesional);
		body = body.replace(TOKEN_TURNO_ID, Seguridad.ofuscarId(idTurno));
		return body;
	}
}
