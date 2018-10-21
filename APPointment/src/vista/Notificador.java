package vista;

import controlador.AdminTurnos;

public class Notificador {

	public static void main(String[] args) 
	{
		try
		{
			AdminTurnos.getInstancia().recordarTurnos();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
