package controlador;

public class ObraSocialView {
	private int id;
	private String nombre;
	private String telefonoPrestadores;
	private String direccionFacturacion;
	private String email;
	private Boolean activo;
	private String nomenclador;
	
	public ObraSocialView(int id, String nombre, String telefonoPrestadores, String direccionFacturacion, String email,
			Boolean activo, String nomenclador) {
		this.id = id;
		this.nombre = nombre;
		this.telefonoPrestadores = telefonoPrestadores;
		this.direccionFacturacion = direccionFacturacion;
		this.email = email;
		this.activo = activo;
		this.nomenclador = nomenclador;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefonoPrestadores() {
		return telefonoPrestadores;
	}

	public String getDireccionFacturacion() {
		return direccionFacturacion;
	}

	public String getEmail() {
		return email;
	}

	public Boolean getActivo() {
		return activo;
	}

	public String getNomenclador() {
		return nomenclador;
	}	
}
