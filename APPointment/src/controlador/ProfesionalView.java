package controlador;

import java.util.List;

public class ProfesionalView 
{
	private int id;
	private String especialidad;
	private int duracionTurno;
	private String telefono;
	private Boolean activo;
	private List<PrestacionView> prestaciones;
	
	public ProfesionalView(int id, String especialidad, int duracionTurno, String telefono, Boolean activo,
			List<PrestacionView> prestaciones) {
		this.id = id;
		this.especialidad = especialidad;
		this.duracionTurno = duracionTurno;
		this.telefono = telefono;
		this.activo = activo;
		this.prestaciones = prestaciones;
	}

	public int getId() {
		return id;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public int getDuracionTurno() {
		return duracionTurno;
	}

	public String getTelefono() {
		return telefono;
	}

	public Boolean getActivo() {
		return activo;
	}

	public List<PrestacionView> getPrestaciones() {
		return prestaciones;
	}
}
