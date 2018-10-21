package controlador;

public class IdNombreView {
	private int id;
	private String Nombre;
	
	public IdNombreView(int id, String nombre)
	{
		this.setId(id);
		this.setNombre(nombre);
	}
	
	public IdNombreView() {
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	@Override
	public String toString()
	{
		return Nombre;
	}
}
