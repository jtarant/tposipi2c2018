package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import controlador.IdNombreView;
import controlador.PrestacionView;
import controlador.ProfesionalView;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Profesional extends Usuario 
{
	private int id;
	private String especialidad;
	private int duracionTurno;
	private String telefono;
	private Boolean activo;
	private List<Prestacion> prestaciones;

	public Profesional(int id, String especialidad, int duracionTurno, String telefono, Boolean activo, List<Prestacion> prestaciones)
	{
		this.setId(id);
		this.setEspecialidad(especialidad);
		this.setDuracionTurno(duracionTurno);
		this.setTelefono(telefono);
		this.setActivo(activo);
		this.prestaciones = prestaciones;
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

	public int getDuracionTurno() {
		return duracionTurno;
	}

	public void setDuracionTurno(int duracionTurno) {
		this.duracionTurno = duracionTurno;
	}

	public List<Prestacion> getPrestaciones() {
		return prestaciones;
	}
	
	public Prestacion obtenerPrestacion(int idServicio)
	{
		Iterator<Prestacion> iterador = getPrestaciones().iterator();
		while (iterador.hasNext())
		{
			Prestacion p = iterador.next();
			if (p.getServicio().getId() == idServicio) return p;
		}
		return null;
	}
	
	public void actualizar()
	{
		// RELEASE 2
		throw new NotImplementedException();
	}
	
	public void eliminar()
	{
		// RELEASE 2
		throw new NotImplementedException();
	}
	
	@Override
	public IdNombreView getIdNombreView() 
	{
		IdNombreView idNombre = super.getIdNombreView();
		idNombre.setId(getId());
		return idNombre;
	}

	public ProfesionalView getView()
	{
		List<PrestacionView> pv = new ArrayList<PrestacionView>();
		for (Prestacion p: getPrestaciones())
			pv.add(p.getView());
		return new ProfesionalView(getId(), getEspecialidad(), getDuracionTurno(), getTelefono(), getActivo(), pv);
	}
}
