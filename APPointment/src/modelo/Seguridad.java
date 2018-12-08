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
		return Base64.getEncoder().encodeToString(password.getBytes());
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
		byte[] bytes = Base64.getDecoder().decode(passOfuscada);
		return new String(bytes);
	}
}
