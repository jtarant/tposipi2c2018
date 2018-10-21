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

import controlador.AdminProfesionales;
import controlador.AdminTurnos;
import controlador.IdNombreView;
import controlador.ItemAgendaView;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPrincipal extends JFrame {

	private WeekCalendar calendario;
	private JComboBox<IdNombreView> cboProfesionales;
	private IdNombreView idNombreProfesional;

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
			this.modificarEvento(e.getCalendarEvent().getID());
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
        setSize(1000, 900);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnAgenda = new JMenu("Agenda");
        menuBar.add(mnAgenda);
        
        JMenuItem mntmReglas = new JMenuItem("Reglas");
        mnAgenda.add(mntmReglas);
        
        JMenu mnFacturacion = new JMenu("Facturacion");
        menuBar.add(mnFacturacion);
        
        JMenu mnPacientes = new JMenu("Pacientes");
        menuBar.add(mnPacientes);
        
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
	
	@SuppressWarnings("deprecation")
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
			List<ItemAgendaView> agenda = AdminTurnos.getInstancia().obtenerAgenda(desde, hasta, idNombreProfesional.getId());
			for (ItemAgendaView item: agenda)
			{
				eventos.add(crearEvento(item.getFechaHoraInicio(), item.getFechaHoraFin(), item.getApellido(), item.getNombre(), item.getDNI(), item.getIdTurno()));
			}
			calendario.setEvents(eventos);
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
				int duracion = 60; // TODO: deshardcodear duracion, tiene que salir del profesional
				Date inicio = formTurno.getFechaHora();
								
				AdminTurnos.getInstancia().reservar(formTurno.getIDPaciente(), idNombreProfesional.getId(), inicio, duracion);
				
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.setTime(inicio);
				cal.add(java.util.Calendar.MINUTE, duracion);
				calendario.addEvent(crearEvento(inicio, cal.getTime(), formTurno.getApellido(), formTurno.getNombre(), formTurno.getDNI(), formTurno.getIDPaciente()));
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
	
	private CalendarEvent crearEvento(Date fechaHoraInicio, Date fechaHoraFin, String apellido, String nombre, int DNI, int idTurno)
	{
		String titulo = apellido + ", " + nombre;
		if (DNI > 0) titulo = titulo + " DNI " + Integer.toString(DNI);
		LocalDate ldInicio = fechaHoraInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalTime ltInicio = fechaHoraInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		LocalTime ltFin = fechaHoraFin.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
		return new CalendarEvent(ldInicio, ltInicio, ltFin, titulo, new Color(194,205,248), idTurno);
	}
	
	private void modificarEvento(int id)
	{
		System.out.println(id);
	}
}
