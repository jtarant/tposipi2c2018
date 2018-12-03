package vista;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorTexto {
	private final static String DATE_FORMAT = "dd/MM/yyyy";
	private final static String HOUR_FORMAT = "HH:mm";
	private final static String VALIDACION_EMAIL = "^([\\w-\\.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$";		

	public static Boolean esFechaValida(String date) 
	{
	        try {
	            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
	            df.setLenient(false);
	            df.parse(date);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	}

	public static Boolean esHoraValida(String date) 
	{
	        try {
	            DateFormat df = new SimpleDateFormat(HOUR_FORMAT);
	            df.setLenient(false);
	            df.parse(date);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	}
	
	public static Boolean esMonedaValida(String valor)
	{
		try 
		{
			float monto = Float.parseFloat(valor);
			if (monto < 0)
				return false;
			else
				return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public static Boolean esEmailValido(String email)
	{
		Pattern exp = Pattern.compile(VALIDACION_EMAIL);
		Matcher matcher = exp.matcher(email);
		return matcher.matches();
	}
	
	public static Boolean esEnteroValido(String valor)
	{
		try 
		{
			Integer.parseInt(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}		
	}
}
