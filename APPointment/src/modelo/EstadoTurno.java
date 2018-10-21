package modelo;

public enum EstadoTurno { 
	ABIERTO(0), CERRADO(1), ANULADO(2); 
	
	private final int valor;
	
	private EstadoTurno(int valor)
	{
		this.valor = valor;
	}
	public int getValor()
	{
		return valor;
	}
	public static EstadoTurno fromInt(int valor)
	{
		for (EstadoTurno tipo: values())
		{
			if (tipo.getValor() == valor) return tipo;
		}
		return null;
	}
}
