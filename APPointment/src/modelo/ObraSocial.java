package modelo;

import controlador.ObraSocialView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ObraSocial 
{
	private int id;
	private String nombre;
	private String telefonoPrestadores;
	private String direccionFacturacion;
	private String email;
	private Boolean activo;
	private String nomenclador;
	
	public ObraSocial(int id, String nombre, String telefonoPrestadores, String direccionFacturacion, String email,
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

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefonoPrestadores() {
		return telefonoPrestadores;
	}

	public void setTelefonoPrestadores(String telefonoPrestadores) {
		this.telefonoPrestadores = telefonoPrestadores;
	}

	public String getDireccionFacturacion() {
		return direccionFacturacion;
	}

	public void setDireccionFacturacion(String direccionFacturacion) {
		this.direccionFacturacion = direccionFacturacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getNomenclador() {
		return nomenclador;
	}

	public void setNomenclador(String nomenclador) {
		this.nomenclador = nomenclador;
	}
	
	public void actualizar()
	{
		// RELEASE 3
		throw new NotImplementedException();
	}
	
	public void eliminar()
	{
		// RELEASE 3
		throw new NotImplementedException();		
	}

	public ObraSocialView getView() 
	{	
		return new ObraSocialView(getId(),getNombre(),getTelefonoPrestadores(),getDireccionFacturacion(),getEmail(),getActivo(),getNomenclador());
	}
}
