package controlador;

import java.util.List;

public class AgendaView 
{
	private List<ItemAgendaView> vistaTurnos;
	private List<ErrorTurnoView> vistaErrores;
	
	public AgendaView(List<ItemAgendaView> vistaTurnos, List<ErrorTurnoView> vistaErrores) 
	{
		this.vistaTurnos = vistaTurnos;
		this.vistaErrores = vistaErrores;
	}

	public List<ItemAgendaView> getVistaTurnos() {
		return vistaTurnos;
	}

	public List<ErrorTurnoView> getVistaErrores() {
		return vistaErrores;
	}
}
