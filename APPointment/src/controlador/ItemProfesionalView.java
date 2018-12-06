package controlador;

public class ItemProfesionalView {
	
	private String apellido;
	private String nombre;
	private String especialidad;
	private Boolean activo;
	
	public ItemProfesionalView(String apellido, String nombre, String especialidad, Boolean activo) {
		super();
		this.apellido = apellido;
		this.nombre = nombre;
		this.especialidad = especialidad;
		this.activo = activo;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}	
}
