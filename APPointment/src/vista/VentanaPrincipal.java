package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.davidmoodie.SwingCalendar.Calendar;
import com.davidmoodie.SwingCalendar.CalendarEvent;
import com.davidmoodie.SwingCalendar.WeekCalendar;

import controlador.AdminPacientes;
import controlador.AdminProfesionales;
import controlador.AdminTurnos;
import controlador.AgendaView;
import controlador.IdNombreView;
import controlador.ItemAgendaView;
import controlador.PacienteView;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

public class VentanaPrincipal extends JFrame {

	private WeekCalendar calendario;
	private JComboBox<IdNombreView> cboProfesionales;
	private IdNombreView idNombreProfesional;
	private JPopupMenu popupMenu;
	private CalendarEvent eventoSeleccionado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try 
				{	
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setTitle("APPointment");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ArrayList<CalendarEvent> events = new ArrayList<>();
		calendario = new WeekCalendar(events);
		calendario.addCalendarEventClickListener(e -> { 
			this.accionesEvento(e.getCalendarEvent());
			});
		calendario.addCalendarEmptyClickListener(e -> {			
			
			LocalDateTime ldt = e.getDateTime();
			ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
			Date fecha = Date.from(zdt.toInstant());
			
			LocalTime lt = Calendar.roundTime(e.getDateTime().toLocalTime(), 30);
			@SuppressWarnings("deprecation")
			Date hora = new Date(fecha.getYear(),fecha.getMonth(),fecha.getDay(),lt.getHour(),lt.getMinute(),0);
			
			this.agregarEvento(fecha, hora);
        });

        JPanel weekControls = new JPanel();
        FlowLayout flowLayout = (FlowLayout) weekControls.getLayout();
        getContentPane().add(weekControls, BorderLayout.NORTH);
        
        cboProfesionales = new JComboBox<IdNombreView>();
        cboProfesionales.addActionListener(new ActionListener() {
        	@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
        		JComboBox<IdNombreView> combo = (JComboBox<IdNombreView>)e.getSource();
        		
        		idNombreProfesional = (IdNombreView)combo.getSelectedItem();
        		cargarAgenda();
        	}
        });
        weekControls.add(cboProfesionales);
        
        JButton prevWeekBtn = new JButton("<");
        weekControls.add(prevWeekBtn);
        prevWeekBtn.addActionListener(e -> this.verSemanaAnterior());

        JButton goToTodayBtn = new JButton("Hoy");
        weekControls.add(goToTodayBtn);
        goToTodayBtn.addActionListener(e -> this.verHoy());

        JButton nextWeekBtn = new JButton(">");
        weekControls.add(nextWeekBtn);
        nextWeekBtn.addActionListener(e -> this.verSemanaProxima());

        getContentPane().add(calendario, BorderLayout.CENTER);
        
        popupMenu = new JPopupMenu();
        
