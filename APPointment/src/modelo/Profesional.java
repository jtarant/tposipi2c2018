package modelo;

public class Profesional {
	private int id;
	private String especialidad;
	private String telefono;
	private Boolean activo;

	
	public Profesional(int id, String especialidad, String telefono, Boolean activo)
	{
		this.setId(id);
		this.setEspecialidad(especialidad);
		this.setTelefono(telefono);
		this.setActivo(activo);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
}
