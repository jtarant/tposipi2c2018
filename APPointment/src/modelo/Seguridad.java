package modelo;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

public class Seguridad 
{	
	@SuppressWarnings("deprecation")
	public static String ofuscarId(int Id)
	{
		String idBase64 = Base64.getEncoder().encodeToString(Integer.toString(Id).getBytes());
		return URLEncoder.encode(idBase64);
	}
	
	@SuppressWarnings("deprecation")
	public static String ofuscarPassword(String password)
	{
		String passBase64 = Base64.getEncoder().encodeToString(password.getBytes());
		return URLEncoder.encode(passBase64);
	}
	
	@SuppressWarnings("deprecation")
	public static int desOfuscarId(String idOfuscado)
	{
		String idBase64 = URLDecoder.decode(idOfuscado);
		byte[] bytes = Base64.getDecoder().decode(idBase64);
		return Integer.parseInt(new String(bytes));
	}
	
	public static String desOfuscarPassword(String passOfuscada)
	{
		String passBase64 = URLDecoder.decode(passOfuscada);
		byte[] bytes = Base64.getDecoder().decode(passBase64);
		return new String(bytes);
	}
}