        JMenuItem mntmAdmitir = new JMenuItem("Admitir");
        mntmAdmitir.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		admitirTurno();
        	}
        });
        popupMenu.add(mntmAdmitir);
        
        JMenuItem mntmCancelar = new JMenuItem("Cancelar");
        mntmCancelar.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		cancelarTurno();
        	}
        });
        popupMenu.add(mntmCancelar);
        setSize(1000, 900);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnAgenda = new JMenu("Agenda");
        menuBar.add(mnAgenda);
        
        JMenuItem mntmReglas = new JMenuItem("Reglas de programaci\u00F3n de turnos");
        mntmReglas.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		ABMReglas formReglas = new ABMReglas();
        		formReglas.setLocationRelativeTo(null);
        		formReglas.setVisible(true);
        		formReglas.dispose();
        	}
        });
        mnAgenda.add(mntmReglas);
        
        JMenu mnFacturacion = new JMenu("Facturacion");
        menuBar.add(mnFacturacion);
        
        JMenuItem mntmReporteDeFacturacion = new JMenuItem("Reporte de Facturacion");
        mntmReporteDeFacturacion.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		Facturacion formFacturacion = new Facturacion();
        		formFacturacion.setLocationRelativeTo(null);
        		formFacturacion.setVisible(true);
        	}
        });
        mnFacturacion.add(mntmReporteDeFacturacion);
        
        JMenu mnPacientes = new JMenu("Pacientes");
        menuBar.add(mnPacientes);
        
        JMenuItem mntmAdministradorDePacientes = new JMenuItem("Administrador de Pacientes");
        mntmAdministradorDePacientes.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent arg0) {
        		// TODO: Aca va el admin de pacientes, quitar esta prueba
        		PacienteView pv = null;
        		try {
					pv = AdminPacientes.getInstancia().obtener(3);
				} catch (Exception e) {
					e.printStackTrace();
				}
        		DatosPaciente formPaciente = new DatosPaciente();
        		formPaciente.setLocationRelativeTo(null);
        		formPaciente.setPaciente(pv);
        		formPaciente.setVisible(true);
        	}
        });
        mnPacientes.add(mntmAdministradorDePacientes);
        
        JMenu mnProfesionales = new JMenu("Profesionales");
        menuBar.add(mnProfesionales);
        
        JMenu mnUsuarios = new JMenu("Usuarios");
        menuBar.add(mnUsuarios);
        
        JMenu mnObrasSociales = new JMenu("Obras Sociales");
        menuBar.add(mnObrasSociales);
        
        JMenu mnMiPerfil = new JMenu("Mi Perfil");
        menuBar.add(mnMiPerfil);
        
        JMenuItem mntmEditar = new JMenuItem("Editar");
        mnMiPerfil.add(mntmEditar);
        
        JMenuItem mntmModificarContrasea = new JMenuItem("Modificar contrase\u00F1a");
        mnMiPerfil.add(mntmModificarContrasea);
        setVisible(true);
        
        this.cargarProfesionales();
        
        // Actualiza automaticamente cada 1 minuto para detectar las cancelaciones por web
        Timer timer = new Timer(1000*60, e -> this.cargarAgenda());
        timer.start();
 	}
	
	private void cargarProfesionales()
	{
		try
		{
			List<IdNombreView> profs = AdminProfesionales.getInstancia().listarPorIdNombre();
			IdNombreView[] items = profs.toArray(new IdNombreView[profs.size()]);
			cboProfesionales.setModel(new DefaultComboBoxModel<IdNombreView>(items));
			cboProfesionales.setSelectedIndex(0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar los profesionales:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}				
	}
	
	public void cargarAgenda()
	{
		try
		{
			LocalDateTime ldInicioSemana = calendario.getDiasSemana().get(0).atStartOfDay();
			LocalDateTime ldFinSemana = calendario.getDiasSemana().get(calendario.getDiasSemana().size()-1).atTime(23, 59, 59);
			
			ZonedDateTime zdt = ldInicioSemana.atZone(ZoneId.systemDefault());
			Date desde = Date.from(zdt.toInstant());
			zdt = ldFinSemana.atZone(ZoneId.systemDefault());
			Date hasta = Date.from(zdt.toInstant());
						
			ArrayList<CalendarEvent> eventos = new ArrayList<CalendarEvent>();
			AgendaView agenda = AdminTurnos.getInstancia().obtenerAgenda(desde, hasta, idNombreProfesional.getId());
			for (ItemAgendaView item: agenda.getVistaTurnos())
			{
				eventos.add(crearEvento(item.getFechaHoraInicio(), item.getFechaHoraFin(), item.getApellido(), item.getNombre(), item.getDNI(), item.getIdTurno(), item.getEstado()));
			}
			calendario.setEvents(eventos);
			
			if (agenda.getVistaErrores().size() > 0)
			{
				ErroresReglas formErrores = new ErroresReglas(agenda.getVistaErrores());
				formErrores.setAlwaysOnTop(true);
				formErrores.setVisible(true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al cargar la agenda de turnos:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	private void agregarEvento(Date fecha, Date hora)
	{
		try
		{
			DatosTurno formTurno = new DatosTurno();
			formTurno.setNombreProfesional(idNombreProfesional.getNombre());
			formTurno.setFecha(fecha);
			formTurno.setHora(hora);
			formTurno.setLocationRelativeTo(null);
			formTurno.setVisible(true);
			if (!formTurno.getCancelado())
			{
				int duracion = 60; // TODO: obtenerla del profesional
				Date inicio = formTurno.getFechaHora();
								
				int idTurno = AdminTurnos.getInstancia().reservar(formTurno.getIDPaciente(), idNombreProfesional.getId(), inicio);
				
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.setTime(inicio);
				cal.add(java.util.Calendar.MINUTE, duracion);
				calendario.addEvent(crearEvento(inicio, cal.getTime(), formTurno.getApellido(), formTurno.getNombre(), formTurno.getDNI(), idTurno, 0));
			}
			formTurno.dispose();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al guardar los cambios:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private void verHoy()
	{
		calendario.goToToday();
		cargarAgenda();
	}
	
	private void verSemanaProxima()
	{
		calendario.nextWeek();
		cargarAgenda();
	}
	
	private void verSemanaAnterior()
	{
		calendario.prevWeek();
		cargarAgenda();
	}
	
	private CalendarEvent crearEvento(Date fechaHoraInicio, Date fechaHoraFin, String apellido, String nombre, int DNI, int idTurno, int estado)
	{
		String titulo = apellido + ", " + nombre;
		if (DNI > 0) titulo = titulo + " DNI " + Integer.toString(DNI);
		LocalDate ldInicio = fechaHoraInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalTime ltInicio = fechaHoraInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		LocalTime ltFin = fechaHoraFin.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		Color color = (estado == 0)? new Color(194,205,248): Color.LIGHT_GRAY;
		return new CalendarEvent(ldInicio, ltInicio, ltFin, titulo, color, idTurno);
	}
	
	private void accionesEvento(CalendarEvent e)
	{
		eventoSeleccionado = e;
		Point pos = calendario.getMousePosition();
		popupMenu.show(calendario, (int)pos.getX(), (int)pos.getY());
	}
	
	private void cancelarTurno()
	{
		try 
		{
			int opcion = JOptionPane.showConfirmDialog(null, "Seguro que queres cancelar el turno?", "Anular turno", JOptionPane.YES_NO_OPTION);
			if (opcion == JOptionPane.YES_OPTION)
			{
				AdminTurnos.getInstancia().anular(eventoSeleccionado.getID());
				calendario.removeEvent(eventoSeleccionado);
				eventoSeleccionado = null;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error cancelar el turno:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private void admitirTurno()
	{
		try 
		{
			DatosAdmision form = new DatosAdmision(eventoSeleccionado.getID());
			form.setLocationRelativeTo(null);
			form.setVisible(true);
			if (!form.getCancelado())
			{
				AdminTurnos.getInstancia().admitir(eventoSeleccionado.getID(), form.getItemsAdmision(), form.getImporteAbonado());
				
				CalendarEvent actualizado = new CalendarEvent(eventoSeleccionado.getDate(), eventoSeleccionado.getStart(), eventoSeleccionado.getEnd(), eventoSeleccionado.getText(), Color.LIGHT_GRAY, eventoSeleccionado.getID());
				JOptionPane.showMessageDialog(null, "Admision realizada correctamente.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				calendario.removeEvent(eventoSeleccionado);
				calendario.addEvent(actualizado);
			}
			form.dispose();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al guardar la admision:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
}
